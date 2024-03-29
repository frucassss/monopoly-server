package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.implementation.checkers.game.player.property.MarketCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class Market {
    private final Player player;
    private final Game game;
    private final String propertyName;
    private final MarketCheck marketCheck;

    public Market(Player player, Game game, String propertyName){
        this.marketCheck = new MarketCheck(game,player);
        this.player = player;
        this.game = game;
        this.propertyName = propertyName;
    }

    public void buyProperty() {
        marketCheck.checkIfItIsMyTurn();
        marketCheck.checkIfTileIsProperty(propertyName);
        marketCheck.checkIfSomebodyHasProperty(propertyName);

        Property property = makePropertyFromTile();

        marketCheck.checkIfImStandingOnProperty(property.receivePosition());
        player.pay(property.receiveCost());
        player.addProperty(property);
        game.receiveLastTurn().makeFinished();
    }

    public void dontBuyProperty(){
        marketCheck.checkIfItIsMyTurn();
        marketCheck.checkIfTileIsProperty(propertyName);
        marketCheck.checkIfSomebodyHasProperty(propertyName);

        Property property = makePropertyFromTile();

        marketCheck.checkIfImStandingOnProperty(property.receivePosition());
        game.receiveLastTurn().makeFinished();
    }

    private Property makePropertyFromTile() {
        Tile tile = game.receiveTileOnName(propertyName);
        return new Property(tile);
    }
}
