package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.*;

public class Move {

    private String description;
    private Tile tile;

    private boolean passedGo = false;
    static final int GO_POSITION = 0;

    private int[] dices;
    private Player player;
    private Game game;
    private Turn turn;
    private final Random random = new Random();
    private List<Tile> tiles;
    private List<String> chance;
    private List<String> communityChest;

    public Move(Move move){
        this.description = move.getDescription();
        this.tile = move.receiveTile();
    }

    public Move(Turn turn) {
        this.turn = turn;
        this.dices = turn.getRoll();
        this.player = turn.receivePlayer();
        this.game = turn.receiveGame();

        this.tiles = game.receiveTiles();
        this.chance = game.receiveChance();
        this.communityChest = game.receiveCommunityChest();

        processDiceRoll();
        processMove();
    }

    private void processDiceRoll(){
        int nextTilePosition = calculateNextTilePosition();
        this.tile = game.receiveTileOnPosition(nextTilePosition);
    }

    private int calculateNextTilePosition(){
        int currentTilePosition = player.receiveCurrentTile().getPosition();

        if (!player.getJailed()){
            int nextTilePosition = currentTilePosition + dices[0] + dices[1];

            if (nextTilePosition >= tiles.size()){
                nextTilePosition -= tiles.size();
                passedGo = true;
            }
            return nextTilePosition;
        }

        return currentTilePosition;
    }

    private void processMove(){
        processGoMove();
        processGoToJailMove();
        processChanceMove();
        processCommunityChestMove();

        processJailMove();

        player.setCurrentTile(tile);
    }

    private void processGoMove(){
        if (passedGo) {
            if (tile.getPosition() > GO_POSITION){
                description = "passes 'GO!' and receives 200 for it";
            }
            else {
                description = "landed on 'GO!' and receives 200 for it";
            }

            player.collect(200);
            turn.addMove(new Move(this));
            passedGo = false;
        }
    }

    private void processGoToJailMove(){
        if (tile.getType().equals("go to jail")){
            description = "has to go to jail";
            turn.addMove(new Move(this));
            player.setJailed(true);
        }
    }

    private void processJailMove(){
        if (player.getJailed()) {
            tile = game.receiveTileOnName("Jail");
            description = "is stuck in jail";
            turn.addMove(new Move(this));
        }
        else if (tile.getType().equals("Jail")) {
            description = "is just visiting jail";
            turn.addMove(new Move(this));
        }
    }

    private void processChanceMove(){
        if (tile.getType().equals("chance")){
            description = receiveRandomChance();
            turn.addMove(new Move(this));
            // TODO: execute action of received chance card.
        }
    }

    private void processCommunityChestMove(){
        if (tile.getType().equals("community chest")){
            description = receiveRandomCommunityChest();
            turn.addMove(new Move(this));
            // TODO: execute action of received community chest card.
        }
    }

    private String receiveRandomChance(){
        int value = random.nextInt(chance.size());
        return chance.get(value);
    }

    private String receiveRandomCommunityChest(){
        int value = random.nextInt(communityChest.size());
        return communityChest.get(value);
    }

    private Tile receiveTile(){
        return tile;
    }

    public String getTile() {
        return tile.getName();
    }

    public String getDescription() {
        return description;
    }
}
