package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.Property;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testAddingPropertyToPlayer() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        List<Property> wantedproperties = List.of(mediterraneanProperty, balticProperty);
        assertEquals(wantedproperties, michiel.getProperties());
    }

    @Test
    void testMortgage() {
        Player Michiel = new Player("Michiel",new Game("test",1,3));
        Player Lucas = new Player("Lucas",new Game("test",1,3));
        List<Player> players = List.of(Michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        Michiel.addProperty(mediterraneanProperty);
        Michiel.mortgageProperty(mediterraneanProperty);
        assertEquals(1530, Michiel.getMoney());
    }

    @Test
    void testUnMortgage() {
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Player Lucas = new Player("Lucas",new Game("test",1,3));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty(mediterraneanProperty);
        michiel.unMortgageProperty(mediterraneanProperty);
        assertEquals(1497, michiel.getMoney());
    }

    @Test
    void testMortgageNoMoney() {
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Player Lucas = new Player("Lucas",new Game("test",1,3));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty(mediterraneanProperty);
        michiel.pay(1500);
        assertThrows(IllegalMonopolyActionException.class, () -> michiel.unMortgageProperty(mediterraneanProperty));
    }

    @Test
    void testMortgageNotHavingTheProperty() {
        Player Michiel = new Player("Michiel",new Game("test",1,3));
        Player Lucas = new Player("Lucas",new Game("test",1,3));
        List<Player> players = List.of(Michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        assertThrows(IllegalMonopolyActionException.class, () -> Michiel.mortgageProperty(mediterraneanProperty));
    }

    @Test
    void testFullyImproveStreet() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);
        michiel.buyHotel(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertEquals(4, michiel.findPropertyInList(mediterraneanProperty).getHouseCount());
        assertEquals(4, michiel.findPropertyInList(balticProperty).getHouseCount());
        assertEquals(1, michiel.findPropertyInList(balticProperty).getHotelCount());
        assertEquals(1, michiel.findPropertyInList(mediterraneanProperty).getHotelCount());
    }

    @Test
    void testNotAllowedToBuyHotel() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void testNotAllowedToBuyHouse() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        michiel.addProperty(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testTryToBuyHouseWithHotelAnd4Houses() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);
        michiel.buyHotel(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testTryToBuyMultipleHotelOnProperty() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);
        michiel.buyHotel(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void haveToFindGoodName() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void testNotRunningAhead() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testTryingToBuyHotelWithoutHaving4Houses() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void testBuyingPropertyWithoutHavingMoney() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.pay(1500);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testSellingAHouse() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.sellHouse(mediterraneanProperty);

        assertEquals(1, michiel.findPropertyInList(mediterraneanProperty).getHouseCount());
        assertEquals(2, michiel.findPropertyInList(balticProperty).getHouseCount());
    }

    @Test
    void testSellingHotel() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);

        michiel.sellHotel(mediterraneanProperty);

        assertEquals(0, michiel.findPropertyInList(mediterraneanProperty).getHotelCount());
    }

    @Test
    void testRunningAheadWithSellingHouse() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.sellHouse(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHouse(mediterraneanProperty);
        });
    }

    @Test
    void testSellingAHouseWithAHotelOnOtherProperty() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);

        assertEquals(0, michiel.findPropertyInList(mediterraneanProperty).getHouseCount());

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHouse(balticProperty);
        });
    }

    @Test
    void testSellingAHouseWithAHotel() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHouse(mediterraneanProperty);
        });
    }

    @Test
    void testSellingFullyImprovedStreet() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHotel(mediterraneanProperty);
        michiel.buyHotel(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);
        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);
        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);
        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);

        michiel.sellHotel(mediterraneanProperty);
        michiel.sellHotel(balticProperty);

        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);
        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);
        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);
        michiel.sellHouse(mediterraneanProperty);
        michiel.sellHouse(balticProperty);

        assertEquals(0, michiel.findPropertyInList(mediterraneanProperty).getHotelCount());
        assertEquals(0, michiel.findPropertyInList(mediterraneanProperty).getHouseCount());
        assertEquals(0, michiel.findPropertyInList(balticProperty).getHotelCount());
        assertEquals(0, michiel.findPropertyInList(balticProperty).getHouseCount());
    }

    @Test
    void testSellingAHotelWhileNotHavingOne() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel",new Game("test",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertThrows(IllegalMonopolyActionException.class, () -> {
            michiel.sellHotel(mediterraneanProperty);
        });
    }

    @Test
    void testStreetWith4PropertiesFullyImproveAndFullySell() {
        Property SaintJames = new Property(new StreetTile("Saint James", 16, "street", 180, 90, 3, "ORANGE", 14, 70, 200, 550, 750, 950, 100, "ORANGE"));
        Property Tennessee = new Property(new StreetTile("Tennessee", 18, "street", 180, 90, 3, "ORANGE", 14, 70, 200, 550, 750, 950, 100, "ORANGE"));
        Property NewYork = new Property(new StreetTile("New York", 19, "street", 200, 100, 3, "ORANGE", 16, 80, 220, 600, 800, 1000, 100, "ORANGE"));

        Player michiel = new Player("Michiel",new Game("test",1,3));
        michiel.collect(999999);


        michiel.addProperty(SaintJames);
        michiel.addProperty(Tennessee);
        michiel.addProperty(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHotel(SaintJames);
        michiel.buyHotel(Tennessee);
        michiel.buyHotel(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.buyHouse(SaintJames);
        michiel.buyHouse(Tennessee);
        michiel.buyHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHotel(SaintJames);
        michiel.sellHotel(Tennessee);
        michiel.sellHotel(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        michiel.sellHouse(SaintJames);
        michiel.sellHouse(Tennessee);
        michiel.sellHouse(NewYork);

        assertEquals(0 ,michiel.findPropertyInList(SaintJames).getHouseCount());
        assertEquals(0 ,michiel.findPropertyInList(Tennessee).getHouseCount());
        assertEquals(0 ,michiel.findPropertyInList(NewYork).getHouseCount());
        assertEquals(0 ,michiel.findPropertyInList(SaintJames).getHotelCount());
        assertEquals(0 ,michiel.findPropertyInList(Tennessee).getHotelCount());
        assertEquals(0, michiel.findPropertyInList(NewYork).getHotelCount());

    }
}