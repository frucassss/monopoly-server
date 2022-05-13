package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.checkers.game.GameCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import java.util.*;

public class Game {

    private final String prefix;
    private final String id;
    private int numberOfPlayers;
    private boolean started = false;
    private String directSale = null;
    private String currentPlayer = null;
    private boolean canRoll = false;
    private boolean ended = false;
    private String winner = null;
    private int[] lastDiceRoll = {0, 0};
    private final List<Turn> turns = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

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
        players.add(player);

        if(players.size() == numberOfPlayers){
            setStarted(true);
            setCurrentPlayer(playerName);
            setCanRoll(true);
        }
    }

    public Player findPlayer(String playerName){
        for (Player player : players){
            if(player.getName().equals(playerName)){
                return player;
            }
        }
        return null;
    }

    public void addTurn(Turn turn){
        turns.add(turn);
    }

    // SETTERS

    public void setNumberOfPlayers(int numberOfPlayers){
        gameCheck.checkNumberOfPlayers(numberOfPlayers);
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setDirectSale(String directSale) {
        this.directSale = directSale;
    }

    public void setCanRoll(boolean canRoll) {
        this.canRoll = canRoll;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setLastDiceRoll(int[] lastDiceRoll) {
        this.lastDiceRoll = lastDiceRoll;
    }

    // RECEIVERS

    public List<Tile> receiveTiles() {
        return tiles;
    }

    public List<String> receiveChance() {
        return chance;
    }

    public List<String> receiveCommunityChest() {
        return communityChest;
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

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getDirectSale() {
        return directSale;
    }

    public boolean isCanRoll() {
        return canRoll;
    }

    public boolean getStarted() {
        return started;
    }

    public boolean isEnded() {
        return ended;
    }

    public String getWinner() {
        return winner;
    }

    public int[] getLastDiceRoll() {
        return lastDiceRoll;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
