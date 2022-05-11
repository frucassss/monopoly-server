package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Turn {

    private final int[] dices = new int[2];
    private final Player player;
    private final Game game;
    private final List<Move> moves = new ArrayList<>();
    private final Random random = new Random();

    public Turn(Game game, Player player){
        this.player = player;
        this.game = game;
        roll();
    }

    private void roll(){
        dices[0] = randomNumberBetween2Values(1,6);
        dices[1] = randomNumberBetween2Values(1,6);
    }

    private int randomNumberBetween2Values(int min, int max){
        return random.nextInt(max-min) + min;
    }

    public String getPlayer() {
        return player.getName();
    }
    public int[] getRoll() {
        return dices;
    }
    public List<Move> getMoves() {
        return moves;
    }

}
