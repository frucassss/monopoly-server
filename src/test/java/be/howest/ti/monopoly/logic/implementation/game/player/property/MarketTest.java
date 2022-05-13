package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    MonopolyService monopolyService = new MonopolyService();
    Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
    MarketTest(){
        game.newPlayer("michiel");
        game.newPlayer("thibo");
    }

    @Test
    void testBuyingProperty(){
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        marketMichielMediterranean.buyProperty();

        assertEquals("Mediterranean", game.findPlayer("michiel").findProperty("Mediterranean").getProperty());
        assertEquals(1500 - game.findPlayer("michiel").findProperty("Mediterranean").receiveCost(),game.findPlayer("michiel").getMoney());
    }

    @Test
    void testBuyPropertyWhileSomebodyElseHasTheProperty(){
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        marketMichielMediterranean.buyProperty();

        game.setCurrentPlayer("thibo");
        game.findPlayer("thibo").setCurrentTile(monopolyService.getTile("Mediterranean"));
        Market marketThiboMediterranean = new Market(game.findPlayer("thibo"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketThiboMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotStandingOnIt(){
        game.setCurrentPlayer("michiel");
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotHavingEnoughMoney(){
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));
        game.findPlayer("michiel").pay(1500);
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingATileThatIsNotAProperty(){
        game.setCurrentPlayer("michiel");
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }
}