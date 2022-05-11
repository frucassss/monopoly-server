package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.Mark;

import static org.junit.jupiter.api.Assertions.*;

class MarketPropertyTest {
    MonopolyService monopolyService = new MonopolyService();
    Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
    MarketPropertyTest(){
        game.newPlayer("michiel");
        game.newPlayer("thibo");
    }

    @Test
    void testBuyingProperty(){
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        MarketProperty marketPropertyMichielMediterranean = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        marketPropertyMichielMediterranean.buyProperty();

        assertEquals("Mediterranean", game.findPlayer("michiel").findProperty("Mediterranean").getProperty());
        assertEquals(1500 - game.findPlayer("michiel").findProperty("Mediterranean").receiveCost(),game.findPlayer("michiel").getMoney());
    }

    @Test
    void testBuyPropertyWhileSomebodyElseHasTheProperty(){
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        MarketProperty marketPropertyMichielMediterranean = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        marketPropertyMichielMediterranean.buyProperty();

        game.setCurrentPlayer("thibo");
        game.findPlayer("thibo").setCurrentTile(monopolyService.getTile("Mediterranean"));
        MarketProperty marketPropertyThiboMediterranean = new MarketProperty(game.findPlayer("thibo"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketPropertyThiboMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotStandingOnIt(){
        game.setCurrentPlayer("michiel");
        MarketProperty marketPropertyMichielMediterranean = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketPropertyMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotHavingEnoughMoney(){
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));
        game.findPlayer("michiel").pay(1500);
        MarketProperty marketPropertyMichielMediterranean = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketPropertyMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingATileThatIsNotAProperty(){
        game.setCurrentPlayer("michiel");
        MarketProperty marketPropertyMichielMediterranean = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketPropertyMichielMediterranean::buyProperty);
    }
}