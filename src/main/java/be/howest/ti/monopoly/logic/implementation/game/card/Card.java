package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Move;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

public class Card {
    protected final Player player;
    protected final Game game;
    protected final Move move;

    protected Tile moveTile = null;

    public Card(Player player, Game game, Move move) {
        this.player = player;
        this.game = game;
        this.move = move;
    }

    public void goToJail() {
        player.setJailed(true);
        advanceTo("Jail");
    }

    public void generalRepair(int houseRepairCost, int hotelRepairCost) {
        int totalAmountToPay = 0;
        for (Property property : player.getProperties()) {
            totalAmountToPay += (property.getHouseCount() * houseRepairCost);
            totalAmountToPay += (property.getHotelCount() * hotelRepairCost);
        }
        player.pay(totalAmountToPay);
    }

    public void advanceTo(String tileName) {
        moveTile = game.receiveTileOnName(tileName);
        determineIfYouPassedGo(moveTile);
    }

    public void determineIfYouPassedGo(Tile nextTile){
        int currentTilePosition = move.receiveTile().getPosition();
        int nextTilePosition = nextTile.getPosition();
        if (nextTilePosition - currentTilePosition <= 0 && !player.getJailed()){
            move.setPassedGo(true);
        }
    }

    public Tile getTile() {
        return moveTile;
    }
}
