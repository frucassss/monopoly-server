package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.exceptions.MonopolyResourceNotFoundException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Market;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Improve;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Rent;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Mortgage;

import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.logic.implementation.tile.UtilityTile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonopolyService extends ServiceAdapter {
    private static final List<String> tileTypes = List.of(
            "street",
            "Go",
            "community chest",
            "Tax Income",
            "railroad",
            "chance",
            "Jail",
            "utility",
            "Free Parking",
            "Go to Jail",
            "Luxury Tax"
    );
    private static final List<String> tileColors = List.of(
            "PURPLE", "BLACK", "LIGHTBLUE", "VIOLET", "WHITE", "ORANGE", "RED", "YELLOW", "DARKGREEN", "DARKBLUE"
    );


    private final Map<String, Game> games = new HashMap<>();

    public void addGame(String gameId, Game game) {
        if (games.containsKey(gameId)) {
            throw new IllegalArgumentException("You made a 'bad request'. First, verify if you supplied a body. This body can be empty, but it must be present. Then, verify if you set the \"Content-Type\"-header correctly to \"application/json\". Finnally, verify the content of the body. For the prefix, for instance, only simple names are allowed, i.e., no special characters such as spaces, pluses, or dashes, ... are allowed. For the number of players, mind to min and max number.");
        }
        games.put(gameId, game);
    }

    @Override
    public Map<String, Game> getGamesFromService() {
        return games;
    }

    @Override
    public Game createGame(String prefix, int numberOfPlayers) {
        Game game = new Game(prefix, games.size(), numberOfPlayers, getChance(), getCommunityChest(), getTiles());
        addGame(game.getId(), game);
        return game;
    }

    @Override
    public void joinGame(String gameId, String playerName) {
        Game game = getGame(gameId);
        game.newPlayer(playerName);
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public List<Tile> getTiles() {
        return List.of(
                new Tile(tileTypes.get(1), 0, tileTypes.get(1)),
                new StreetTile("Mediterranean", 1, tileTypes.get(0), 60, 30, 2, tileColors.get(0), 2, 10, 30, 90, 160, 250, 50, tileColors.get(0)),
                new Tile("Community Chest I", 2, tileTypes.get(2)),
                new StreetTile("Baltic", 3, tileTypes.get(0), 60, 30, 2, tileColors.get(0), 4, 20, 60, 180, 320, 450, 50, tileColors.get(0)),
                new Tile(tileTypes.get(3), 4, tileTypes.get(3)),
                new RailroadTile("Reading RR", 5, tileTypes.get(4), 200, 100, 4, tileColors.get(1), -1),
                new StreetTile("Oriental", 6, tileTypes.get(0), 100, 50, 3, tileColors.get(2), 6, 30, 90, 270, 400, 550, 50, tileColors.get(2)),
                new Tile("Chance I", 7, tileTypes.get(5)),
                new StreetTile("Vermont", 8, tileTypes.get(0), 100, 50, 3, tileColors.get(2), 6, 30, 90, 270, 400, 550, 50, tileColors.get(2)),
                new StreetTile("Connecticut", 9, tileTypes.get(0), 120, 60, 3, tileColors.get(2), 8, 40, 100, 300, 450, 600, 50, tileColors.get(2)),
                new Tile(tileTypes.get(6), 10, tileTypes.get(6)),
                new StreetTile("Saint Charles Place", 11, tileTypes.get(0), 140, 70, 3, tileColors.get(3), 10, 50, 150, 450, 625, 750, 100, tileColors.get(3)),
                new UtilityTile("Electric Company", 12, tileTypes.get(7), 150, 75, 2, tileColors.get(4), -1),
                new StreetTile("States", 13, tileTypes.get(0), 140, 70, 3, tileColors.get(3), 10, 50, 150, 450, 625, 750, 100, tileColors.get(3)),
                new StreetTile("Virginia", 14, tileTypes.get(0), 160, 80, 3, tileColors.get(3), 12, 60, 180, 500, 700, 900, 100, tileColors.get(3)),
                new RailroadTile("Pennsylvania RR", 15, tileTypes.get(4), 200, 100, 4, tileColors.get(1), -1),
                new StreetTile("Saint James", 16, tileTypes.get(0), 180, 90, 3, tileColors.get(5), 14, 70, 200, 550, 750, 950, 100, tileColors.get(5)),
                new Tile("Community Chest II", 17, tileTypes.get(2)),
                new StreetTile("Tennessee", 18, tileTypes.get(0), 180, 90, 3, tileColors.get(5), 14, 70, 200, 550, 750, 950, 100, tileColors.get(5)),
                new StreetTile("New York", 19, tileTypes.get(0), 200, 100, 3, tileColors.get(5), 16, 80, 220, 600, 800, 1000, 100, tileColors.get(5)),
                new Tile(tileTypes.get(8), 20, tileTypes.get(8)),
                new StreetTile("Kentucky Avenue", 21, tileTypes.get(0), 220, 110, 3, tileColors.get(6), 18, 90, 250, 700, 875, 1050, 150, tileColors.get(6)),
                new Tile("Chance II", 22, tileTypes.get(5)),
                new StreetTile("Indiana Avenue", 23, tileTypes.get(0), 220, 110, 3, tileColors.get(6), 18, 90, 250, 700, 875, 1050, 150, tileColors.get(6)),
                new StreetTile("Illinois Avenue", 24, tileTypes.get(0), 240, 120, 3, tileColors.get(6), 20, 100, 300, 750, 925, 1100, 150, tileColors.get(6)),
                new RailroadTile("Baltimore and Ohio RR", 25, tileTypes.get(4), 200, 100, 4, tileColors.get(1), -1),
                new StreetTile("Atlantic", 26, tileTypes.get(0), 260, 130, 3, tileColors.get(7), 22, 110, 330, 800, 975, 1150, 150, tileColors.get(7)),
                new StreetTile("Ventnor", 27, tileTypes.get(0), 260, 130, 3, tileColors.get(7), 22, 110, 330, 800, 975, 1150, 150, tileColors.get(7)),
                new UtilityTile("Water Works", 28, tileTypes.get(7), 150, 75, 2, tileColors.get(4), -1),
                new StreetTile("Marvin Gardens", 29, tileTypes.get(0), 280, 140, 3, tileColors.get(7), 24, 120, 360, 850, 1025, 1200, 150, tileColors.get(7)),
                new Tile(tileTypes.get(8), 30, tileTypes.get(8)),
                new StreetTile("Pacific", 31, tileTypes.get(0), 300, 150, 3, tileColors.get(8), 26, 130, 390, 900, 1100, 1275, 200, tileColors.get(8)),
                new StreetTile("North Carolina", 32, tileTypes.get(0), 300, 150, 3, tileColors.get(8), 26, 130, 390, 900, 1100, 1275, 200, tileColors.get(8)),
                new Tile("Community Chest III", 33, tileTypes.get(2)),
                new StreetTile("Pennsylvania", 34, tileTypes.get(0), 320, 160, 3, tileColors.get(8), 28, 150, 450, 1000, 1200, 1400, 200, tileColors.get(8)),
                new RailroadTile("Short Line RR", 35, tileTypes.get(4), 200, 100, 4, tileColors.get(1), -1),
                new Tile("Chance III", 36, tileTypes.get(5)),
                new StreetTile("Park Place", 37, tileTypes.get(0), 350, 175, 2, tileColors.get(9), 35, 175, 500, 1100, 1300, 1500, 200, tileColors.get(9)),
                new Tile(tileTypes.get(9), 38, tileTypes.get(9)),
                new StreetTile("Boardwalk", 39, tileTypes.get(0), 400, 200, 2, tileColors.get(9), 50, 200, 600, 1400, 1700, 2000, 200, tileColors.get(9))
        );
    }

    @Override
    public List<String> getChance() {
        return List.of(
                "Advance to Boardwalk",
                "Advance to Go (Collect $200)",
                "Advance to Illinois Avenue. If you pass Go, collect $200",
                "Advance to St. Charles Place. If you pass Go, collect $200",
                "Advance to the nearest Railroad.",
                "Advance token to nearest Utility.",
                "Bank pays you dividend of $50",
                "Get Out of Jail Free",
                "Go Back 3 Spaces",
                "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200",
                "Make general repairs on all your property. For each house pay $25. For each hotel pay $100",
                "Speeding fine $15",
                "Take a trip to Reading Railroad. If you pass Go, collect $200",
                "You have been elected Chairman of the Board. Pay each player $50",
                "Your building loan matures. Collect $150"
        );
    }

    @Override
    public List<String> getCommunityChest() {
        return List.of(
                "Advance to Go (Collect $200)",
                "Bank error in your favor. Collect $200",
                "Doctor's fee. Pay $50",
                "From sale of stock you get $50",
                "Get Out of Jail Free",
                "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
                "Holiday fund matures. Receive $100",
                "Income tax refund. Collect $20",
                "It is your birthday. Collect $10 from every player",
                "Life insurance matures. Collect $100",
                "Pay hospital fees of $100",
                "Pay school fees of $50",
                "Receive $25 consultancy fee",
                "You are assessed for street repair. $40 per house. $115 per hotel",
                "You have won second prize in a beauty contest. Collect $10",
                "You inherit $100"
        );
    }

    @Override
    public Game getGame(String gameId) {
        if (!games.containsKey(gameId)) {
            throw new MonopolyResourceNotFoundException("The game you are looking for does not exist. Double check the ID.");
        }
        return games.get(gameId);
    }

    @Override
    public Tile getTile(int position) {
        for (Tile tile : getTiles()) {
            if (tile.getPosition() == position) {
                return tile;
            }
        }
        throw new MonopolyResourceNotFoundException("no such tile");
    }

    @Override
    public Tile getTile(String name) {
        for (Tile tile : getTiles()) {
            if (tile.getName().equals(name)) {
                return tile;
            }
        }
        throw new MonopolyResourceNotFoundException("no such tile");
    }

    @Override
    public void buyHouse(String gameId, String playerName, String propertyName) {
        Player player = getGame(gameId).findPlayer(playerName);
        Improve improve = new Improve(player, propertyName);
        improve.buyHouse();
    }

    @Override
    public void buyHotel(String gameId, String playerName, String propertyName) {
        Player player = getGame(gameId).findPlayer(playerName);
        Improve improve = new Improve(player, propertyName);
        improve.buyHotel();
    }


    @Override
    public void sellHouse(String gameId, String playerName, String propertyName) {
        Player player = getGame(gameId).findPlayer(playerName);
        Improve improve = new Improve(player, propertyName);
        improve.sellHouse();
    }

    @Override
    public void sellHotel(String gameId, String playerName, String propertyName) {
        Player player = getGame(gameId).findPlayer(playerName);
        Improve improve = new Improve(player, propertyName);
        improve.sellHotel();
    }

    @Override
    public void declareBankruptcy(String gameId, String playerName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        player.makeBankrupt(game);
    }

    @Override
    public void buyProperty(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        Market market = new Market(player, game, propertyName);
        market.buyProperty();
    }

    @Override
    public void dontBuyProperty(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        Market market = new Market(player, game, propertyName);
        market.dontBuyProperty();
    }

    @Override
    public void payPrisonFine(String gameId, String playerName) {
        Player player = getGame(gameId).findPlayer(playerName);
        player.payPrisonFine();
    }

    @Override
    public void useGetOutOfJailFreeCard(String gameId, String playerName) {
        Player player = getGame(gameId).findPlayer(playerName);
        player.useGetOutOfJailFreeCard();
    }

    @Override
    public void collectDebt(String gameId, String playerName, String propertyName, String debtorName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        Player debtor = game.findPlayer(debtorName);
        Rent rent = new Rent(game, player, debtor, propertyName);
        rent.collectRent();
    }

    @Override
    public void rollDice(String gameId, String playerName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        new Turn(game, player);
    }

    @Override
    public void takeMortgage(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        Mortgage mortgage = new Mortgage(player, propertyName);
        mortgage.takeMortgage();
    }

    @Override
    public void settleMortgage(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        Mortgage mortgage = new Mortgage(player, propertyName);
        mortgage.settleMortgage();
    }
}
