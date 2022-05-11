package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Improve;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Mortgage;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
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

}