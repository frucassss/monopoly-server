package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.property.ImproveProperty;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private final Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    private final Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
    private final Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
    private final Property mediterraneanProperty = new Property(mediterraneanTile);
    private final Property balticProperty = new Property(balticTile);
    ImproveProperty improveMediterranean = new ImproveProperty(michiel, "Mediterranean");
    ImproveProperty improveBaltic = new ImproveProperty(michiel, "Baltic");


    @Test
    void testAddingPropertyToPlayer() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        List<Property> wantedproperties = List.of(mediterraneanProperty, balticProperty);
        assertEquals(wantedproperties, michiel.getProperties());
    }

    @Test
    void testMortgage() {
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        assertEquals(1530, michiel.getMoney());
    }

    @Test
    void testUnMortgage() {
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        michiel.unMortgageProperty("Mediterranean");
        assertEquals(1497, michiel.getMoney());
    }

    @Test
    void testMortgageNoMoney() {
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        michiel.pay(1500);
        assertThrows(IllegalMonopolyActionException.class, () -> michiel.unMortgageProperty("Mediterranean"));
    }

    @Test
    void testMortgageNotHavingTheProperty() {
        assertThrows(IllegalMonopolyActionException.class, () -> michiel.mortgageProperty("Mediterranean"));
    }

    @Test
    void testFullyImproveStreet() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

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

        improveMediterranean.buyHouse();

        assertThrows(IllegalMonopolyActionException.class,()->{
            improveMediterranean.buyHotel();
        });
    }

    @Test
    void testNotAllowedToBuyHouse() {
        michiel.addProperty(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, ()->{
            improveMediterranean.buyHouse();
        });
    }

    @Test
    void testTryToBuyHouseWithHotelAnd4Houses() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

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

        assertThrows(IllegalMonopolyActionException.class, ()->{
            improveMediterranean.buyHouse();
        });
    }

    @Test
    void testTryToBuyMultipleHotelOnProperty() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

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

        assertThrows(IllegalMonopolyActionException.class, ()->{
            improveMediterranean.buyHotel();

        });
    }

    @Test
    void testRunningAheadHotel() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        for (int i = 0; i < 3; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHouse();

        assertThrows(IllegalMonopolyActionException.class,()->{
            improveMediterranean.buyHotel();
        });
    }

    @Test
    void testRunningAheadHouse() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        improveMediterranean.buyHouse();
        improveBaltic.buyHouse();

        improveMediterranean.buyHouse();
        assertThrows(IllegalMonopolyActionException.class, ()->{
            improveMediterranean.buyHouse();
        });
    }

    @Test
    void testBuyingPropertyWithoutHavingMoney() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.pay(1500);

        assertThrows(IllegalMonopolyActionException.class,()->{
            improveMediterranean.buyHouse();
        });
    }

    @Test
    void testSellingAHouse() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

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

        for (int i = 0; i < 2; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.sellHouse();

        assertThrows(IllegalMonopolyActionException.class, () -> {
            improveMediterranean.sellHouse();
        });
    }

    @Test
    void testSellingAHouseWithAHotelOnOtherProperty() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();

        assertEquals(0, michiel.findProperty("Mediterranean").getHouseCount());

        assertThrows(IllegalMonopolyActionException.class, () -> {
            improveBaltic.sellHouse();
        });
    }

    @Test
    void testSellingAHouseWithAHotel() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        improveMediterranean.buyHotel();

        assertThrows(IllegalMonopolyActionException.class, () -> {
            improveMediterranean.sellHouse();
        });
    }

    @Test
    void testSellingFullyImprovedStreet() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

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

        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }

        assertThrows(IllegalMonopolyActionException.class, () -> {
            improveMediterranean.sellHotel();
        });
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

        ImproveProperty improveSaintJames = new ImproveProperty(michiel, "Saint James");
        ImproveProperty improveTennessee = new ImproveProperty(michiel, "Tennessee");
        ImproveProperty improveNewYork = new ImproveProperty(michiel, "New York");

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