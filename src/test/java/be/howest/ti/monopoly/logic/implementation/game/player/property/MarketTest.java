package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    MonopolyService monopolyService = new MonopolyService();
    Game game = new Game("hello",5,2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
    public MarketTest(){
        game.newPlayer("michiel");
        game.newPlayer("thibo");
    }

    @Test
    void testBuyingProperty(){
        Player player = game.findPlayer("michiel");
        new Turn(game,  player);
        game.setCurrentPlayer(player);
        player.setCurrentTile(monopolyService.getTile("Mediterranean"));

        Market marketMichielMediterranean = new Market(player,game,"Mediterranean");
        marketMichielMediterranean.buyProperty();

        Property property =  player.findProperty("Mediterranean");
        assertEquals("Mediterranean", property.getProperty());
    }

    @Test
    void testBuyPropertyWhileSomebodyElseHasTheProperty(){
        Player player1 = game.findPlayer("thibo");
        Player player2 = game.findPlayer("michiel");

        new Turn(game,  player2);
        game.setCurrentPlayer(player2);
        player2.setCurrentTile(monopolyService.getTile("Mediterranean"));

        Market marketMichielMediterranean = new Market(player2 ,game,"Mediterranean");
        marketMichielMediterranean.buyProperty();

        game.setCurrentPlayer(player1);
        new Turn(game,  player1);
        game.setCurrentPlayer(player1);
        player1.setCurrentTile(monopolyService.getTile("Mediterranean"));
        Market marketThiboMediterranean = new Market(player1 ,game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketThiboMediterranean::buyProperty);
    }

    @Test
    void testBuyingPropertyWhileNotStandingOnIt(){
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }

    @Test
    void testBuyingATileThatIsNotAProperty(){
        Market marketMichielMediterranean = new Market(game.findPlayer("michiel"),game,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, marketMichielMediterranean::buyProperty);
    }
}