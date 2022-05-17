package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    @Test
    void testPay(){
        MonopolyService monopolyService = new MonopolyService();
        Game game = new Game("Hallo",1,2,monopolyService.getChance(),monopolyService.getCommunityChest(),monopolyService.getTiles());
        Player michiel = new Player("michiel");
        Chance payer = new Chance("Speeding fine $15",michiel,game);

        assertEquals(1485,michiel.getMoney());
        }
    }
