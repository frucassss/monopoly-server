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

    public void checkIfYouTryToBuyAProperty(String tileName) {
        Tile tile = game.receiveTile(tileName);
        if (!doesTileQualifyAsAProperty(tile)) {
            throw new IllegalMonopolyActionException("You aren't allowed to have a regular tile as a property");
        }
    }

    public void checkIfSomebodyHasProperty(String propertyName) {
        for (Player other : game.getPlayers()) {
            if (other.doIHaveProperty(propertyName)) {
                throw new IllegalMonopolyActionException("Property is already owned by a player!");
            }
        }
    }

    public void checkIfItIsMyTurn() {
        if (!game.receiveCurrentPlayer().equals(player)){
            throw new IllegalMonopolyActionException("It's not your turn!");
        }
    }

    private boolean doesTileQualifyAsAProperty(Tile tile){
        return (tile.getType().equals("street") || tile.getType().equals("railroad") || tile.getType().equals("utility"));
    }
}
