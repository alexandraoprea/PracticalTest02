package ro.pub.cs.systems.eim.practicaltest02.model;
public class PokemonInformation {
    private String[] typeNames;
    private String[] abilityNames;
    private String imageURI;

    public PokemonInformation() {
        this.typeNames = null;
        this.abilityNames = null;
        this.imageURI = null;
    }

    public PokemonInformation(String[] typeNames, String[] abilityNames, String imageURI) {
        this.typeNames = typeNames;
        this.abilityNames = abilityNames;
        this.imageURI = imageURI;
    }

    public String[] getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(String[] typeNames) {
        this.typeNames = typeNames;
    }

    public String[] getAbilityNames() {
        return abilityNames;
    }

    public void setAbilityNames(String[] abilityNames) {
        this.abilityNames = abilityNames;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    @Override
    public String toString() {
        StringBuilder types = new StringBuilder();
        for (String typeName : typeNames) {
            types.append(typeName);
        }
        StringBuilder abilities = new StringBuilder();
        for (String abilityName : abilityNames) {
            abilities.append(abilityName);
        }
        return "PokemonInformation{" +
                "types='" + types.toString() + '\'' +
                ", abilities='" + abilities.toString() + '\'' +
                '}';
    }
}
