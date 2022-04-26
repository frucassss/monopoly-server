package be.howest.ti.monopoly.logic.implementation;

public class Tile{
    private String name;
    private int position;
    private String nameAsPathParameter;
    private String type;

    public Tile(String name, int position, String nameAsPathParameter, String type){
        this.name = name;
        this.position = position;
        this.nameAsPathParameter = nameAsPathParameter;
        this.type = type;
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

        return name != null ? name.equals(tile.name) : tile.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
