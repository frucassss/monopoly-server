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
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        List<Property> wantedproperties = List.of(mediterraneanProperty,balticProperty);
        assertEquals(wantedproperties,michiel.getProperties());
    }

    @Test
    void testMortgage() {
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Player Lucas = new Player("Lucas", new Game("hello",1,3));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        michiel.addProperty(mediterraneanProperty);
        michiel.mortgageProperty("Mediterranean");
        assertEquals(1530, michiel.getMoney());
    }

    @Test
    void testUnMortgage() {
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Player Lucas = new Player("Lucas", new Game("hello",1,3));
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
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Player Lucas = new Player("Lucas", new Game("hello",1,3));
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
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Player Lucas = new Player("Lucas", new Game("hello",1,3));
        List<Player> players = List.of(michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        assertThrows(IllegalMonopolyActionException.class, () -> michiel.mortgageProperty("Mediterranean"));
    }

    @Test
    void testFullyImproveStreet(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void testNotAllowedToBuyHotel(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void testNotAllowedToBuyHouse(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        michiel.addProperty(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse("Mediterranean");
        });
    }

    @Test
    void testTryToBuyHouseWithHotelAnd4Houses(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void testTryToBuyMultipleHotelOnProperty(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void haveToFindGoodName(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void testNotRunningAhead(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void testTryingToBuyHotelWithoutHaving4Houses(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
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
    void testBuyingPropertyWithoutHavingMoney(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel", new Game("hello",1,3));
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.pay(1500);

        assertThrows(IllegalMonopolyActionException.class,()->{
           michiel.buyHouse("Mediterranean");
        });
    }
}