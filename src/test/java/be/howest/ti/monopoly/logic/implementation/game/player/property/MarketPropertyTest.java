package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.Mark;

import static org.junit.jupiter.api.Assertions.*;

class MarketPropertyTest {

    @Test
    void testBuyingProperty(){
        MonopolyService monopolyService = new MonopolyService();
        Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        MarketProperty marketProperty = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        marketProperty.buyProperty();

        assertEquals("Mediterranean", game.findPlayer("michiel").findProperty("Mediterranean").getProperty());
        assertEquals(1500 - game.findPlayer("michiel").findProperty("Mediterranean").receiveCost(),game.findPlayer("michiel").getMoney());
    }

    @Test
    void testBuyPropertyWhileSomebodyElseHasTheProperty(){
        MonopolyService monopolyService = new MonopolyService();
        Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));

        MarketProperty marketPropertyMichielMediterranean = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
        marketPropertyMichielMediterranean.buyProperty();

        game.setCurrentPlayer("thibo");
        game.findPlayer("thibo").setCurrentTile(monopolyService.getTile("Mediterranean"));
        assertThrows(IllegalMonopolyActionException.class,()->{
            MarketProperty marketPropertyThiboMediterranean = new MarketProperty(game.findPlayer("thibo"),game,"Mediterranean");
            marketPropertyThiboMediterranean.buyProperty();
        });
    }

    @Test
    void testBuyingPropertyWhileNotStandingOnIt(){
        MonopolyService monopolyService = new MonopolyService();
        Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        game.setCurrentPlayer("michiel");
        assertThrows(IllegalMonopolyActionException.class, ()->{
            MarketProperty marketProperty = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
            marketProperty.buyProperty();
        });
    }

    @Test
    void testBuyingPropertyWhileNotHavingEnoughMoney(){
        MonopolyService monopolyService = new MonopolyService();
        Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        game.setCurrentPlayer("michiel");
        game.findPlayer("michiel").setCurrentTile(monopolyService.getTile("Mediterranean"));
        game.findPlayer("michiel").pay(1500);
        assertThrows(IllegalMonopolyActionException.class, ()->{
            MarketProperty marketProperty = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
            marketProperty.buyProperty();
        });
    }

    @Test
    void testBuyingATileThatIsNotAProperty(){
        MonopolyService monopolyService = new MonopolyService();
        Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        game.setCurrentPlayer("michiel");
        assertThrows(IllegalMonopolyActionException.class, ()->{
            MarketProperty marketProperty = new MarketProperty(game.findPlayer("michiel"),game,"Mediterranean");
            marketProperty.buyProperty();
        });
    }
}