package be.howest.ti.monopoly.logic.implementation.checkers.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.game.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.web.exceptions.ForbiddenAccessException;

public class GameCheck {

    private final Game game;

    public GameCheck(Game game) {
        this.game = game;
    }

    public void checkCharactersInString(String str, String type) {
        if (str == null) {
            throw new IllegalArgumentException(type + " cannot not be empty.");
        } else if (!str.matches("[a-zA-Z0-9]+") || str.length() > 14 || str.length() < 1) {
            throw new IllegalArgumentException(type + " is invalid, " + type + " can have a max. length of 14 alphabetical and numeric characters");
        }
    }

    public void checkNumberOfPlayers(int numberOfPlayers) {
        if (numberOfPlayers < 2 || numberOfPlayers > 8) {
            throw new IllegalArgumentException("Invalid number of players: min. 2 & max. 8");
        }
    }

    public void checkIfGameIsNotStarted() {
        if (game.getStarted()) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. In this case, it is most likely that you tried to join a game which has already started, or you used a name that is already taken in this game.");
        }
    }

    public void checkIfPlayerIsInGame(String playerName) {
        if (game.findPlayer(playerName) != null) {
            throw new ForbiddenAccessException("Player is already in game!");
        }
    }
/*
   public void checkWinner() {
        
        int i = 0;
        for (Player p : game.getPlayers()){
            if (p.getBankrupt()) {
                i++;
            }
        }
        if (i == game.getNumberOfPlayers() - 1) {
            for (Player p : game.getPlayers()) {
                if (!p.getBankrupt()) {
                    game.setWinner(p.getName());
                    game.setEnded(game.isEnded());
                }
            }
        }
    }
*/

}
