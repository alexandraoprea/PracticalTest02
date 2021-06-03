package ro.pub.cs.systems.eim.practicaltest02.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String name;
    private TextView informationTextView;
    private ImageView profileImageView;
    private Bitmap bitmapImage;

    private Socket socket;

    public ClientThread(String address, int port, String name, TextView informationTextView, ImageView profileImageView) {
        this.address = address;
        this.port = port;
        this.name = name;
        this.informationTextView = informationTextView;
        this.profileImageView = profileImageView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(name);
            printWriter.flush();
            String pokemonInformation;
            while ((pokemonInformation = bufferedReader.readLine()) != null) {
                if (pokemonInformation.startsWith("http")) {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGetCartoon = new HttpGet(pokemonInformation);
                    HttpResponse httpResponse = httpClient.execute(httpGetCartoon);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        bitmapImage = BitmapFactory.decodeStream(httpEntity.getContent());
                        profileImageView.post(new Runnable() {
                            @Override
                            public void run() {
                                profileImageView.setImageBitmap(bitmapImage);
                            }
                        });
                    }
                    break;
                }
                final String information = pokemonInformation;
                informationTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        informationTextView.setText(information);
                    }
                });
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }
}