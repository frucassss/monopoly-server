package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.tile.*;

public class Property {
    private PropertyTile property;
    private final int mortgageValue;
    private boolean mortgage = false;
    private int houseCount = 0;
    private int hotelCount = 0;

    public Property(Tile property) {
        checkIfTileTypeIsAtLeastAPropertyTile(property);
        makeRightTileTypeFromProperty(property);
        this.mortgageValue = this.property.getMortgage();
    }

    public void makeRightTileTypeFromProperty(Tile property) {
        switch (property.getType()) {
            case "street":
                this.property = (StreetTile) property;
                break;
            case "railroad":
                this.property = (RailroadTile) property;
                break;
            case "utility":
                this.property = (UtilityTile) property;
                break;
            default:
                this.property = (PropertyTile) property;
                break;
        }
    }

    public void checkIfTileTypeIsAtLeastAPropertyTile(Tile property) {
        if (!(property.getType().equals("street") || property.getType().equals("railroad") || property.getType().equals("utility"))) {
            throw new IllegalMonopolyActionException("You aren't allowed to have a regular tile as a property");
        }
    }

    public void addHouse() {
        checkIfYouDontHaveMoreThen4HousesOnProperty();
        checkIfPropertyIsStreetTile();
        houseCount += 1;
    }

    public void checkIfYouDontHaveMoreThen4HousesOnProperty() {
        if (houseCount == 4) {
            throw new IllegalMonopolyActionException("You already have 4 houses on the property");
        }
    }

    public void checkIfPropertyIsStreetTile() {
        if (!this.property.getType().equals("street")) {
            throw new IllegalMonopolyActionException("Your property isn't a streetTile");
        }
    }

    public void removeHouse() {
        checkIfYouCanRemoveHouse();
        checkIfPropertyIsStreetTile();
        houseCount -= 1;
    }

    public void checkIfYouCanRemoveHouse() {
        if (houseCount == 0) {
            throw new IllegalMonopolyActionException("You don't have houses to remove");
        }
    }

    public void addHotel() {
        checkIfYouCanAddHotel();
        checkIfPropertyIsStreetTile();
        hotelCount += 1;
    }

    public void checkIfYouCanAddHotel() {
        if (houseCount != 4) {
            throw new IllegalMonopolyActionException("You can't buy an hotel because you don't have 4 houses");
        } else if (hotelCount > 0) {
            throw new IllegalMonopolyActionException("You can't buy an hotel because you already have one");
        }
    }

    public void removeHotel() {
        checkIfYouCanRemoveHotel();
        checkIfPropertyIsStreetTile();
        hotelCount -= 1;
    }

    public void checkIfYouCanRemoveHotel() {
        if (hotelCount == 0) {
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

    public int getHousePrice() {
        checkIfPropertyIsStreetTile();
        StreetTile propertyStreet = (StreetTile) this.property;
        return propertyStreet.getHousePrice();
    }

    public int getGroupSize(){
        checkIfPropertyIsStreetTile();
        StreetTile propertyStreet = (StreetTile) this.property;
        return propertyStreet.getGroupSize();
    }

    public String getStreetColor(){
        checkIfPropertyIsStreetTile();
        StreetTile propertyStreet = (StreetTile) this.property;
        return propertyStreet.getStreetColor();
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

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
