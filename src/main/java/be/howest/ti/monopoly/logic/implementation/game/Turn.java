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

    private final Random random = new Random();
    private final TurnCheck turnCheck = new TurnCheck(this);
    private final GameCheck gameCheck;

    public Turn(Game game, Player player){
        this.player = player;
        this.game = game;
        gameCheck = new GameCheck(game);
        roll();
    }

    private void roll(){
        gameCheck.checkIfGameStarted();

        dices[0] = randomNumberBetween2Values(1,6);
        dices[1] = randomNumberBetween2Values(1,6);
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


    // SETTERS

    public void makeFinished(){
        finished = true;
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
