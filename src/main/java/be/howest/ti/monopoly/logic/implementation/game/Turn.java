package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.checkers.game.GameCheck;
import be.howest.ti.monopoly.logic.implementation.checkers.game.TurnCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Turn {

    private final int[] dices = new int[2];
    private final Player player;
    private final Game game;
    private final List<Move> moves = new ArrayList<>();
    private boolean finished = false;

    private static int doubleCount = 0;
    private final Random random = new Random();
    private final TurnCheck turnCheck = new TurnCheck(this);

    public Turn(Game game, Player player){
        GameCheck gameCheck = new GameCheck(game);
        gameCheck.checkIfGameStarted();
        gameCheck.checkIfGameIsNotEnded();

        this.player = player;
        this.game = game;
        // check if given player is valid to throw;
        roll();
    }

    private void roll(){
        dices[0] = generateDiceNumber();
        dices[1] = generateDiceNumber();
        game.setLastDiceRoll(dices);
        setDoubleRoll();
    }

    private void setDoubleRoll(){
        if(dices[0] == dices[1]){
            rolledDouble();
        }
        else {
            resetDoubleCount();
        }
    }

    private void rolledDouble(){
        doubleCount += 1;
        if (doubleCount >= 3){
            player.setJailed(true);
            resetDoubleCount();
        }
    }

    private int generateDiceNumber(){
        return random.nextInt(6) + 1;
    }


    // RECEIVERS

    public Game receiveGame(){
        return game;
    }

    public Player receivePlayer(){
        return player;
    }

    public int receiveDoubleCount(){
        return doubleCount;
    }


    // SETTERS

    public void makeFinished(){
        finished = true;
    }

    public void resetDoubleCount(){
        doubleCount = 0;
    }

    // GETTERS

    public String getPlayer() {
        return player.getName();
    }

    public int[] getRoll() {
        return dices;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public boolean getFinished() {
        return finished;
    }
}
