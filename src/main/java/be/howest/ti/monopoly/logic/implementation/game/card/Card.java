package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.List;

public class Card {
    protected final Player player;
    protected final Game game;

    protected Tile moveTile = null;
    protected String moveDescription = null;

    public Card(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public void goToJail() {
        moveTile = game.receiveTileOnName("Jail");
        moveDescription = "Chance card made you go to jail";
        player.setJailed(true);
    }

    public void generalRepair(int houseRepairCost, int hotelRepairCost) {
        int totalAmountToPay = 0;
        List<Property> playerProperties = player.getProperties();
        for (Property property : playerProperties) {
            totalAmountToPay += (property.getHouseCount() * houseRepairCost);
            totalAmountToPay += (property.getHotelCount() * hotelRepairCost);
        }
        player.pay(totalAmountToPay);
    }

    public void advanceTo(String tileName) {
        moveTile = game.receiveTileOnName(tileName);
        moveDescription = "You have to go to: " + tileName;
    }
}
