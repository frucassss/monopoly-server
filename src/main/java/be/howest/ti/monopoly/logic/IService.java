package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.List;
import java.util.Map;

public interface IService {
    String getVersion();

    List<Tile> getTiles();

    List<String> getChance();

    List<String> getCommunityChest();

    Game getGame(String gameId);

    Game createGame(String prefix, int numberOfPlayers);

    void joinGame(String gameId, String playerName);

    Map<String, Game> getGamesFromService();

    Tile getTile(int position);

    Tile getTile(String name);

    void buyHouse(String gameId, String playerName, String propertyName);

    void buyHotel(String gameId, String playerName, String propertyName);

    void declareBankruptcy(String gameId, String playerName);

    void sellHouse(String gameId, String playerName, String propertyName);

    void sellHotel(String gameId, String playerName, String propertyName);

    void payPrisonFine(String gameId, String playerName);

    void useGetOutOfJailFreeCard(String gameId, String playerName);
}
