package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.IService;
import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Map;

public class TestService implements IService {

    IService delegate = new ServiceAdapter();

    void setDelegate(IService delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getVersion() {
        return delegate.getVersion();
    }

    @Override
    public List<Tile> getTiles() {
        return delegate.getTiles();
    }

    @Override
    public List<String> getChance() {
        return delegate.getChance();
    }

    @Override
    public List<String> getCommunityChest() {
        return delegate.getCommunityChest();
    }

    @Override
    public Game getGame(String gameId) {
        return delegate.getGame(gameId);
    }

    @Override
    public Game createGame(String prefix, int numberOfPlayers) {
        return delegate.createGame(prefix, numberOfPlayers);
    }

    @Override
    public void joinGame(String gameId, String playerName) {
        delegate.joinGame(gameId, playerName);
    }

    @Override
    public Map<String, Game> getGamesFromService() {
        return delegate.getGamesFromService();
    }

    @Override
    public Tile getTile(int position) {
        return delegate.getTile(position);
    }

    @Override
    public Tile getTile(String name) {
        return delegate.getTile(name);
    }

    @Override
    public void buyHouse(String gameId, String playerName, String propertyName) {
        delegate.buyHouse(gameId,playerName,propertyName);
    }

    @Override
    public void buyHotel(String gameId, String playerName, String propertyName) {
        delegate.buyHotel(gameId, playerName, propertyName);
    }

    @Override
    public void declareBankruptcy(String gameId, String playerName) {
        delegate.declareBankruptcy(gameId, playerName);
    }

    @Override
    public void sellHouse(String gameId, String playerName, String propertyName) {
        delegate.sellHouse(gameId,playerName,propertyName);
    }

    @Override
    public void sellHotel(String gameId, String playerName, String propertyName) {
        delegate.sellHotel(gameId,playerName,propertyName);
    }

    @Override
    public void payPrisonFine(String gameId, String playerName) {
        delegate.payPrisonFine(gameId,playerName);
    }

    @Override
    public void useGetOutOfJailFreeCard(String gameId, String playerName) {
        delegate.useGetOutOfJailFreeCard(gameId, playerName);
    }

    @Override
    public void buyProperty(String gameId, String playerName, String propertyName) {
        delegate.buyProperty(gameId,playerName,propertyName);
    }

    @Override
    public void collectDept(String gameId, String playerName, String propertyName, String debtorName) {
        delegate.collectDept(gameId, playerName, propertyName, debtorName);
    }
}
