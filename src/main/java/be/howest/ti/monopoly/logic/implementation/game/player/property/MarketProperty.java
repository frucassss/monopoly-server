package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class MarketProperty {
    private Player player;
    private Game game;
    private String propertyName;
    private final PlayerCheck playerCheck;

    public MarketProperty(Player player, Game game, String propertyName){
        this.player = player;
        this.game = game;
        this.propertyName = propertyName;
        this.playerCheck = new PlayerCheck(player);
    }

    public void buyProperty() {
        checkIfItIsMyTurn();
        checkIfSomebodyHasProperty(propertyName);
        checkIfYouTryToBuyAProperty(propertyName);
        Property property = makePropertyFromTile(propertyName);
        checkIfIHaveEnoughMoney(property.receiveCost());
        checkIfImStandingOnProperty(property.receivePosition());
        player.pay(property.receiveCost());
        player.addProperty(property);
    }

    private void checkIfIHaveEnoughMoney(int amount) {
        if (amount > player.getMoney()) {
            throw new IllegalMonopolyActionException("You don't have enough money");
        }
    }

    private void checkIfImStandingOnProperty(int propertyPosition) {
        if (propertyPosition != player.receiveCurrentTile().getPosition()) {
            throw new IllegalMonopolyActionException("You need to stand on property before you can buy it");
        }
    }

    private void checkIfYouTryToBuyAProperty(String propertyName) {
        for (Tile tile : game.receiveTiles()) {
            if (tile.getName().equals(propertyName)) {
                if (!(tile.getType().equals("street") || tile.getType().equals("railroad") || tile.getType().equals("utility"))) {
                    throw new IllegalMonopolyActionException("You aren't allowed to have a regular tile as a property");
                }
            }
        }
    }

    private void checkIfSomebodyHasProperty(String propertyName) {
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

    private void checkIfItIsMyTurn() {
        if (!game.getCurrentPlayer().equals(player.getName())){
            throw new IllegalMonopolyActionException("It's not your turn!");
        }
    }

    private Property makePropertyFromTile(String propertyName) {
        for (Tile tile : game.receiveTiles()) {
            if (tile.getName().equals(propertyName)) {
                return new Property(tile);
            }
        }
        throw new IllegalMonopolyActionException("Couldn't find property in tiles");
    }
}
