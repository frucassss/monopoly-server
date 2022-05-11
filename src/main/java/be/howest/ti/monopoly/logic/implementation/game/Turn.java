package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.game.Move;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private final int[] dices = new int[2];
    private final Player player;
    private final List<Move> moves = new ArrayList<>();
    protected List<Tile> tiles;
    protected List<String> chance;
    protected List<String> communityChest;

    public Turn(Game game, Player player){
        this.player = player;
        this.tiles = player.getGame();
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
