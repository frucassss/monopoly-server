package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
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
    private static Property mediterraneanProperty;
    private static Property balticProperty;
    private static Property railroadPropertyOne;
    private static Property railroadPropertyTwo;
    private static Property railroadPropertyThree;

    @BeforeEach
    public void initTest() {
        monopolyService = new MonopolyService();
        game = new Game("hello", 5, 2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
        game.newPlayer("lucas");
        game.newPlayer("michiel");
        michiel = game.findPlayer("michiel");
        lucas = game.findPlayer("lucas");
        mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        balticTile = new StreetTile("Baltic", 3, "street", 60, 30, 2, "PURPLE", 4, 20, 60, 180, 320, 450, 50, "PURPLE");
        railroadTileOne = new RailroadTile("Reading RR", 5, "railroad", 200, 100, 4, "BLACK", -1);
        railroadTileTwo = new RailroadTile("Pennsylvania RR", 15, "railroad", 200, 100, 4, "BLACK", -1);
        railroadTileThree = new RailroadTile("Baltimore and Ohio RR", 25, "railroad", 200, 100, 4, "BLACK", -1);
        mediterraneanProperty = new Property(mediterraneanTile);
        balticProperty = new Property(balticTile);
        railroadPropertyOne = new Property(railroadTileOne);
        railroadPropertyTwo = new Property(railroadTileTwo);
        railroadPropertyThree = new Property(railroadTileThree);
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


}