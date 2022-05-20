package be.howest.ti.monopoly.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceAdapterTest {
    ServiceAdapter serviceAdapter = new ServiceAdapter();
    String gameId = "hallo";
    String propertyName = "lalaLand";
    String playerName = "zombie101";
    @Test
    void testServiceAdapterThrows(){
        assertThrows(UnsupportedOperationException.class,()->{
           serviceAdapter.buyProperty(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getVersion();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getTiles();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getChance();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getCommunityChest();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getGame(gameId);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.createGame(gameId,5);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.joinGame(gameId,playerName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getGamesFromService();
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getTile(propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.getTile(5);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.buyHouse(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.declareBankruptcy(gameId,playerName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.sellHotel(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.sellHotel(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.payPrisonFine(gameId,playerName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.useGetOutOfJailFreeCard(gameId,playerName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.dontBuyProperty(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.collectDebt(gameId,playerName,propertyName,"debtor");
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.buyHotel(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.rollDice(gameId,playerName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.takeMortgage(gameId,playerName,propertyName);
        });
        assertThrows(UnsupportedOperationException.class,()->{
            serviceAdapter.settleMortgage(gameId,playerName,propertyName);
        });
    }
}