package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private final Player michiel = new Player("michiel");

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