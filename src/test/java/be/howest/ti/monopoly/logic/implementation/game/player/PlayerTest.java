package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testAddingPropertyToPlayer() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        List<Property> wantedproperties = List.of(mediterraneanProperty, balticProperty);
        assertEquals(wantedproperties, michiel.getProperties());
    }

    @Test
    void testMortgage() {

        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Player Lucas = new Player("Lucas", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        assertEquals(1530, michiel.getMoney());
    }

    @Test
    void testUnMortgage() {
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Player Lucas = new Player("Lucas", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        michiel.unMortgageProperty("Mediterranean");
        assertEquals(1497, michiel.getMoney());
    }

    @Test
    void testMortgageNoMoney() {
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Player Lucas = new Player("Lucas", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        michiel.pay(1500);
        assertThrows(IllegalMonopolyActionException.class, () -> michiel.unMortgageProperty("Mediterranean"));
    }

    @Test
    void testMortgageNotHavingTheProperty() {
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Player Lucas = new Player("Lucas", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        assertThrows(IllegalMonopolyActionException.class, () -> michiel.mortgageProperty("Mediterranean"));
    }

    @Test
    void testFullyImproveStreet() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");
        michiel.buyHotel("Baltic");

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        assertEquals(4,michiel.findPropertyInList("Mediterranean").getHouseCount());
        assertEquals(4,michiel.findPropertyInList("Baltic").getHouseCount());
        assertEquals(1,michiel.findPropertyInList("Baltic").getHotelCount());
        assertEquals(1, michiel.findPropertyInList("Mediterranean").getHotelCount());
    }

    @Test
    void testNotAllowedToBuyHotel() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.buyHotel("Mediterranean");
        });
    }

    @Test
    void testNotAllowedToBuyHouse() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        michiel.addProperty(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse("Mediterranean");
        });
    }

    @Test
    void testTryToBuyHouseWithHotelAnd4Houses() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");
        michiel.buyHotel("Baltic");

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse("Mediterranean");
        });
    }

    @Test
    void testTryToBuyMultipleHotelOnProperty() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");
        michiel.buyHotel("Baltic");

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHotel("Mediterranean");

        });
    }

    @Test
    void haveToFindGoodName() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");

        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.buyHotel("Mediterranean");
        });
    }

    @Test
    void testNotRunningAhead() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHouse("Mediterranean");
        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse("Mediterranean");
        });
    }

    @Test
    void testTryingToBuyHotelWithoutHaving4Houses() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        assertThrows(IllegalMonopolyActionException.class, ()->{
           michiel.buyHotel("Mediterranean");
        });
    }

    @Test
    void testBuyingPropertyWithoutHavingMoney() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.pay(1500);

        assertThrows(IllegalMonopolyActionException.class,()->{
           michiel.buyHouse("Mediterranean");
        });
    }

    @Test
    void testSellingAHouse() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.sellHouse("Mediterranean");

        assertEquals(2, michiel.findPropertyInList("Baltic").getHouseCount());
        assertEquals(1, michiel.findPropertyInList("Mediterranean").getHouseCount());
    }

    @Test
    void testSellingHotel() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");

        michiel.sellHotel("Mediterranean");

        assertEquals(0, michiel.findPropertyInList("Mediterranean").getHotelCount());
    }

    @Test
    void testRunningAheadWithSellingHouse() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.sellHouse("Mediterranean");

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHouse("Mediterranean");
        });
    }

    @Test
    void testSellingAHouseWithAHotelOnOtherProperty() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");

        assertEquals(0, michiel.findPropertyInList("Mediterranean").getHouseCount());

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHouse("Baltic");
        });
    }

    @Test
    void testSellingAHouseWithAHotel() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHouse("Mediterranean");
        });
    }

    @Test
    void testSellingFullyImprovedStreet() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.buyHotel("Mediterranean");
        michiel.buyHotel("Baltic");

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");
        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");
        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");
        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");

        michiel.sellHotel("Mediterranean");
        michiel.sellHotel("Baltic");

        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");
        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");
        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");
        michiel.sellHouse("Mediterranean");
        michiel.sellHouse("Baltic");

        assertEquals(0, michiel.findPropertyInList("Mediterranean").getHotelCount());
        assertEquals(0, michiel.findPropertyInList("Mediterranean").getHouseCount());
        assertEquals(0, michiel.findPropertyInList("Baltic").getHotelCount());
        assertEquals(0, michiel.findPropertyInList("Baltic").getHouseCount());
    }

    @Test
    void testSellingAHotelWhileNotHavingOne() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");
        michiel.buyHouse("Mediterranean");
        michiel.buyHouse("Baltic");

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHotel("Mediterranean");
        });
    }

    @Test
    void testStreetWith4PropertiesFullyImproveAndFullySell() {
        Property SaintJames = new Property(new StreetTile("Saint James", 16, "street", 180, 90, 3, "ORANGE", 14, 70, 200, 550, 750, 950, 100, "ORANGE"));
        Property Tennessee = new Property(new StreetTile("Tennessee", 18, "street", 180, 90, 3, "ORANGE", 14, 70, 200, 550, 750, 950, 100, "ORANGE"));
        Property NewYork = new Property(new StreetTile("New York", 19, "street", 200, 100, 3, "ORANGE", 16, 80, 220, 600, 800, 1000, 100, "ORANGE"));

        Player michiel = new Player("Michiel", new Game("hello",1,3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        michiel.collect(999999);


        michiel.addProperty(SaintJames);
        michiel.addProperty(Tennessee);
        michiel.addProperty(NewYork);

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHotel("Saint James");
        michiel.buyHotel("Tennessee");
        michiel.buyHotel("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.buyHouse("Saint James");
        michiel.buyHouse("Tennessee");
        michiel.buyHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHotel("Saint James");
        michiel.sellHotel("Tennessee");
        michiel.sellHotel("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        michiel.sellHouse("Saint James");
        michiel.sellHouse("Tennessee");
        michiel.sellHouse("New York");

        assertEquals(0 ,michiel.findPropertyInList("Saint James").getHouseCount());
        assertEquals(0 ,michiel.findPropertyInList("Tennessee").getHouseCount());
        assertEquals(0 ,michiel.findPropertyInList("New York").getHouseCount());
        assertEquals(0 ,michiel.findPropertyInList("Saint James").getHotelCount());
        assertEquals(0 ,michiel.findPropertyInList("Tennessee").getHotelCount());
        assertEquals(0, michiel.findPropertyInList("New York").getHotelCount());

    }
}