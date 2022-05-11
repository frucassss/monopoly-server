package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Move {
    private Tile tile;
    private String description;
    protected List<Tile> tiles;
    protected List<String> chance;
    protected List<String> communityChest;

    Move(Tile tile, List<String> chance, List<String> communityChest,List<Tile> tiles){
        this.tile = tile;
        this.tiles = tiles;
        this.chance = chance;
        this.communityChest = communityChest;
        switch (tile.getType()){
            case "chance":
                description = getRandomChance();
                break;
            case "community chest":
                description = getRandomCommunityChest();
                break;
            case "Free Parking":
                description = "does nothing special";
                break;
            case "Tax Income":
                description = "has to pay 200 on taxes";
                break;
            case "Luxury Tax":
                description = "has to pay 75 on taxes";
                break;
            case "Go":
                break;
            case "street":
                break;
            case "railroad":
                break;
            case "jail":
                break;
            case "utility":
                break;
            case "go to jail":
                description = "has to go to jail";
                break;
            default:
                throw new IllegalMonopolyActionException("This tile type doesn't exist?");
        }

    }

    private String getRandomChance(){
        Random random = new Random();
        int value = random.nextInt(chance.size() - 1) + 1;
        return chance.get(value);
    }

    private String getRandomCommunityChest(){
        Random random = new Random();
        int value = random.nextInt(communityChest.size() - 1) + 1;
        return communityChest.get(value);
    }

    public String getTile() {
        return tile.getName();
    }

    public String getDescription() {
        return description;
    }
}
