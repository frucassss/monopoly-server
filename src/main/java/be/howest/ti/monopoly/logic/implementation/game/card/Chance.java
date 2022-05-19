package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Move;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

import java.util.List;

public class Chance extends Card {
    private static final int HOUSE_REPAIR_COST = 25;
    private static final int HOTEL_REPAIR_COST = 100;

    private static final int LAST_BOARD_POSITION = 39;
    private static final int MIDDLE_BOARD_POSITION = 20;

    public Chance(String chanceDescription, Player player, Game game, Move move) {
        super(player, game, move);
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
            case "Advance to the nearest Railroad.":
                advanceToNearestRailroad();
                break;
            case "Advance token to nearest Utility.":
                advanceToNearestUtility();
                break;
            case "Bank pays you dividend of $50":
                player.collect(50);
                break;
            case "Get Out of Jail Free":
                player.addGetOutOfJailFreeCard();
                break;
            case "Go Back 3 Spaces":
                goBack3Spaces();
                break;
            case "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200":
                goToJail();
                break;
            case "Make general repairs on all your property. For each house pay $25. For each hotel pay $100":
                generalRepair(HOUSE_REPAIR_COST, HOTEL_REPAIR_COST);
                break;
            case "Speeding fine $15":
                player.pay(15);
                break;
            case "Take a trip to Reading Railroad. If you pass Go, collect $200":
                advanceTo("Reading RR");
                break;
            case "You have been elected Chairman of the Board. Pay each player $50":
                payEachPlayer();
                break;
            case "Your building loan matures. Collect $150":
                player.collect(150);
                break;
            default:
                break;
        }
    }

    private void payEachPlayer() {
        int amountOfPlayersInGameMinusMovePlayer = game.getNumberOfPlayers() - 1;
        this.player.pay(50 * amountOfPlayersInGameMinusMovePlayer);
        List<Player> gamePlayers = game.getPlayers();
        for (Player gamePlayer : gamePlayers) {
            if (!(gamePlayer.equals(this.player))) {
                gamePlayer.collect(50);
            }
        }
    }

    public void advanceToNearestUtility() {
        int playerTilePosition = move.receiveTile().getPosition();
        if (playerTilePosition < MIDDLE_BOARD_POSITION) {
            advanceTo("Electric Company");
        } else {
            advanceTo("Water Works");
        }
    }

    public void advanceToNearestRailroad() {
        int playerTilePosition = move.receiveTile().getPosition();
        if (playerTilePosition < 7) {
            advanceTo("Reading RR");
        } else if (playerTilePosition < MIDDLE_BOARD_POSITION) {
            advanceTo("Pennsylvania RR");
        } else if (playerTilePosition < 30) {
            advanceTo("Baltimore and Ohio RR");
        } else {
            advanceTo("Short Line RR");
        }
    }

    public void goBack3Spaces() {
        int playerCurrentPosition = move.receiveTile().getPosition();
        int newPlayerPosition;
        if (playerCurrentPosition >= 3) {
            newPlayerPosition = playerCurrentPosition - 3;
        } else {
            newPlayerPosition = (LAST_BOARD_POSITION - (2 - playerCurrentPosition));
        }
        moveTile = game.receiveTileOnPosition(newPlayerPosition);
    }
}
