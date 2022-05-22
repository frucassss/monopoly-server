package be.howest.ti.monopoly.logic.implementation.checkers.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.PropertyTile;

public class PropertyCheck {

    private final Property property;
    private final PropertyTile propertyTile;

    private static final int MAX_HOUSE_COUNT = 4;

    public PropertyCheck(Property property){
        this.property = property;
        this.propertyTile = property.receivePropertyTile();
    }

    public void checkIfYouCanRemoveHotel() {
        if (property.getHotelCount() == 0) {
            throw new IllegalMonopolyActionException("You can't remove a hotel you don't have");
        }
    }

    public void checkIfYouCanRemoveHouse() {
        if (property.getHouseCount() == 0) {
            throw new IllegalMonopolyActionException("You don't have houses to remove");
        }
    }

    public void checkIfYouCanAddHotel() {
        if (property.getHouseCount() != MAX_HOUSE_COUNT) {
            throw new IllegalMonopolyActionException("You can't buy an hotel because you don't have 4 houses");
        } else if (property.getHotelCount() > 0) {
            throw new IllegalMonopolyActionException("You can't buy an hotel because you already have one");
        }
    }

    public void checkIfYouDontHaveMoreThen4HousesOnProperty() {
        if (property.getHouseCount() == MAX_HOUSE_COUNT) {
            throw new IllegalMonopolyActionException("You already have 4 houses on the property");
        }
    }

    public void checkIfPropertyIsStreetTile() {
        if (!propertyTile.getType().equals("street")) {
            throw new IllegalMonopolyActionException("Your property isn't a streetTile");
        }
    }
}
