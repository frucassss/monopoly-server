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

        roll();
    }

    private void roll(){
        dices[0] = randomNumberBetween2Values(1,6);
        dices[1] = randomNumberBetween2Values(1,6);
        game.setLastDiceRoll(dices);
        if(dices[0] == dices[1]){
            doubleCount += 1;
        }
    }

    private int randomNumberBetween2Values(int min, int max){
        return random.nextInt(max-min) + min;
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
