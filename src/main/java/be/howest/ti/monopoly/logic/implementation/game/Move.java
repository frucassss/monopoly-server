package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.game.card.Chance;
import be.howest.ti.monopoly.logic.implementation.game.card.CommunityChest;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
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

    public Move(Move move) {
        this.description = move.getDescription();
        this.tile = move.receiveTile();
    }

    public Move(Turn turn, Game game, Player player) {
        this.turn = turn;
        this.dices = turn.getRoll();
        this.game = game;
        this.player = player;

        this.tiles = game.receiveTiles();
        this.chance = game.receiveChance();
        this.communityChest = game.receiveCommunityChest();

        processDiceRoll();
        processMove();
    }

    private void processDiceRoll() {
        int nextTilePosition = calculateNextTilePosition();
        this.tile = game.receiveTileOnPosition(nextTilePosition);
    }

    private int calculateNextTilePosition() {
        int currentTilePosition = player.receiveCurrentTile().getPosition();

        if (!player.getJailed()) {
            int nextTilePosition = currentTilePosition + dices[0] + dices[1];

            if (nextTilePosition >= tiles.size()) {
                nextTilePosition -= tiles.size();
                passedGo = true;
            }
            return nextTilePosition;
        }

        return currentTilePosition;
    }

    private void processMove() {
        processGoToJailMove();
        processChanceMove();
        processCommunityChestMove();
        processGoMove();
        processFreeParkingMove();
        processTaxIncomeMove();
        processLuxuryTaxMove();
        processPropertyMove();
        processJailMove();

        player.setCurrentTile(tile);
    }

    private void processGoMove() {
        if (passedGo) {
            if (tile.getPosition() > GO_POSITION) {
                description = "passes 'GO!' and receives 200 for it";
                turn.addMove(new Move(this));
                player.collect(200);
            } else if (tile.getPosition() == GO_POSITION) {
                description = "landed on 'GO!' and receives 200 for it";
                turn.addMove(new Move(this));
                player.collect(200);
                turn.makeFinished();
            }
            passedGo = false;
        }
    }

    private void processGoToJailMove() {
        if (tile.getType().equals("Go to Jail")) {
            description = "has to go to jail";
            turn.addMove(new Move(this));
            player.setJailed(true);
        }
    }

    private void processJailMove() {
        if (player.getJailed()) {
            tile = game.receiveTileOnName("Jail");
            description = "is stuck in jail";
            turn.addMove(new Move(this));
            turn.makeFinished();
        } else if (tile.getType().equals("Jail")) {
            description = "is just visiting jail";
            turn.addMove(new Move(this));
            turn.makeFinished();
        }
    }

    private void processChanceMove() {
        if (tile.getType().equals("chance")) {
            description = receiveRandomChance();
            turn.addMove(new Move(this));
            Chance chanceInstance = new Chance(description, player, game, this);
            if (chanceInstance.getTile() != null) {
                tile = chanceInstance.getTile();
            } else {
                turn.makeFinished();
            }
        }
    }

    private void processCommunityChestMove() {
        if (tile.getType().equals("community chest")) {
            description = receiveRandomCommunityChest();
            turn.addMove(new Move(this));
            CommunityChest communityChestMove = new CommunityChest(description, player, game, this);
            if (communityChestMove.getTile() != null) {
                tile = communityChestMove.getTile();
            } else {
                turn.makeFinished();
            }
        }
    }

    private void processFreeParkingMove() {
        if (tile.getType().equals("Free Parking")) {
            description = "has a free parking spot";
            turn.addMove(new Move(this));
            turn.makeFinished();
        }
    }

    private void processTaxIncomeMove() {
        if (tile.getType().equals("Tax Income")) {
            description = "has to pay 200 on taxes";
            turn.addMove(new Move(this));
            player.pay(200);
            turn.makeFinished();
        }
    }

    private void processLuxuryTaxMove() {
        if (tile.getType().equals("Luxury Tax")) {
            description = "has to pay 75 on taxes";
            turn.addMove(new Move(this));
            player.pay(75);
            turn.makeFinished();
        }
    }

    private void processPropertyMove() {
        if (isTileAProperty()) {
            for (Player other : game.getPlayers()) {
                for (Property property : other.getProperties()) {
                    if (property.getProperty().equals(tile.getName())) {
                        determineDescriptionForOwnedProperty(other, property);
                        turn.addMove(new Move(this));
                        turn.makeFinished();
                        return;
                    }
                }
            }

            description = "can buy this property in direct sale";
            turn.addMove(new Move(this));
            game.setDirectSale(tile.getName());
        }
    }

    private void determineDescriptionForOwnedProperty(Player other, Property property) {
        if (other.equals(player)) {
            description = "already owns this property";
        } else if (property.getMortgage()) {
            description = "doesn't have to pay rent, property has mortgage";
        } else {
            description = "should pay rent";
        }
    }

    private boolean isTileAProperty() {
        return tile.getType().equals("street") || tile.getType().equals("railroad") || tile.getType().equals("utility");
    }


    private String receiveRandomChance() {
        int value = random.nextInt(chance.size());
        return chance.get(value);
    }

    private String receiveRandomCommunityChest() {
        int value = random.nextInt(communityChest.size());
        return communityChest.get(value);
    }

    private Tile receiveTile() {
        return tile;
    }

    public String getTile() {
        return tile.getName();
    }

    public String getDescription() {
        return description;
    }

    public void setPassedGo(boolean passedGo) {
        this.passedGo = passedGo;
    }
}
