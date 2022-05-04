package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.Objects;
import java.util.Random;

public class Move {
    private Tile tile;
    private String description;

    Move(Tile tile){
        this.tile = tile;
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
                break;
        }

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
