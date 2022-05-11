package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MortgageTest {

    private final Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    private final Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
    private final Property mediterraneanProperty = new Property(mediterraneanTile);

    @Test
    void testMortgage() {
        michiel.addProperty(mediterraneanProperty);

        Mortgage mortgage = new Mortgage(michiel, "Mediterranean");

        mortgage.takeMortgage();
        assertEquals(1530, michiel.getMoney());
    }

    @Test
    void testUnMortgage() {
        michiel.addProperty(mediterraneanProperty);

        Mortgage mortgage = new Mortgage(michiel, "Mediterranean");

        mortgage.takeMortgage();
        mortgage.settleMortgage();
        assertEquals(1497, michiel.getMoney());
    }

    @Test
    void testMortgageNoMoney() {
        michiel.addProperty(mediterraneanProperty);

        Mortgage mortgage = new Mortgage(michiel, "Mediterranean");

        mortgage.takeMortgage();
        michiel.pay(1500);
        assertThrows(IllegalMonopolyActionException.class, mortgage::settleMortgage);
    }

    @Test
    void testMortgageNotHavingTheProperty() {
        assertThrows(IllegalMonopolyActionException.class, () -> {
            new Mortgage(michiel, "Mediterranean");
        });
    }

}