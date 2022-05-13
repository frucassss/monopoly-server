package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private final Player michiel = new Player("michiel", new Game("dummy", 1, 2, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

    @Test
    void testPayingNegativeNumber() {
        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.pay(-10);
        });
    }

    @Test
    void testCollectingNegativeNumber(){
        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.collect(-10);
        });
    }

    @Test
    void testPayingMyWayOutOfJail(){
        michiel.setJailed(true);
        michiel.payPrisonFine();
        assertEquals(1450,michiel.getMoney());
        assertFalse(michiel.getJailed());
    }

    @Test
    void testUsingAGetOutOfJailForFreeCardToGetOutOfJail(){
        michiel.addGetOutOfJailFreeCard();
        michiel.setJailed(true);
        michiel.useGetOutOfJailFreeCard();
        assertEquals(0,michiel.getGetOutOfJailFreeCards());
        assertFalse(michiel.getJailed());
    }

    @Test
    void testNotHavingTheMoneyToGetOutOfJail(){
        michiel.setJailed(true);
        michiel.pay(1451);
        assertThrows(IllegalMonopolyActionException.class, michiel::payPrisonFine);
    }

    @Test
    void testGettingOutOfJailWithCardWhileNotHavingACard(){
        michiel.setJailed(true);
        assertThrows(IllegalMonopolyActionException.class,michiel::useGetOutOfJailFreeCard);
    }

    @Test
    void testTryingToGetOutOfJailWhileNotBeingInJail(){
        assertThrows(IllegalMonopolyActionException.class,michiel::useGetOutOfJailFreeCard);
        assertThrows(IllegalMonopolyActionException.class,michiel::payPrisonFine);
    }
}