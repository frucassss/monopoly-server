package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    MonopolyService monopolyService = new MonopolyService();
    Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());

    public MarketTest(){
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        game.setCurrentPlayer(game.findPlayer("michiel"));
    }

    @Test
    void testBuyingProperty(){
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        marketMichielMediterranean.buyProperty();

        assertEquals("Mediterranean", game.findPlayer("michiel").findProperty("Mediterranean").getProperty());
        assertEquals(1500 - game.findPlayer("michiel").findProperty("Mediterranean").receiveCost(),game.findPlayer("michiel").getMoney());
    }

    @Test
    void testBuyPropertyWhileSomebodyElseHasTheProperty(){
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        marketMichielMediterranean.buyProperty();

        game.setCurrentPlayer(game.findPlayer("thibo"));
        game.findPlayer("thibo").setCurrentTile(monopolyService.getTile("Mediterranean"));
        Market marketThiboMediterranean = new Market(game.findPlayer("thibo"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketThiboMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotStandingOnIt(){
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotHavingEnoughMoney(){
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));
        game.findPlayer("michiel").pay(1500);
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingATileThatIsNotAProperty(){
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }
}