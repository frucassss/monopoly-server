package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
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
        Player michiel = new Player("Michiel");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        List<Property> wantedproperties = List.of(mediterraneanProperty,balticProperty);
        assertEquals(wantedproperties,michiel.getProperties());
    }

    @Test
    void testMortgage() {
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        Michiel.addProperty(mediterraneanProperty);
        Michiel.mortgageProperty(mediterraneanProperty);
        assertEquals(1530, Michiel.getMoney());
    }

    @Test
    void testUnMortgage() {
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        Michiel.addProperty(mediterraneanProperty);
        Michiel.mortgageProperty(mediterraneanProperty);
        Michiel.unMortgageProperty(mediterraneanProperty);
        assertEquals(1497, Michiel.getMoney());
    }

    @Test
    void testMortgageNoMoney() {
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        Michiel.addProperty(mediterraneanProperty);
        Michiel.mortgageProperty(mediterraneanProperty);
        Michiel.pay(1500);
        assertThrows(IllegalMonopolyActionException.class, () -> Michiel.unMortgageProperty(mediterraneanProperty));
    }

    @Test
    void testMortgageNotHavingTheProperty() {
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        Tile mediterranean = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterranean);
        assertThrows(IllegalMonopolyActionException.class, () -> Michiel.mortgageProperty(mediterraneanProperty));
    }

    @Test
    void testFullyImproveStreet(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertEquals(4,michiel.findPropertyInList(mediterraneanProperty).getHouseCount());
        assertEquals(4,michiel.findPropertyInList(balticProperty).getHouseCount());
        assertEquals(1,michiel.findPropertyInList(balticProperty).getHotelCount());
        assertEquals(1, michiel.findPropertyInList(mediterraneanProperty).getHotelCount());
    }

    @Test
    void testNotAllowedToBuyHotel(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void testNotAllowedToBuyHouse(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Player michiel = new Player("Michiel");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        michiel.addProperty(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testTryToBuyHouseWithHotelAnd4Houses(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testTryToBuyMultipleHotelOnProperty(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void haveToFindGoodName(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void testNotRunningAhead(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testTryingToBuyHotelWithoutHaving4Houses(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertThrows(IllegalMonopolyActionException.class, ()->{
           michiel.buyHotel(mediterraneanProperty);
        });
    }

    @Test
    void testBuyingPropertyWithoutHavingMoney(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.pay(1500);

        assertThrows(IllegalMonopolyActionException.class,()->{
           michiel.buyHouse(mediterraneanProperty);
        });
    }

    @Test
    void testSellingAHouse(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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
    void testSellingHotel(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertEquals(0,michiel.findPropertyInList(mediterraneanProperty).getHotelCount());
    }

    @Test
    void testRunningAheadWithSellingHouse(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        Property balticProperty = new Property(balticTile);
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);

        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);
        michiel.buyHouse(mediterraneanProperty);
        michiel.buyHouse(balticProperty);

        michiel.sellHouse(mediterraneanProperty);

        assertThrows(IllegalMonopolyActionException.class, ()->{
            michiel.sellHouse(mediterraneanProperty);
        });
    }

    @Test
    void testSellingAHouseWithAHotelOnOtherProperty(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertEquals(0,michiel.findPropertyInList(mediterraneanProperty).getHouseCount());

        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.sellHouse(balticProperty);
        });
    }

    @Test
    void testSellingAHouseWithAHotel(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertThrows(IllegalMonopolyActionException.class,()->{
            michiel.sellHouse(mediterraneanProperty);
        });
    }

    @Test
    void testSellingFullyImprovedStreet(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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
    void testSellingAHotelWhileNotHavingOne(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        Player michiel = new Player("Michiel");
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

        assertThrows(IllegalMonopolyActionException.class, ()->{michiel.sellHotel(mediterraneanProperty);});
    }
}