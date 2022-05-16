package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public class ServiceAdapter implements IService {

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tile> getTiles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getChance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getCommunityChest() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game getGame(String gameId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game createGame(String prefix, int numberOfPlayers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void joinGame(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Game> getGamesFromService() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tile getTile(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void buyHouse(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void declareBankruptcy(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sellHouse(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sellHotel(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void payPrisonFine(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void useGetOutOfJailFreeCard(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void buyProperty(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void collectDebt(String gameId, String playerName, String propertyName, String debtorName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tile getTile(int position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void buyHotel(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void rollDice(String gameId, String playerName){
        throw new UnsupportedOperationException();
    }
}
