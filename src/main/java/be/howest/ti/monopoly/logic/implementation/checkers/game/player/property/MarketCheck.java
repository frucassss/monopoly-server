package be.howest.ti.monopoly.logic.implementation.checkers.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class MarketCheck {
    private final Game game;
    private final Player player;

    public MarketCheck(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public void checkIfImStandingOnProperty(int propertyPosition) {
        if (propertyPosition != player.receiveCurrentTile().getPosition()) {
            throw new IllegalMonopolyActionException("You need to stand on property before you can buy it");
        }
    }

    public void checkIfYouTryToBuyAProperty(String propertyName) {
        for (Tile tile : game.receiveTiles()) {
            if (tile.getName().equals(propertyName)) {
                if (!(tile.getType().equals("street") || tile.getType().equals("railroad") || tile.getType().equals("utility"))) {
                    throw new IllegalMonopolyActionException("You aren't allowed to have a regular tile as a property");
                }
            }
        }
    }

    public void checkIfSomebodyHasProperty(String propertyName) {
        for (String key : game.getPlayers().keySet()) {
            Player player = game.getPlayers().get(key);
            if (player.doIHaveProperty(propertyName)) {
                if (player.getName().equals(player.getName())) {
                    throw new IllegalMonopolyActionException("You already have this property");
                } else {
                    throw new IllegalMonopolyActionException("Somebody already has this property");
                }
            }
        }
    }

    public void checkIfItIsMyTurn() {
        if (!game.getCurrentPlayer().equals(player.getName())){
            throw new IllegalMonopolyActionException("It's not your turn!");
        }
    }
}
