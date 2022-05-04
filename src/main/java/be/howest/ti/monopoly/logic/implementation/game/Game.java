package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.web.exceptions.ForbiddenAccessException;
import be.howest.ti.monopoly.web.exceptions.InvalidRequestException;

import java.util.*;

public class Game {

    private final String prefix;
    private final String id;
    private int numberOfPlayers;
    private final Map<String, Player> players = new HashMap<>();
    private boolean started = false;

    public Game(String prefix, String gameId, int numberOfPlayers) {
        this.prefix = prefix;
        this.id = gameId;
        setNumberOfPlayers(numberOfPlayers);
    }

    public void newPlayer(String playerName){
        checkIfGameIsNotStarted();
        checkPlayerName(playerName);
        checkIfPlayerIsInGame(playerName);

        Player player = new Player(playerName);
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
        checkNumberOfPlayers(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
    }

    // GETTERS

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

    // CHECKERS - Validate if applies to the rules

    public void checkNumberOfPlayers(int numberOfPlayers){
        if(numberOfPlayers < 2 || numberOfPlayers > 8){
            throw new InvalidRequestException("Invalid number of players: min. 2 & max. 8");
        }
    }

    public void checkIfGameIsNotStarted(){
        if(started) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. In this case, it is most likely that you tried to join a game which has already started, or you used a name that is already taken in this game.");
        }
    }

    public void checkPlayerName(String playerName){
        if(!playerName.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("Invalid player name!");
        }

    }

    public void checkIfPlayerIsInGame(String playerName){
        if(players.containsKey(playerName)){
            throw new ForbiddenAccessException("Player is already in game! Can only contain Alphabets.");
        }
    }
}
