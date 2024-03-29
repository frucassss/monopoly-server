package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.logic.implementation.tile.UtilityTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;

import static org.junit.jupiter.api.Assertions.*;


class RentTest {

    private static MonopolyService monopolyService;
    private static Game game;
    private static Player michiel;
    private static Player lucas;
    private static Tile mediterraneanTile;
    private static Tile balticTile;
    private static Tile railroadTileOne;
    private static Tile railroadTileTwo;
    private static Tile railroadTileThree;
    private static Tile utilityTileOne;
    private static Tile utilityTileTwo;
    private static Tile goTile;
    private static Property mediterraneanProperty;
    private static Property balticProperty;
    private static Property railroadPropertyOne;
    private static Property railroadPropertyTwo;
    private static Property railroadPropertyThree;
    private static Property utilityPropertyOne;
    private static Property utilityPropertyTwo;

    @BeforeEach
    public void initTest() {
        monopolyService = new MonopolyService();
        game = new Game("hello", 5, 2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("lucas");
        game.newPlayer("michiel");
        michiel = game.findPlayer("michiel");
        lucas = game.findPlayer("lucas");
        mediterraneanTile = monopolyService.getTile("Mediterranean");
        balticTile = monopolyService.getTile("Baltic");
        railroadTileOne = monopolyService.getTile("Reading RR");
        railroadTileTwo = monopolyService.getTile("Pennsylvania RR");
        railroadTileThree = monopolyService.getTile("Baltimore and Ohio RR");
        utilityTileOne = monopolyService.getTile("Electric Company");
        utilityTileTwo = monopolyService.getTile("Water Works");
        goTile = monopolyService.getTile("Go");
        mediterraneanProperty = new Property(mediterraneanTile);
        balticProperty = new Property(balticTile);
        railroadPropertyOne = new Property(railroadTileOne);
        railroadPropertyTwo = new Property(railroadTileTwo);
        railroadPropertyThree = new Property(railroadTileThree);
        utilityPropertyOne = new Property(utilityTileOne);
        utilityPropertyTwo = new Property(utilityTileTwo);
    }

    @Test
    void rentWithOnePropertyTest() {
        michiel.addProperty(mediterraneanProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 2, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 2, lucas.getMoney());
    }

    @Test
    void rentWithStreetWithoutHousesOrHotelTest1() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 4, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 4, lucas.getMoney());
    }

    @Test
    void rentWithStreetWithoutHousesOrHotelTest2() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(balticTile);
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Baltic");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 8, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 8, lucas.getMoney());
    }

    @Test
    void rentWithOneHouseTest() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        improveMediterranean.buyHouse();
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 10, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 10, lucas.getMoney());
    }

    @Test
    void rentWithTwoHouseTest() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");
        for (int i = 0; i < 2; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 30, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 30, lucas.getMoney());
    }

    @Test
    void rentWithThreeHouseTest() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");
        for (int i = 0; i < 3; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 90, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 90, lucas.getMoney());
    }


    @Test
    void rentWithFourHouseTest() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");
        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }
        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 160, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 160, lucas.getMoney());
    }

    @Test
    void rentWithHotelTest() {
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);
        Improve improveMediterranean = new Improve(michiel, "Mediterranean");
        Improve improveBaltic = new Improve(michiel, "Baltic");
        for (int i = 0; i < 4; i++) {
            improveMediterranean.buyHouse();
            improveBaltic.buyHouse();
        }
        improveMediterranean.buyHotel();

        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Mediterranean");
        rent.collectRent();
        assertEquals(moneyMichielBeforeTurn + 250, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 250, lucas.getMoney());
    }
    @Test
    void rentOneRailroadTest() {
        michiel.addProperty(railroadPropertyOne);
        new Turn(game, lucas);
        lucas.setCurrentTile(railroadTileOne);

        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Reading RR");
        rent.collectRent();

        assertEquals(moneyMichielBeforeTurn + 25, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 25, lucas.getMoney());
    }

    @Test
    void rentThreeRailroadTest() {
        michiel.addProperty(railroadPropertyOne);
        michiel.addProperty(railroadPropertyTwo);
        michiel.addProperty(railroadPropertyThree);

        new Turn(game, lucas);
        lucas.setCurrentTile(railroadTileOne);

        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Reading RR");
        rent.collectRent();

        assertEquals(moneyMichielBeforeTurn + 100, michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - 100, lucas.getMoney());
    }

    @Test
    void rentOneUtilityTest(){
        michiel.addProperty(utilityPropertyOne);
        new Turn(game, lucas);
        lucas.setCurrentTile(utilityTileOne);

        int totalDice = game.getLastDiceRoll()[0] + game.getLastDiceRoll()[1];

        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Electric Company");
        rent.collectRent();

        assertEquals(moneyMichielBeforeTurn + (totalDice * 4), michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - (totalDice * 4), lucas.getMoney());
    }

    @Test
    void rentTwoUtilityTest(){
        michiel.addProperty(utilityPropertyOne);
        michiel.addProperty(utilityPropertyTwo);
        new Turn(game, lucas);
        lucas.setCurrentTile(utilityTileOne);

        int totalDice = game.getLastDiceRoll()[0] + game.getLastDiceRoll()[1];

        int moneyMichielBeforeTurn = michiel.getMoney();
        int moneyLucasBeforeTurn = lucas.getMoney();

        Rent rent = new Rent(game, michiel, lucas, "Electric Company");
        rent.collectRent();

        assertEquals(moneyMichielBeforeTurn + (totalDice * 10), michiel.getMoney());
        assertEquals(moneyLucasBeforeTurn - (totalDice * 10), lucas.getMoney());
    }

    @Test
    void notAllowedToCollectRentTest(){
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        new Turn(game, lucas);
        lucas.setCurrentTile(mediterraneanTile);

        Rent rent = new Rent(game, michiel, lucas, "Baltic");

        assertThrows(IllegalMonopolyActionException.class, rent::collectRent);
    }

    @Test
    void notAllowedToCollectRentTest2(){
        michiel.addProperty(mediterraneanProperty);
        michiel.addProperty(balticProperty);
        game.setCurrentPlayer(michiel);
        new Turn(game, michiel);
        Rent rent = new Rent(game, michiel, lucas, "Baltic");

        assertThrows(IllegalMonopolyActionException.class, rent::collectRent);
    }

}