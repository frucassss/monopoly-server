package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonopolyServiceTest {
    MonopolyService monopolyService = new MonopolyService();

    @BeforeEach
    void clearMonopolyService() {
        monopolyService = new MonopolyService();
        monopolyService.createGame("test101", 2);
    }

    void makeGameFullOfPlayers() {
        monopolyService.joinGame("test101_0", "Michiel");
        monopolyService.joinGame("test101_0", "Thibo");
    }

    void addPropertyToPlayerEnvironment(String playerName, String propertyName) {
        Game game = monopolyService.getGame("test101_0");
        game.setCurrentPlayer(monopolyService.getGame("test101_0").findPlayer(playerName));
        while (!game.receiveCurrentPlayer().receiveCurrentTile().getName().equals(propertyName)) {
            game.setCurrentPlayer(monopolyService.getGame("test101_0").findPlayer(playerName));
            while (game.receiveCurrentPlayer().getGetOutOfJailFreeCards() != 0) {
                game.receiveCurrentPlayer().setJailed(true);
                game.receiveCurrentPlayer().useGetOutOfJailFreeCard();
            }
            monopolyService.rollDice("test101_0", playerName);
            game.receiveLastTurn().makeFinished();
        }
        game.setCurrentPlayer(monopolyService.getGame("test101_0").findPlayer(playerName));
        game.receiveLastTurn().makeUnfinished();
        monopolyService.buyProperty("test101_0", playerName, propertyName);
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

    @Test
    void testBuyProperty() {
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment("Michiel", "Mediterranean");
        assertEquals("Mediterranean", monopolyService.getGame("test101_0").findPlayer("Michiel").getProperties().get(0).getProperty());
    }

    @Test
    void testBuyHouse() {
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment("Michiel", "Mediterranean");
        addPropertyToPlayerEnvironment("Michiel", "Baltic");
        monopolyService.buyHouse("test101_0", "Michiel", "Mediterranean");
        assertEquals(1, monopolyService.getGame("test101_0").findPlayer("Michiel").findProperty("Mediterranean").getHouseCount());
    }

    @Test
    void testBuyHotel() {
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment("Michiel", "Mediterranean");
        addPropertyToPlayerEnvironment("Michiel", "Baltic");

        for (int i = 0; i < 4; i++) {
            monopolyService.buyHouse("test101_0", "Michiel", "Mediterranean");
            monopolyService.buyHouse("test101_0", "Michiel", "Baltic");
        }

        monopolyService.buyHotel("test101_0", "Michiel", "Mediterranean");

        assertEquals(1, monopolyService.getGame("test101_0").findPlayer("Michiel").findProperty("Mediterranean").getHotelCount());
        assertEquals(4, monopolyService.getGame("test101_0").findPlayer("Michiel").findProperty("Baltic").getHouseCount());
    }


    @Test
    void testSellHotel(){
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment("Michiel", "Mediterranean");
        addPropertyToPlayerEnvironment("Michiel", "Baltic");

        for (int i = 0; i < 4; i++) {
            monopolyService.buyHouse("test101_0", "Michiel", "Mediterranean");
            monopolyService.buyHouse("test101_0", "Michiel", "Baltic");
        }

        monopolyService.buyHotel("test101_0", "Michiel", "Mediterranean");

        monopolyService.sellHotel("test101_0", "Michiel", "Mediterranean");
        assertEquals(0, monopolyService.getGame("test101_0").findPlayer("Michiel").findProperty("Mediterranean").getHotelCount());
    }

    @Test
    void testSellHouse(){
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment("Michiel", "Mediterranean");
        addPropertyToPlayerEnvironment("Michiel", "Baltic");

        for (int i = 0; i < 4; i++) {
            monopolyService.buyHouse("test101_0", "Michiel", "Mediterranean");
            monopolyService.buyHouse("test101_0", "Michiel", "Baltic");
        }

        monopolyService.sellHouse("test101_0", "Michiel", "Mediterranean");
        assertEquals(3,monopolyService.getGame("test101_0").findPlayer("Michiel").findProperty("Mediterranean").getHouseCount());

    }
    @Test
    void testDeclareBankrupt(){
        makeGameFullOfPlayers();
        monopolyService.declareBankruptcy("test101_0","Michiel");
        assertTrue(monopolyService.getGame("test101_0").findPlayer("Michiel").getBankrupt());
    }

    @Test
    void testPayPrisonFine(){
        makeGameFullOfPlayers();
        monopolyService.getGame("test101_0").findPlayer("Michiel").setJailed(true);
        monopolyService.payPrisonFine("test101_0","Michiel");
        assertFalse(monopolyService.getGame("test101_0").findPlayer("Michiel").getJailed());
        assertEquals(1450,monopolyService.getGame("test101_0").findPlayer("Michiel").getMoney());
    }

    @Test
    void testUseGetOutOfJailCard(){
        makeGameFullOfPlayers();
        monopolyService.getGame("test101_0").findPlayer("Michiel").setJailed(true);
        monopolyService.getGame("test101_0").findPlayer("Michiel").addGetOutOfJailFreeCard();
        monopolyService.useGetOutOfJailFreeCard("test101_0","Michiel");
        assertFalse(monopolyService.getGame("test101_0").findPlayer("Michiel").getJailed());
        assertEquals(0,monopolyService.getGame("test101_0").findPlayer("Michiel").getGetOutOfJailFreeCards());
    }


}