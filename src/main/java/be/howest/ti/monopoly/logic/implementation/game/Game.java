package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.game.GameCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.web.exceptions.ForbiddenAccessException;

import java.util.*;

public class Game {

    private final String prefix;
    private final String id;
    private int numberOfPlayers;
    private final Map<String, Player> players = new HashMap<>();
    private boolean started = false;
    private String currentPlayer;
    private final List<Tile> tiles;
    private final List<String> chance;
    private final List<String> communityChest;
    private final GameCheck gameCheck;

    public Game(String prefix, int sessionNumber, int numberOfPlayers, List<String> chance, List<String> communityChest, List<Tile> tiles) {
        gameCheck = new GameCheck(this);
        gameCheck.checkCharactersInString(prefix, "Prefix");
        this.tiles = tiles;
        this.communityChest = communityChest;
        this.chance = chance;
        this.prefix = prefix;
        this.id = prefix + "_" + sessionNumber;
        setNumberOfPlayers(numberOfPlayers);
    }

    public void newPlayer(String playerName){
        gameCheck.checkIfGameIsNotStarted();
        gameCheck.checkCharactersInString(playerName, "Player name");
        gameCheck.checkIfPlayerIsInGame(playerName);
        Player player = new Player(playerName, this);
        players.put(playerName, player);

        if(players.size() == numberOfPlayers){
            started = true;
        }
    }

    public Player findPlayer(String playerName){
        return players.get(playerName);
    }

    // SETTERS

    public void setNumberOfPlayers(int numberOfPlayers){
        gameCheck.checkNumberOfPlayers(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    // GETTERS

    public List<Tile> receiveTiles() {
        return tiles;
    }

    public List<String> receiveChance() {
        return chance;
    }

    public List<String> receiveCommunityChest() {
        return communityChest;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getId() {
        return id;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public boolean getStarted() {
        return started;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

}
