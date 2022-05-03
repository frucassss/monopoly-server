package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class Property {
    private final Tile property;
    private boolean mortgage = false;
    private int houseCount = 0;
    private int hotelCount = 0;

    public Property(Tile property) {
        this.property = property;
    }

    public void addHouse(){
        if(houseCount < 4){
            hotelCount += 1;
        }
    }
    public void removeHouse(){
        if(houseCount > 0){
            houseCount -= 1;
        }
    }

    // GETTERS

    public String getProperty() {
        return property.getName();
    }

    public boolean isMortgage() {
        return mortgage;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public int getHotelCount() {
        return hotelCount;
    }
}
