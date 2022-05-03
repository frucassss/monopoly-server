package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private int[] roll = new int[2];
    private Player player;
    private List<Move> moves = new ArrayList<>();

    Turn(Player player){
        this.player = player;
    }

    public int[] getRoll() {
        return roll;
    }

    public String getPlayer() {
        return player.getName();
    }

    public List<Move> getMoves() { return moves; }

    public void addMove(Tile tile){
        //todo je moet controleren of de tile al van iemand is of niet, is dus misschien verstandigst om eerst de game classe te hebben.
    }
}
