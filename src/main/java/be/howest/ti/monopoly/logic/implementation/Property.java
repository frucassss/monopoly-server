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

    public void addHotel(){
        if(houseCount == 4 && hotelCount == 0){
            hotelCount += 1;
        }
    }

    public void removeHotel(){
        if (hotelCount > 0){
            hotelCount -= 1;
        }
    }

    public void mortgageProperty(){
        this.mortgage = true;
    }

    public void unMortgageProperty(){
        this.mortgage = false;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property1 = (Property) o;

        return property != null ? property.equals(property1.property) : property1.property == null;
    }

    @Override
    public int hashCode() {
        return property != null ? property.hashCode() : 0;
    }
}
