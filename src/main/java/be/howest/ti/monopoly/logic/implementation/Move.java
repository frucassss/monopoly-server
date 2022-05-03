package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.Random;

public class Move {
    private Tile tile;
    private String description;

    Move(Tile tile){
        this.tile = tile;
    }

    private String getTypeFromTile(Tile tile){
        return tile.getType();
    }

    private String getRandomChance(){
        Random random = new Random();
        int value = random.nextInt(15 + 1) + 1;
        return Monopoly.getChance().get(value);
    }

    private String getRandomCommunityChest(){
        Random random = new Random();
        int value = random.nextInt(16 + 1) + 1;
        return Monopoly.getCommunityChest().get(value);
    }



    public String getTile() {
        return tile.getName();
    }

    public String getDescription() {
        return description;
    }


}
