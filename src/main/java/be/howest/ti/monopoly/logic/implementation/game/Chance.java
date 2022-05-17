package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.List;

public class Chance {

    private final Player player;
    private final Game game;

    private Tile newTile = null;
    private String moveDescription = null;

    public Chance(String chanceDescription, Player player, Game game) {
        this.player = player;
        this.game = game;
        processDifferentChanceCards(chanceDescription);
    }

    private void processDifferentChanceCards(String description) {
        switch (description) {
            case "Advance to Boardwalk":
                advanceTo("Boardwalk");
                break;
            case "Advance to Go (Collect $200)":
                advanceTo("Go");
                break;
            case "Advance to Illinois Avenue. If you pass Go, collect $200":
                advanceTo("Illinois Avenue");
                break;
            case "Advance to St. Charles Place. If you pass Go, collect $200":
                advanceTo("Saint Charles Place");
                break;
            case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled":
                advanceToNearestRailroad();
                break;
            case "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.":
                advanceToNearestUtility();
                break;
            case "Bank pays you dividend of $50":
                player.collect(50);
                break;
            case "Get Out of Jail Free":
                player.addGetOutOfJailFreeCard();
                break;
            case "Go Back 3 spaces":
                goBack3Spaces();
                break;
            case "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200":
                goToJail();
                break;
            case "Make general repairs on all your property. For each house pay $25. For each hotel pay $100":
                generalRepair();
                break;
            case "Speeding fine $15":
                player.pay(15);
                break;
            case "Take a trip to Reading Railroad. If you pass Go, collect $200":
                advanceTo("Reading RR");
                break;
            case "You have been elected Chairman of the Board. Pay each player $50":
                payEachPlayer(50);
                break;
            case "Your building loan matures. Collect $150":
                player.collect(150);
                break;
        }
    }

    private void payEachPlayer(int amount) {
        int amountOfPlayersInGameMinusMovePlayer = game.getNumberOfPlayers() - 1;
        this.player.pay(amount * amountOfPlayersInGameMinusMovePlayer);
        List<Player> gamePlayers = game.getPlayers();
        for (Player gamePlayer : gamePlayers) {
            if (!(gamePlayer.equals(this.player))) {
                gamePlayer.collect(amount);
            }
        }
    }

    private void goToJail() {
        newTile = game.receiveTileOnName("Jail");
        moveDescription = "Chance made you go to jail";
        player.setJailed(true);
    }

    private void generalRepair() {
        int houseRepairCost = 25;
        int hotelRepairCost = 100;
        int totalAmountToPay = 0;
        List<Property> playerProperties = player.getProperties();
        for (Property property : playerProperties) {
            totalAmountToPay += (property.getHouseCount() * houseRepairCost);
            totalAmountToPay += (property.getHotelCount() * hotelRepairCost);
        }
        player.pay(totalAmountToPay);
    }

    private void advanceToNearestUtility() {
        Tile waterWorksUtility = game.receiveTileOnPosition(12);
        Tile electricUtility = game.receiveTileOnPosition(28);
        int playerTilePosition = player.receiveCurrentTile().getPosition();
        if (playerTilePosition < 20){
            newTile = waterWorksUtility;
            moveDescription = "You have to go to Water Works";
        }
        else if (playerTilePosition > 20){
            newTile = electricUtility;
            moveDescription = "You have to go to Electric Company";
        }
    }

    private void goBack3Spaces() {
        int playerCurrentPosition = player.receiveCurrentTile().getPosition();
        int newPlayerPosition;
        if (playerCurrentPosition >= 3){
            newPlayerPosition = playerCurrentPosition - 3;
        } else {
            newPlayerPosition = 40 - (3 - playerCurrentPosition);
        }
        newTile = game.receiveTileOnPosition(newPlayerPosition);
        moveDescription = "You had to go back 3 spaces and now you're standing on: " + newTile;
    }

    private void advanceToNearestRailroad() {
    }

    private void advanceTo(String tile) {
        Tile tileToAdvanceTo = game.receiveTileOnName(tile);
        newTile = tileToAdvanceTo;
        moveDescription = "You have to go to: " + tile;
    }

    public Tile getTile() {
        return newTile;
    }

    public String getMoveDescription() {
        return moveDescription;
    }
}
