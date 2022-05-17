package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.game.player.property.RentCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;


public class Rent {

    private final Player creditor;
    private final Player debtor;
    private final Game game;
    private final Property property;

    private int rent = 0;
    private final String propertyType;
    private static final int RENT_MULTIPLIER_WHEN_CREDITOR_HAS_1_UTILITY = 4;
    private static final int RENT_MULTIPLIER_WHEN_CREDITOR_HAS_BOTH_UTILITIES = 10;
    private static final int MIN_RAILROAD_RENT = 25;

    public Rent(Game game, Player creditor, Player debtor, String propertyName) {
        this.creditor = creditor;
        this.debtor = debtor;
        this.game = game;
        this.property = creditor.findProperty(propertyName);
        this.propertyType = this.property.receivePropertyTile().getType();
    }

    public void collectRent() {
        RentCheck rentCheck = new RentCheck(this.game, this.debtor, this.property);
        rentCheck.checkIfDebtorIsOnPlayersTile();
        rentCheck.checkIfPlayerCanCollectRent();

        calculateRent();
        debtor.pay(rent);
        creditor.collect(rent);
    }

    public void calculateRent(){
        switch (this.propertyType) {
            case "street":
                calculateStreetRent();
                break;
            case "railroad":
                calculateRailRoadRent();
                break;
            case "utility":
                calculateUtilityRent();
                break;
            default:
                throw new IllegalMonopolyActionException("You can not collect rent on this tile");
        }
    }

    private void calculateUtilityRent() {
        int[] dices = game.getLastDiceRoll();
        int totalDiceNumber =  dices[0] + dices[1];
        int utilityCount = countPropertiesFromDebtorWithSameType(this.propertyType);

        if (utilityCount == 1) {
            rent = totalDiceNumber * RENT_MULTIPLIER_WHEN_CREDITOR_HAS_1_UTILITY;
        } else if (utilityCount == 2) {
            rent = totalDiceNumber * RENT_MULTIPLIER_WHEN_CREDITOR_HAS_BOTH_UTILITIES;
        }

    }


    private void calculateRailRoadRent() {
        int railroadCount = countPropertiesFromDebtorWithSameType(this.propertyType);
        rent = (int) Math.pow(2, railroadCount - 1) * MIN_RAILROAD_RENT;
    }


    private void calculateStreetRent() {
        StreetTile tile = (StreetTile) property.receivePropertyTile();

        if (doesCreditorOwnTheWholeStreet() && noHotelOnProperty() && noHousesOnProperty()) {
            rent = 2 * property.receivePropertyTile().receiveRent();
        }
        else if (!doesCreditorOwnTheWholeStreet() && noHotelOnProperty() && noHousesOnProperty()) {
            rent = property.receivePropertyTile().receiveRent();
        }
        else if (oneHouseOnProperty()) {
            rent = tile.getRentWithOneHouse();
        }
        else if (twoHousesOnProperty()) {
            rent = tile.getRentWithTwoHouses();
        }
        else if (threeHousesOnProperty()) {
            rent = tile.getRentWithThreeHouses();
        }
        else if (fourHousesOnProperty()) {
            rent = tile.getRentWithFourHouses();
        }
        else if (oneHotelOnProperty()) {
            rent = tile.getRentWithHotel();
        }
    }


    private boolean noHousesOnProperty() {
        return property.getHouseCount() == 0;
    }


    private boolean noHotelOnProperty() {
        return property.getHotelCount() == 0;
    }


    private boolean oneHouseOnProperty() {
        return property.getHouseCount() == 1;
    }

    private boolean oneHotelOnProperty() {
        return property.getHotelCount() == 1;
    }

    private boolean twoHousesOnProperty() {
        return property.getHouseCount() == 2;
    }

    private boolean threeHousesOnProperty() {
        return property.getHouseCount() == 3;
    }

    private boolean fourHousesOnProperty() {
        return property.getHouseCount() == 4;
    }

    private int countPropertiesFromDebtorWithSameType(String propertyType) {
        int count = 0;
        for (Property other : this.debtor.getProperties()) {
            if (other.receivePropertyTile().getType().equals(propertyType)) {
                count++;
            }
        }
        return count;
    }

    private boolean doesCreditorOwnTheWholeStreet() {
        int counter = 0;
        for (Property other : this.creditor.getProperties()) {
            if (other.receiveColor().equals(this.property.receiveColor())) {
                counter++;
            }
        }
        return counter == this.property.receiveGroupSize();
    }
}
