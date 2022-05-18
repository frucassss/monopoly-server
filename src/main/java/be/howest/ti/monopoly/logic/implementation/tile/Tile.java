package be.howest.ti.monopoly.logic.implementation.tile;

public class Tile{
    protected final String name;
    protected final int position;
    protected final String nameAsPathParameter;
    protected final String type;

    public Tile(String name, int position, String type){
        this.name = name;
        this.position = position;
        this.nameAsPathParameter = convertNameToNameAsPathParameter(name);
        this.type = type;
    }

    private String convertNameToNameAsPathParameter(String name){
        return name.replaceAll("\\s", "_");
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNameAsPathParameter() {
        return nameAsPathParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return position == tile.position;
    }

    @Override
    public int hashCode() {
        return position;
    }
}
