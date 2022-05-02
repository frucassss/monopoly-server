package be.howest.ti.monopoly.logic.implementation.tile;

public class RailroadTile extends PropertyTile{

    public RailroadTile(String name, int position, String type, int cost, int mortgage, int groupSize, String color, int rent) {
        super(name, position, type, cost, mortgage, groupSize, color, rent);
    }

    public String getRent(){
        return "25 times the amount of RR the owner owns";
    }
}
