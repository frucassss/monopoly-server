package be.howest.ti.monopoly.logic.implementation;

public class RailroadTile extends Tile{
    private int cost;
    private int mortgage;
    private int rent;
    private int groupSize;
    private String color;

    public RailroadTile(String name, int position, int cost, int mortgage, int rent, int groupSize, String color, String type, String nameAsPathParameter) {
        super(name, position, nameAsPathParameter, type);
        this.cost = cost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.groupSize = groupSize;
        this.color = color;
    }

    public int getCost() {
        return cost;
    }

    public int getMortgage() {
        return mortgage;
    }

    public int getRent() {
        return rent;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getColor() {
        return color;
    }
}
