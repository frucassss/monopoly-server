package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.game.player.Player;

import java.util.List;

public class Chance {

    private final String chanceDescription;
    private final Move move;
    private final Player player;
    private final Game game;

    public Chance(String chanceDescription, Move move, Player player, Game game) {
        this.chanceDescription = chanceDescription;
        this.move = move;
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
        for (Player player : gamePlayers){
            if (!(player.equals(this.player))){
                player.collect(amount);
            }
        }
    }

    private void goToJail() {
    }

    private void generalRepair() {
    }

    private void advanceToNearestUtility() {
    }

    private void goBack3Spaces() {
    }

    private void advanceToNearestRailroad() {
    }

    private void advanceTo(String propertyName) {

    }
}
