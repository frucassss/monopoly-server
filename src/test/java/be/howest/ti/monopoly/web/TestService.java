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
    public Game getGame(String gameId){
        return delegate.getGame(gameId);
    }

    @Override
    public Game createGame(String prefix, int numberOfPlayers){
        return delegate.createGame(prefix, numberOfPlayers);
    }

    @Override
    public void joinGame(String gameId, String playerName){
        delegate.joinGame(gameId, playerName);
    }

    @Override
    public Map<String, Game> getGames(){
        return delegate.getGames();
    }

    @Override
    public Tile getTile(int position) {
        return delegate.getTile(position);
    }

    @Override
    public Tile getTile(String name) {
        return delegate.getTile(name);
    }
}
