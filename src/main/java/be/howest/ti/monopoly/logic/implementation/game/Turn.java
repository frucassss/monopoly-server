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
    private static final int MAX_ROLL_DOUBLE_COUNT = 3;
    private final Random random = new Random();
    private static final int MAX_DICE_NUMBER = 6;
    private static final int OFF_BY_ONE_ERROR_CORRECTION = 1;

    public Turn(Game game, Player player){
        GameCheck gameCheck = new GameCheck(game);
        gameCheck.checkIfGameStarted();
        gameCheck.checkIfGameIsNotEnded();

        this.player = player;
        this.game = game;
        // check if given player is valid to throw;
        TurnCheck turnCheck = new TurnCheck(this);
        turnCheck.checkIfPlayerCanRoll();
        roll();
        // set next inline current player
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
        if (doubleCount >= MAX_ROLL_DOUBLE_COUNT){
            player.setJailed(true);
            resetDoubleCount();
        }
    }

    private int generateDiceNumber(){
        return random.nextInt(MAX_DICE_NUMBER) + OFF_BY_ONE_ERROR_CORRECTION;
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
