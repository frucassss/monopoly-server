package be.howest.ti.monopoly.logic.implementation;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private int[] roll = new int[2];
    private Player player= new Player;
    private List<Move> moves = new ArrayList<>();
    Turn(int[] roll, Player player, List<Move> moves){
        this.moves = moves;
        this.player = player;
        this.roll = roll;
    }

    public int[] getRoll() {
        return roll;
    }

    public Player getPlayer() {
        return player.getName;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void rollDice(){
        int dice1 = (int) ((Math.random()*(6-1)) + 1);
        int dice2 = (int) ((Math.random()*(6-1)) + 1);
        roll[0] = dice1;
        roll[1] = dice2;
    }
}
