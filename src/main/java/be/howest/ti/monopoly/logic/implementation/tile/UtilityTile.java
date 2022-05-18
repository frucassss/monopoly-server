package be.howest.ti.monopoly.logic.implementation.tile;

public class UtilityTile extends PropertyTile{

    private static final String DESCRIPTION_RENT = "4 or 10 times the dice roll";

    public UtilityTile(String name, int position, String type, int cost, int mortgage, int groupSize, String color, int rent) {
        super(name, position, type, cost, mortgage, groupSize, color, rent);
    }

    public String getRent(){
        return DESCRIPTION_RENT;
    }
}
