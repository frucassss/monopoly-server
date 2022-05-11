package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.player.property.PropertyCheck;
import be.howest.ti.monopoly.logic.implementation.tile.*;

public class Property {
    private PropertyTile propertyTile;
    private final int mortgageValue;
    private boolean mortgage = false;
    private int houseCount = 0;
    private int hotelCount = 0;
    private final PropertyCheck propertyCheck;

    public Property(Tile propertyTile) {
        checkIfTileTypeIsAtLeastAPropertyTile(propertyTile);
        makeRightTileTypeFromProperty(propertyTile);

        propertyCheck = new PropertyCheck(this);
        this.mortgageValue = this.propertyTile.getMortgage();
    }

    private void makeRightTileTypeFromProperty(Tile property) {
        switch (property.getType()) {
            case "street":
                this.propertyTile = (StreetTile) property;
                break;
            case "railroad":
                this.propertyTile = (RailroadTile) property;
                break;
            case "utility":
                this.propertyTile = (UtilityTile) property;
                break;
            default:
                this.propertyTile = (PropertyTile) property;
                break;
        }
    }

    public void addHouse() {
        propertyCheck.checkIfYouDontHaveMoreThen4HousesOnProperty();
        propertyCheck.checkIfPropertyIsStreetTile();
        houseCount += 1;
    }

    public void removeHouse() {
        propertyCheck.checkIfYouCanRemoveHouse();
        propertyCheck.checkIfPropertyIsStreetTile();
        houseCount -= 1;
    }

    public void addHotel() {
        propertyCheck.checkIfYouCanAddHotel();
        propertyCheck.checkIfPropertyIsStreetTile();
        hotelCount += 1;
    }

    public void removeHotel() {
        propertyCheck.checkIfYouCanRemoveHotel();
        propertyCheck.checkIfPropertyIsStreetTile();
        hotelCount -= 1;
    }

    public void tookMortgage() {
        this.mortgage = true;
    }

    public void settledMortgage() {
        this.mortgage = false;
    }

    // GETTERS

    public int receiveHousePrice() {
        propertyCheck.checkIfPropertyIsStreetTile();
        StreetTile propertyStreet = (StreetTile) this.propertyTile;
        return propertyStreet.getHousePrice();
    }

    public PropertyTile receivePropertyTile(){
        return propertyTile;
    }

    public int receiveMortgageValue() {
        return mortgageValue;
    }

    public int receiveGroupSize(){
        propertyCheck.checkIfPropertyIsStreetTile();
        StreetTile propertyStreet = (StreetTile) this.propertyTile;
        return propertyStreet.getGroupSize();
    }

    public String receiveColor(){
        return this.propertyTile.getColor();
    }

    public String getProperty() {
        return propertyTile.getName();
    }

    public boolean getMortgage() {
        return mortgage;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public int getHotelCount() {
        return hotelCount;
    }

    public void checkIfTileTypeIsAtLeastAPropertyTile(Tile property) {
        String type = property.getType();
        if (!(type.equals("street") || type.equals("railroad") || type.equals("utility"))) {
            throw new IllegalMonopolyActionException("You aren't allowed to have a regular tile as a property");
        }
    }
}
