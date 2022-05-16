package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;

public class Rent {
    private final Player player;
    private final Game game;
    private final String propertyName;
    private final Player debtor;
    private int rent = 0;

    public Rent(Player player, Game game, String propertyName, Player debtor) {
        this.player = player;
        this.game = game;
        this.propertyName = propertyName;
        this.debtor = debtor;
    }

    public void collectRent() {
        Property property = player.findProperty(this.propertyName);
        String propertyType = property.receivePropertyTile().getType();
        switch (propertyType){
            case "street":
                receiveStreetRent(property);
                break;
            case "railroad":
                receiveRailRoadRent(property);
                break;
            case "utility":
                //TODO
                break;
        }
        pay(player, debtor);
    }

    private void receiveRailRoadRent(Property property) {
        RailroadTile tile = (RailroadTile) property.receivePropertyTile();
        switch (railRoadCount()){
            case 1:
                rent = 25;
                break;
            case 2:
                rent = 50;
                break;
            case 3:
                rent = 100;
                break;
            case 4:
                rent = 200;
                break;
        }
    }

    private void pay(Player player, Player debtor) {
        debtor.payRent(rent);
        player.collectRent(rent);
    }

    private void receiveStreetRent(Property property) {
        StreetTile tile = (StreetTile) property.receivePropertyTile();

        if (doesPlayerOwnTheWholeStreet(property) && noHotelOnProperty(property) && noHousesOnProperty(property)){
            rent =  2 * property.receivePropertyTile().receiveRent();
        }
        else if (!doesPlayerOwnTheWholeStreet(property) && noHotelOnProperty(property) && noHousesOnProperty(property)){
            rent =  property.receivePropertyTile().receiveRent();
        }
        else if(oneHouseOnProperty(property)){
            rent = tile.getRentWithOneHouse();
        }
        else if (twoHousesOnProperty(property)){
            rent = tile.getRentWithTwoHouses();
        }
        else if (threeHousesOnProperty(property)){
            rent = tile.getRentWithThreeHouses();
        }
        else if (fourHousesOnProperty(property)){
            rent = tile.getRentWithFourHouses();
        }
        else if (oneHotelOnProperty(property)){
            rent = tile.getRentWithHotel();
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

    private int railRoadCount(){
        int i = 0;
        for (Property p : player.getProperties()){
            if (p.receivePropertyTile().getType() == "railroad"){
                i++;
            }
        }
        return  i;
    }

    private int utilityCount(){
        int i = 0;
        for (Property p : player.getProperties()){
            if (p.receivePropertyTile().getType() == "utility"){
                i++;
            }
        }
        return i;
    }

    private boolean doesPlayerOwnTheWholeStreet(Property property){
        int propertyCounter = 0;
        for (Property propertiesFormPlayer : player.getProperties()) {
            if (propertiesFormPlayer.receiveColor().equals(property.receiveColor())) {
                propertyCounter++;
            }
        }
        if ((propertyCounter == property.receiveGroupSize())) {
            return true;
        }
        return false;
    }
}
