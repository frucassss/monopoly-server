package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
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

    Map<String, Game> getGames();

    Tile getTile(int position);

    Tile getTile(String name);
}
