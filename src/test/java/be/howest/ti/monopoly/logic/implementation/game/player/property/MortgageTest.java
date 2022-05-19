package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MortgageTest {
    MonopolyService monopolyService = new MonopolyService();
    private final Player michiel = new Player("Michiel");
    private final Property mediterraneanProperty = new Property(monopolyService.getTile("Mediterranean"));
    private final Property balticProperty = new Property(monopolyService.getTile("Baltic"));

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

    @Test
    void testMortgageWhenYouHaveAHouseOnAProperty() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        Improve improveBaltic = new Improve(michiel,"Baltic");
        improveBaltic.buyHouse();
        Mortgage mortgageMediterranean = new Mortgage(michiel,"Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, mortgageMediterranean::takeMortgage);
    }

}