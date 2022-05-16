package be.howest.ti.monopoly.logic.implementation.tile;

public class PropertyTile extends Tile{
    protected int rent;
    protected final int cost;
    protected final int mortgage;
    protected final int groupSize;
    protected final String color;

    public PropertyTile(String name, int position, String type, int cost, int mortgage, int groupSize, String color, int rent) {
        super(name, position, type);
        this.cost = cost;
        this.mortgage = mortgage;
        this.groupSize = groupSize;
        this.color = color;
        this.rent = rent;
    }

    public int getCost() {
        return cost;
    }

    public int getMortgage() {
        return mortgage;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getColor() {
        return color;
    }

    public int receiveRent(){return rent;}
}
