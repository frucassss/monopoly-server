package be.howest.ti.monopoly.logic.implementation.tile;

public class UtilityTile extends PropertyTile{

    public UtilityTile(String name, int position, String type, int cost, int mortgage, int groupSize, String color, int rent) {
        super(name, position, type, cost, mortgage, groupSize, color, rent);
    }

    public String getRent(){
        return "4 or 10 times the dice roll";
    }
}
