package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;


public class Rent {
    private final Player player;
    private final Game game;
    private final String propertyName;
    private final Player debtor;
    private int totalRent = 0;
    private static final int OF_BY_ONE_ERROR = 1;

    public Rent(Player player, Game game, String propertyName, Player debtor) {
        this.player = player;
        this.game = game;
        this.propertyName = propertyName;
        this.debtor = debtor;
    }

    public void collectRent() {
        Property property = player.findProperty(this.propertyName);
        String propertyType = property.receivePropertyTile().getType();
        checkIfDebtorIsOnPlayersTile(property);
        checkIfPlayerCanCollectRent();
        propertyTypeSwitch(propertyType, property);
        pay(player, debtor);
    }

    public void propertyTypeSwitch(String propertyType, Property property){
        switch (propertyType) {
            case "street":
                receiveStreetRent(property);
                break;
            case "railroad":
                receiveRailRoadRent();
                break;
            case "utility":
                receiveUtilityRent();
                break;
            default:
                throw new IllegalMonopolyActionException("You can not collect rent on this tile");
        }
    }

    private void receiveUtilityRent() {
        int totalDiceNumber = game.getLastDiceRoll()[0] + game.getLastDiceRoll()[1];
        if (utilityCount() == 1) {
            totalRent = totalDiceNumber * 4;
        } else if (utilityCount() == 2) {
            totalRent = totalDiceNumber * 10;
        }

    }


    private void receiveRailRoadRent() {
        int railroadCountMinOne = railRoadCount() - OF_BY_ONE_ERROR;
        totalRent = (int) Math.pow(2, railroadCountMinOne) * 25;
    }

    private void pay(Player player, Player debtor) {
        debtor.pay(totalRent);
        player.collect(totalRent);
    }

    private void receiveStreetRent(Property property) {
        StreetTile tile = (StreetTile) property.receivePropertyTile();

        if (doesPlayerOwnTheWholeStreet(property) && noHotelOnProperty(property) && noHousesOnProperty(property)) {
            totalRent = 2 * property.receivePropertyTile().receiveRent();
        } else if (!doesPlayerOwnTheWholeStreet(property) && noHotelOnProperty(property) && noHousesOnProperty(property)) {
            totalRent = property.receivePropertyTile().receiveRent();
        } else if (oneHouseOnProperty(property)) {
            totalRent = tile.getRentWithOneHouse();
        } else if (twoHousesOnProperty(property)) {
            totalRent = tile.getRentWithTwoHouses();
        } else if (threeHousesOnProperty(property)) {
            totalRent = tile.getRentWithThreeHouses();
        } else if (fourHousesOnProperty(property)) {
            totalRent = tile.getRentWithFourHouses();
        } else if (oneHotelOnProperty(property)) {
            totalRent = tile.getRentWithHotel();
        }
    }


    private boolean noHousesOnProperty(Property property) {
        return property.getHouseCount() == 0;
    }


    private boolean noHotelOnProperty(Property property) {
        return property.getHotelCount() == 0;
    }


    private boolean oneHouseOnProperty(Property property) {
        return property.getHouseCount() == 1;
    }

    private boolean oneHotelOnProperty(Property property) {
        return property.getHotelCount() == 1;
    }

    private boolean twoHousesOnProperty(Property property) {
        return property.getHouseCount() == 2;
    }

    private boolean threeHousesOnProperty(Property property) {
        return property.getHouseCount() == 3;
    }

    private boolean fourHousesOnProperty(Property property) {
        return property.getHouseCount() == 4;
    }

    private int railRoadCount() {
        int railroadCardCounter = 0;
        for (Property p : player.getProperties()) {
            if (p.receivePropertyTile().getType().equals("railroad")) {
                railroadCardCounter++;
            }
        }
        return railroadCardCounter;
    }

    private int utilityCount() {
        int utilityCardCounter = 0;
        for (Property p : player.getProperties()) {
            if (p.receivePropertyTile().getType().equals("utility")) {
                utilityCardCounter++;
            }
        }
        return utilityCardCounter;
    }

    private boolean doesPlayerOwnTheWholeStreet(Property property) {
        int propertyCounter = 0;
        for (Property propertiesFormPlayer : player.getProperties()) {
            if (propertiesFormPlayer.receiveColor().equals(property.receiveColor())) {
                propertyCounter++;
            }
        }
        return propertyCounter == property.receiveGroupSize();
    }

    private void checkIfDebtorIsOnPlayersTile(Property property){
        if (!debtor.getCurrentTile().equals(property.getProperty())) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. Maybe your are not the owner of said property, or the other player did not land on your property? Also, you can only claim rent untill the next dice roll.");
        }
    }

    private void checkIfPlayerCanCollectRent(){
        Turn turn = game.receiveLastTurn();
         if (!debtor.getName().equals(turn.getPlayer())){
             throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. Maybe your are not the owner of said property, or the other player did not land on your property? Also, you can only claim rent untill the next dice roll.");
         }
    }
}
