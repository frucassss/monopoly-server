package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.tile.PropertyTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class Property {
    private final PropertyTile property;
    protected final int mortgageValue;
    private boolean mortgage = false;
    private int houseCount = 0;
    private int hotelCount = 0;

    public Property(Tile property) {
        checkIfTileTypeIsProperty(property);
        this.property = (PropertyTile) property;
        this.mortgageValue = this.property.getMortgage();
    }

    public void checkIfTileTypeIsProperty(Tile property) {
        if (!(property.getType().equals("street") || property.getType().equals("railroad") || property.getType().equals("utility"))) {
            throw new IllegalMonopolyActionException("You're trying to make a property of a non property");
        }
    }

    public void addHouse() {
        if (houseCount < 4) {
            houseCount += 1;
        } else {
            throw new IllegalMonopolyActionException("You already have 4 houses on the property");
        }
    }

    public void removeHouse() {
        if (houseCount > 0) {
            houseCount -= 1;
        } else {
            throw new IllegalMonopolyActionException("You don't have houses to remove");
        }
    }

    public void addHotel() {
        if (houseCount == 4 && hotelCount == 0) {
            hotelCount += 1;
        } else {
            throw new IllegalMonopolyActionException("You can't buy an hotel atm");
        }
    }

    public void removeHotel() {
        if (hotelCount > 0) {
            hotelCount -= 1;
        } else {
            throw new IllegalMonopolyActionException("You can't remove a hotel you don't have");
        }
    }

    public void mortgageProperty() {
        this.mortgage = true;
    }

    public void unMortgageProperty() {
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
