package be.howest.ti.monopoly.logic.implementation;

public class UtilityTile extends Tile{
    private int cost;
    private int mortgage;
    private String rent;
    private int groupSize;
    private String color;
    private String type;
    private String nameAsPathParameter;
    public UtilityTile(String name, int position,int cost,int mortgage, String rent,int groupSize,String color, String type, String nameAsPathParameter) {
        super(name, position, nameAsPathParameter, type);
        this.cost = cost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.groupSize = groupSize;
        this.color = color;
        this.type = type;
        this.nameAsPathParameter = nameAsPathParameter;
    }

    public int getCost() {
        return cost;
    }

    public int getMortgage() {
        return mortgage;
    }

    public String getRent() {
        return rent;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getNameAsPathParameter() {
        return nameAsPathParameter;
    }


}
