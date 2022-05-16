package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImproveTest {

    private final Player michiel = new Player("Michiel");
    private final Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
    private final Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
    private final Property mediterraneanProperty = new Property(mediterraneanTile);
    private final Property balticProperty = new Property(balticTile);

    @Test
    void testFullyImproveStreet() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();
        improveBaltic.buyHotel();

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        assertEquals(4,michiel.findProperty("Mediterranean").getHouseCount());
        assertEquals(4,michiel.findProperty("Baltic").getHouseCount());
        assertEquals(1,michiel.findProperty("Baltic").getHotelCount());
        assertEquals(1, michiel.findProperty("Mediterranean").getHotelCount());
    }

    @Test
    void testNotAllowedToBuyHotel() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");

        improveMediterranean.buyHouse();

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHotel);
    }

    @Test
    void testNotAllowedToBuyHouse() {
        michiel.addProperty(mediterraneanProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHouse);
    }

    @Test
    void testTryToBuyHouseWithHotelAnd4Houses() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();
        improveBaltic.buyHotel();

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHouse);
    }

    @Test
    void testTryToBuyMultipleHotelOnProperty() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();
        improveBaltic.buyHotel();

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHotel);
    }

    @Test
    void testRunningAheadHotel() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 3; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHouse();

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHotel);
    }

    @Test
    void testRunningAheadHouse() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        improveMediterranean.buyHouse();
        improveBaltic.buyHouse();

        improveMediterranean.buyHouse();
        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHouse);
    }

    @Test
    void testBuyingPropertyWithoutHavingMoney() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");

        michiel.pay(1500);

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::buyHouse);
    }

    @Test
    void testSellingAHouse() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 2; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.sellHouse();

        assertEquals(2, michiel.findProperty("Baltic").getHouseCount());
        assertEquals(1, michiel.findProperty("Mediterranean").getHouseCount());
    }

    @Test
    void testSellingHotel() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();
        improveMediterranean.sellHotel();

        assertEquals(0, michiel.findProperty("Mediterranean").getHotelCount());
    }

    @Test
    void testRunningAheadWithSellingHouse() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 2; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.sellHouse();

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::sellHouse);
    }

    @Test
    void testSellingAHouseWithAHotelOnOtherProperty() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();

        assertEquals(0, michiel.findProperty("Mediterranean").getHouseCount());

        assertThrows(IllegalMonopolyActionException.class, improveBaltic::sellHouse);
    }

    @Test
    void testSellingAHouseWithAHotel() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::sellHouse);
    }

    @Test
    void testSellingFullyImprovedStreet() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();
        improveBaltic.buyHotel();

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        for (int i = 0; i < 4; i++) {
            improveMediterranean.sellHouse();
            improveBaltic.sellHouse();
        }

        improveMediterranean.sellHotel();
        improveBaltic.sellHotel();

        for (int i = 0; i < 4; i++) {
            improveMediterranean.sellHouse();
            improveBaltic.sellHouse();
        }

        assertEquals(0, michiel.findProperty("Mediterranean").getHotelCount());
        assertEquals(0, michiel.findProperty("Mediterranean").getHouseCount());
        assertEquals(0, michiel.findProperty("Baltic").getHotelCount());
        assertEquals(0, michiel.findProperty("Baltic").getHouseCount());
    }

    @Test
    void testSellingAHotelWhileNotHavingOne() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        assertThrows(IllegalMonopolyActionException.class, improveMediterranean::sellHotel);
    }

    @Test
    void testStreetWith3PropertiesFullyImproveAndFullySell() {
        Property SaintJames = new Property(new StreetTile("Saint James", 16, "street", 180, 90, 3, "ORANGE", 14, 70, 200, 550, 750, 950, 100, "ORANGE"));
        Property Tennessee = new Property(new StreetTile("Tennessee", 18, "street", 180, 90, 3, "ORANGE", 14, 70, 200, 550, 750, 950, 100, "ORANGE"));
        Property NewYork = new Property(new StreetTile("New York", 19, "street", 200, 100, 3, "ORANGE", 16, 80, 220, 600, 800, 1000, 100, "ORANGE"));

        michiel.collect(999999);


        michiel.addProperty(SaintJames);
        michiel.addProperty(Tennessee);
        michiel.addProperty(NewYork);

        Improve improveSaintJames = new Improve(michiel, "Saint James");
        Improve improveTennessee = new Improve(michiel, "Tennessee");
        Improve improveNewYork = new Improve(michiel, "New York");

        for (int i = 0; i < 4; i++) {
            improveNewYork.buyHouse();
            improveTennessee.buyHouse();
            improveSaintJames.buyHouse();
        }

        improveNewYork.buyHotel();
        improveTennessee.buyHotel();
        improveSaintJames.buyHotel();

        for (int i = 0; i < 4; i++) {
            improveNewYork.buyHouse();
            improveTennessee.buyHouse();
            improveSaintJames.buyHouse();
        }

        for (int i = 0; i < 4; i++) {
            improveNewYork.sellHouse();
            improveTennessee.sellHouse();
            improveSaintJames.sellHouse();
        }

        improveNewYork.sellHotel();
        improveTennessee.sellHotel();
        improveSaintJames.sellHotel();

        for (int i = 0; i < 4; i++) {
            improveNewYork.sellHouse();
            improveTennessee.sellHouse();
            improveSaintJames.sellHouse();
        }


        assertEquals(0 ,michiel.findProperty("Saint James").getHouseCount());
        assertEquals(0 ,michiel.findProperty("Tennessee").getHouseCount());
        assertEquals(0 ,michiel.findProperty("New York").getHouseCount());
        assertEquals(0 ,michiel.findProperty("Saint James").getHotelCount());
        assertEquals(0 ,michiel.findProperty("Tennessee").getHotelCount());
        assertEquals(0, michiel.findProperty("New York").getHotelCount());

    }

}