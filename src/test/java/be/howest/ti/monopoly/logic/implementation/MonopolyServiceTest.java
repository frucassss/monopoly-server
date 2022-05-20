package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonopolyServiceTest {
    MonopolyService monopolyService = new MonopolyService();

    MonopolyServiceTest() {
        monopolyService.createGame("test101", 2);
    }


    @Test
    void testMakingTheGameWithSameGameId() {
        assertThrows(IllegalArgumentException.class, () -> {
            monopolyService.addGame("test101_0", new Game("test101", 1, 2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles()));
        });
    }

    @Test
    void testCreateGame() {
        for (int i = 0; i < 10; i++) {
            monopolyService.createGame("test101", 2);
        }
        assertEquals(11, monopolyService.getGamesFromService().size());
    }

    @Test
    void testJoinGame() {
        monopolyService.joinGame("test101_0", "Michiel");
        assertEquals("Michiel", monopolyService.getGame("test101_0").getPlayers().get(0).getName());
    }


}