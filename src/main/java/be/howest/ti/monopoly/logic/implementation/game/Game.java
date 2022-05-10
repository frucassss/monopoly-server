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
    private boolean ended = false;
    private String winner = null;

    public Game(String prefix, int sessionNumber, int numberOfPlayers) {
        checkCharactersInString(prefix, "Prefix");
        this.prefix = prefix;
        this.id = prefix + "_" + sessionNumber;
        setNumberOfPlayers(numberOfPlayers);
    }

    public void newPlayer(String playerName) {
        checkIfGameIsNotStarted();
        checkCharactersInString(playerName, "Player name");
        checkIfPlayerIsInGame(playerName);

        Player player = new Player(playerName);
        players.put(playerName, player);

        if (players.size() == numberOfPlayers) {
            started = true;
        }

    }

    public Player findPlayer(String playerName) {
        return players.get(playerName);
    }

    // SETTERS

    public void setNumberOfPlayers(int numberOfPlayers) {
        checkNumberOfPlayers(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
    }
    public void setEnded(){
        this.ended = true;
    }

    public void setWinner(String playerName) {
        this.winner = playerName;
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

    public boolean getEnded() {
        return ended;
    }

    public String getWinner() {
        return winner;
    }

    // CHECKERS - Validate if applies to the rules

    public void checkNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < 2 || numberOfPlayers > 8) {
            throw new IllegalArgumentException("Invalid number of players: min. 2 & max. 8");
        }
    }

    public void checkIfGameIsNotStarted() {
        if (started) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. In this case, it is most likely that you tried to join a game which has already started, or you used a name that is already taken in this game.");
        }
    }

    public void checkCharactersInString(String str, String type) {
        if (str == null) {
            throw new IllegalArgumentException(type + " cannot not be empty.");
        } else if (!str.matches("[a-zA-Z0-9]+") || str.length() > 14 || str.length() < 1) {
            throw new IllegalArgumentException(type + " is invalid, " + type + " can have a max. length of 14 alphabetical and numeric characters");
        }

    }

    public void checkIfPlayerIsInGame(String playerName) {
        if (players.containsKey(playerName)) {
            throw new ForbiddenAccessException("Player is already in game! Can only contain Alphabets.");
        }
    }

    public void checkWinner() {
        int i = 0;
        for (Player p : getPlayers().values()) {
            if (p.getBankrupt()) {
                i++;

            }
        }

        if (i == getNumberOfPlayers() - 1) {
            for (Player p : getPlayers().values()) {
                if (!p.getBankrupt()) {
                    setWinner(p.getName());
                    setEnded();


                }
            }
        }
    }
}