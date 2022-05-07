package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Turn {
    private final int[] dices = new int[2];
    private final Player player;
    private final List<Move> moves = new ArrayList<>();
    protected List<Tile> tiles;
    protected List<String> chance;
    protected List<String> communityChest;

    Turn(Player player, List<String> chance, List<String> communityChest, List<Tile> tiles){
        this.player = player;
        this.tiles = tiles;
        this.chance = chance;
        this.communityChest = communityChest;
        roll();
    }

    public void roll(){
        dices[0] = randomNumberBetween2Values(1,6);
        dices[1] = randomNumberBetween2Values(1,6);
    }

    public int getDicesAddedUp(){
        return dices[0] + dices[1];
    }

    public int randomNumberBetween2Values(int min, int max){
        Random random = new Random();
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

    public void addMove(Tile tile, String description){
        //todo je moet controleren of de tile al van iemand is of niet, is dus misschien verstandigst om eerst de game classe te hebben.
    }
}
