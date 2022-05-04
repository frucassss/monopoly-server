package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {
    @Test
    public void testMoveDescription() {
        Tile freeParking = new Tile("Free Parking", 20, "Free Parking");
        Tile taxIncome = new Tile("Tax Income", 4, "Tax Income");
        Tile chance = new Tile("Chance I", 7, "chance");
        Tile communityChest = new Tile("Community Chest I", 2, "community chest");

        Move move;
        move = new Move(freeParking);
        assertEquals("does nothing special", move.getDescription());
        move = new Move(taxIncome);
        assertEquals("has to pay 200 on taxes", move.getDescription());
        move = new Move(chance);
        assertTrue(Monopoly.getChance().contains(move.getDescription()));
        move = new Move(communityChest);
        assertTrue(Monopoly.getCommunityChest().contains(move.getDescription()));
    }
}