package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.exceptions.MonopolyResourceNotFoundException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Market;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Improve;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Mortgage;
import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.logic.implementation.tile.UtilityTile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonopolyService extends ServiceAdapter {

    private final Map<String, Game> games = new HashMap<>();

    public void addGame(String gameId, Game game){
        if(games.containsKey(gameId)){
            throw new IllegalArgumentException("You made a 'bad request'. First, verify if you supplied a body. This body can be empty, but it must be present. Then, verify if you set the \"Content-Type\"-header correctly to \"application/json\". Finnally, verify the content of the body. For the prefix, for instance, only simple names are allowed, i.e., no special characters such as spaces, pluses, or dashes, ... are allowed. For the number of players, mind to min and max number.");
        }
        games.put(gameId, game);
    }

    @Override
    public Map<String, Game> getGamesFromService(){
        return games;
    }

    @Override
    public Game createGame(String prefix, int numberOfPlayers){
        Game game = new Game(prefix, games.size(), numberOfPlayers, getChance(),getCommunityChest(),getTiles());
        addGame(game.getId(), game);
        return game;
    }

    @Override
    public void joinGame(String gameId, String playerName){
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
                new Tile("Go",0,"Go"),
                new StreetTile("Mediterranean",1,"street",60,30,2,"PURPLE",2,10,30,90,160,250,50,"PURPLE"),
                new Tile("Community Chest I",2,"community chest"),
                new StreetTile("Baltic",3,"street",60,30,2,"PURPLE",4,20,60,180,320,450,50,"PURPLE"),
                new Tile("Tax Income",4,"Tax Income"),
                new RailroadTile("Reading RR",5,"railroad",200,100,4,"BLACK",-1),
                new StreetTile("Oriental",6,"street",100,50,3,"LIGHTBLUE",6,30,90,270,400,550,50,"LIGHTBLUE"),
                new Tile("Chance I",7,"chance"),
                new StreetTile("Vermont",8,"street",100,50,3,"LIGHTBLUE",6,30,90,270,400,550,50,"LIGHTBLUE"),
                new StreetTile("Connecticut",9,"street",120,60,3,"LIGHTBLUE",8,40,100,300,450,600,50,"LIGHTBLUE"),
                new Tile("Jail",10,"Jail"),
                new StreetTile("Saint Charles Place",11,"street",140,70,3,"VIOLET",10,50,150,450,625,750,100,"VIOLET"),
                new UtilityTile("Electric Company",12,"utility",150,75,2,"WHITE",-1),
                new StreetTile("States",13,"street",140,70,3,"VIOLET",10,50,150,450,625,750,100,"VIOLET"),
                new StreetTile("Virginia",14,"street",160,80,3,"VIOLET",12,60,180,500,700,900,100,"VIOLET"),
                new RailroadTile("Pennsylvania RR",15,"railroad",200,100,4,"BLACK",-1),
                new StreetTile("Saint James",16,"street",180,90,3,"ORANGE",14,70,200,550,750,950,100,"ORANGE"),
                new Tile("Community Chest II",17,"community chest"),
                new StreetTile("Tennessee",18,"street",180,90,3,"ORANGE",14,70,200,550,750,950,100,"ORANGE"),
                new StreetTile("New York",19,"street",200,100,3,"ORANGE",16,80,220,600,800,1000,100,"ORANGE"),
                new Tile("Free Parking",20,"Free Parking"),
                new StreetTile("Kentucky Avenue",21,"street",220,110,3,"RED",18,90,250,700,875,1050,150,"RED"),
                new Tile("Chance II",22,"chance"),
                new StreetTile("Indiana Avenue",23,"street",220,110,3,"RED",18,90,250,700,875,1050,150,"RED"),
                new StreetTile("Illinois Avenue",24,"street",240,120,3,"RED",20,100,300,750,925,1100,150,"RED"),
                new RailroadTile("Baltimore and Ohio RR",25,"railroad",200,100,4,"BLACK",-1),
                new StreetTile("Atlantic",26,"street",260,130,3,"YELLOW",22,110,330,800,975,1150,150,"YELLOW"),
                new StreetTile("Ventnor",27,"street",260,130,3,"YELLOW",22,110,330,800,975,1150,150,"YELLOW"),
                new UtilityTile("Water Works",28,"utility",150,75,2,"WHITE",-1),
                new StreetTile("Marvin Gardens",29,"street",280,140,3,"YELLOW",24,120,360,850,1025,1200,150,"YELLOW"),
                new Tile("Go to Jail",30,"Go to Jail"),
                new StreetTile("Pacific",31,"street",300,150,3,"DARKGREEN",26,130,390,900,1100,1275,200,"DARKGREEN"),
                new StreetTile("North Carolina",32,"street",300,150,3,"DARKGREEN",26,130,390,900,1100,1275,200,"DARKGREEN"),
                new Tile("Community Chest III",33,"community chest"),
                new StreetTile("Pennsylvania",34,"street",320,160,3,"DARKGREEN",28,150,450,1000,1200,1400,200,"DARKGREEN"),
                new RailroadTile("Short Line RR",35,"railroad",200,100,4,"BLACK",-1),
                new Tile("Chance III",36,"chance"),
                new StreetTile("Park Place",37,"street",350,175,2,"DARKBLUE",35,175,500,1100,1300,1500,200,"DARKBLUE"),
                new Tile("Luxury Tax",38,"Luxury Tax"),
                new StreetTile("Boardwalk",39,"street",400,200,2,"DARKBLUE",50,200,600,1400,1700,2000,200,"DARKBLUE")
        );
    }

    @Override
    public List<String> getChance() {
        return List.of(
                "Advance to Boardwalk",
                "Advance to Go (Collect $200)",
                "Advance to Illinois Avenue. If you pass Go, collect $200",
                "Advance to St. Charles Place. If you pass Go, collect $200",
                "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled",
                "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.",
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
                "Pay hospital fees of $100 ",
                "Pay school fees of $50",
                "Receive $25 consultancy fee ",
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
        for (Tile tile : getTiles()){
            if (tile.getPosition() == position){
                return tile;
            }
        }
        throw new MonopolyResourceNotFoundException("no such tile");
    }

    @Override
    public Tile getTile(String name) {
        for (Tile tile: getTiles()){
            if (tile.getName().equals(name)){
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
    public void sellHouse(String gameId, String playerName, String propertyName){
        Player player = getGame(gameId).findPlayer(playerName);
        Improve improve = new Improve(player, propertyName);
        improve.sellHouse();
    }

    @Override
    public void sellHotel(String gameId, String playerName, String propertyName){
        Player player = getGame(gameId).findPlayer(playerName);
        Improve improve = new Improve(player, propertyName);
        improve.sellHotel();
    }

    @Override
    public void declareBankruptcy(String gameId, String playerName){
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        player.makeBankrupt();
    }

    @Override
    public void buyProperty(String gameId, String playerName, String propertyName){
        Market market = new Market(getGame(gameId).findPlayer(playerName),getGame(gameId),propertyName);
        market.buyProperty();
    }

    @Override
    public void payPrisonFine(String gameId, String playerName){
        Player player  = getGame(gameId).findPlayer(playerName);
        player.payPrisonFine();
    }

    @Override
    public void useGetOutOfJailFreeCard(String gameId, String playerName){
        Player player  = getGame(gameId).findPlayer(playerName);
        player.useGetOutOfJailFreeCard();
    }

    @Override
    public void collectDebt(String gameId, String playerName, String propertyName, String debtorName) {

    }

    @Override
    public void rollDice(String gameId, String playerName) {
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        new Turn(game, player);
    }
    @Override
    public void takeMortgage(String gameId, String playerName, String propertyName){
        Game game = getGame(gameId);
        Player player = game.findPlayer(playerName);
        Mortgage mortgage = new Mortgage(player,propertyName);
        mortgage.takeMortgage();
    }
}
