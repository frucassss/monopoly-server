package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.checkers.player.property.MarketCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class Market {
    private Player player;
    private Game game;
    private String propertyName;
    private final PlayerCheck playerCheck;
    private final MarketCheck marketCheck;

    public Market(Player player, Game game, String propertyName){
        this.marketCheck = new MarketCheck(game,player);
        this.player = player;
        this.game = game;
        this.propertyName = propertyName;
        this.playerCheck = new PlayerCheck(player);
    }

    public void buyProperty() {
        marketCheck.checkIfItIsMyTurn();
        marketCheck.checkIfSomebodyHasProperty(propertyName);
        marketCheck.checkIfYouTryToBuyAProperty(propertyName);
        Property property = makePropertyFromTile(propertyName);
        playerCheck.checkIfIHaveEnoughMoney(property.receiveCost());
        marketCheck.checkIfImStandingOnProperty(property.receivePosition());
        player.pay(property.receiveCost());
        player.addProperty(property);
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
