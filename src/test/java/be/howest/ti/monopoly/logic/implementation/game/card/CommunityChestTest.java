package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;

import static org.junit.jupiter.api.Assertions.*;

class CommunityChestTest {

    private static MonopolyService monopolyService;

    @BeforeEach
    void init() {
        monopolyService = new MonopolyService();
    }



    @Test
    void payEachPlayerTest() {
        String description = "";
        while (!description.equals("It is your birthday. Collect $10 from every player")) {
            Game game = new Game("hallo", 1, 2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
            game.newPlayer("michiel");
            game.newPlayer("thibo");
            Player michiel = game.findPlayer("michiel");
            Player thibo = game.findPlayer("thibo");
            int balanceMichielBeforeTurn = michiel.getMoney();
            int balanceThiboBeforeTurn = thibo.getMoney();
            Turn turn = new Turn(game, michiel);
            description = turn.getMoves().get(0).getDescription();
            if (description.equals("It is your birthday. Collect $10 from every player")) {
                assertEquals(balanceMichielBeforeTurn + 10 , michiel.getMoney());
                assertEquals(balanceThiboBeforeTurn - 10, thibo.getMoney());
            }
        }

    }
}