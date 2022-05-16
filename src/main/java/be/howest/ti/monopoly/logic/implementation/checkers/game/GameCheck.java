package be.howest.ti.monopoly.logic.implementation.checkers.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.game.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.web.exceptions.ForbiddenAccessException;

public class GameCheck {

    private final Game game;
    private static final int MAX_STRING_LENGTH = 14;
    private static final int MIN_STRING_LENGTH = 1;
    private static final String STRING_CONSTRAINTS = "[a-zA-Z0-9]+";
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 8;

    public GameCheck(Game game) {
        this.game = game;
    }

    public void checkCharactersInString(String str, String type) {
        if (str == null) {
            throw new IllegalArgumentException(type + " cannot not be empty.");
        } else if (!str.matches(STRING_CONSTRAINTS) || str.length() > MAX_STRING_LENGTH || str.length() < MIN_STRING_LENGTH) {

            throw new IllegalArgumentException(type + " is invalid, " + type + " can have a max. length of 14 alphabetical and numeric characters");
        }
    }


    public void checkNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Invalid number of players: min. 2 & max. 8");
        }
    }

    public void checkIfGameIsNotStarted() {
        if (game.getStarted()) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. In this case, it is most likely that you tried to join a game which has already started, or you used a name that is already taken in this game.");
        }
    }


   public void checkWinner() {
        
        int numberOfBankruptPlayers = 0;
        for (Player p : game.getPlayers()){
            if (p.getBankrupt()) {
                numberOfBankruptPlayers++;
            }
        }
        if (numberOfBankruptPlayers == game.getNumberOfPlayers() - 1) {
            for (Player p : game.getPlayers()) {
                if (!p.getBankrupt()) {
                    game.setWinner(p.getName());
                    game.setEnded(game.getEnded());
                }
            }
        }
    }



    public void checkIfGameStarted() {
        if (!game.getStarted()) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. Rolling the dice is not allowed when the game hasn't started yet.");
        }
    }

    public void checkIfPlayerIsInGame(String playerName) {
        if (game.findPlayer(playerName) != null) {
            throw new ForbiddenAccessException("Player is already in game!");
        }
    }

    public void checkIfGameIsNotEnded() {
        if (game.getEnded()) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. Game already ended.");
        }
    }

}
