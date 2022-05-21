package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonopolyServiceTest {
    MonopolyService monopolyService = new MonopolyService();
    String prefix = "test101_0";
    String playerNameMichiel = "Michiel";
    
    @BeforeEach
    void clearMonopolyService() {
        monopolyService = new MonopolyService();
        monopolyService.createGame("test101", 2);
    }

    void makeGameFullOfPlayers() {
        monopolyService.joinGame(prefix, playerNameMichiel);
        monopolyService.joinGame(prefix, "Thibo");
    }

    void addPropertyToPlayerEnvironment(String playerName, String propertyName) {
        Game game = monopolyService.getGame(prefix);
        game.setCurrentPlayer(monopolyService.getGame(prefix).findPlayer(playerName));
        while (!game.receiveCurrentPlayer().receiveCurrentTile().getName().equals(propertyName)) {
            game.setCurrentPlayer(monopolyService.getGame(prefix).findPlayer(playerName));
            while (game.receiveCurrentPlayer().getGetOutOfJailFreeCards() != 0) {
                game.receiveCurrentPlayer().setJailed(true);
                game.receiveCurrentPlayer().useGetOutOfJailFreeCard();
            }
            monopolyService.rollDice(prefix, playerName);
            game.receiveLastTurn().makeFinished();
        }
        game.setCurrentPlayer(monopolyService.getGame(prefix).findPlayer(playerName));
        game.receiveLastTurn().makeUnfinished();
        monopolyService.buyProperty(prefix, playerName, propertyName);
    }

    @Test
    void testMakingTheGameWithSameGameId() {
        assertThrows(IllegalArgumentException.class, () -> {
            monopolyService.addGame(prefix, new Game("test101", 1, 2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles()));
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
        monopolyService.joinGame(prefix, playerNameMichiel);
        assertEquals(playerNameMichiel, monopolyService.getGame(prefix).getPlayers().get(0).getName());
    }

    @Test
    void testBuyProperty() {
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel, "Mediterranean");
        assertEquals("Mediterranean", monopolyService.getGame(prefix).findPlayer(playerNameMichiel).getProperties().get(0).getProperty());
    }

    @Test
    void testBuyHouse() {
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel, "Mediterranean");
        addPropertyToPlayerEnvironment(playerNameMichiel, "Baltic");
        monopolyService.buyHouse(prefix, playerNameMichiel, "Mediterranean");
        assertEquals(1, monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Mediterranean").getHouseCount());
    }

    @Test
    void testBuyHotel() {
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel, "Mediterranean");
        addPropertyToPlayerEnvironment(playerNameMichiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            monopolyService.buyHouse(prefix, playerNameMichiel, "Mediterranean");
            monopolyService.buyHouse(prefix, playerNameMichiel, "Baltic");
        }

        monopolyService.buyHotel(prefix, playerNameMichiel, "Mediterranean");

        assertEquals(1, monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Mediterranean").getHotelCount());
        assertEquals(4, monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Baltic").getHouseCount());
    }


    @Test
    void testSellHotel(){
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel, "Mediterranean");
        addPropertyToPlayerEnvironment(playerNameMichiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            monopolyService.buyHouse(prefix, playerNameMichiel, "Mediterranean");
            monopolyService.buyHouse(prefix, playerNameMichiel, "Baltic");
        }

        monopolyService.buyHotel(prefix, playerNameMichiel, "Mediterranean");

        monopolyService.sellHotel(prefix, playerNameMichiel, "Mediterranean");
        assertEquals(0, monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Mediterranean").getHotelCount());
    }

    @Test
    void testSellHouse(){
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel, "Mediterranean");
        addPropertyToPlayerEnvironment(playerNameMichiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            monopolyService.buyHouse(prefix, playerNameMichiel, "Mediterranean");
            monopolyService.buyHouse(prefix, playerNameMichiel, "Baltic");
        }

        monopolyService.sellHouse(prefix, playerNameMichiel, "Mediterranean");
        assertEquals(3,monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Mediterranean").getHouseCount());

    }
    @Test
    void testDeclareBankrupt(){
        makeGameFullOfPlayers();
        monopolyService.declareBankruptcy(prefix,playerNameMichiel);
        assertTrue(monopolyService.getGame(prefix).findPlayer(playerNameMichiel).getBankrupt());
    }

    @Test
    void testPayPrisonFine(){
        makeGameFullOfPlayers();
        monopolyService.getGame(prefix).findPlayer(playerNameMichiel).setJailed(true);
        monopolyService.payPrisonFine(prefix,playerNameMichiel);
        assertFalse(monopolyService.getGame(prefix).findPlayer(playerNameMichiel).getJailed());
        assertEquals(1450,monopolyService.getGame(prefix).findPlayer(playerNameMichiel).getMoney());
    }

    @Test
    void testUseGetOutOfJailCard(){
        makeGameFullOfPlayers();
        monopolyService.getGame(prefix).findPlayer(playerNameMichiel).setJailed(true);
        monopolyService.getGame(prefix).findPlayer(playerNameMichiel).addGetOutOfJailFreeCard();
        monopolyService.useGetOutOfJailFreeCard(prefix,playerNameMichiel);
        assertFalse(monopolyService.getGame(prefix).findPlayer(playerNameMichiel).getJailed());
        assertEquals(0,monopolyService.getGame(prefix).findPlayer(playerNameMichiel).getGetOutOfJailFreeCards());
    }

    @Test
    void testMortgage(){
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel,"Mediterranean");
        monopolyService.takeMortgage(prefix,playerNameMichiel,"Mediterranean");
        assertTrue(monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Mediterranean").getMortgage());
    }

    @Test
    void testSettleMortgage(){
        makeGameFullOfPlayers();
        addPropertyToPlayerEnvironment(playerNameMichiel,"Mediterranean");
        monopolyService.takeMortgage(prefix,playerNameMichiel,"Mediterranean");
        monopolyService.settleMortgage(prefix,playerNameMichiel,"Mediterranean");
        assertFalse(monopolyService.getGame(prefix).findPlayer(playerNameMichiel).findProperty("Mediterranean").getMortgage());
    }
}