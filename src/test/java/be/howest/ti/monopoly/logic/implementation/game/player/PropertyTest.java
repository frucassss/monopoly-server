package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.logic.implementation.tile.UtilityTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    @Test
    void testIfYouTryToMakeAPropertyOfANormalTile() {
        Tile Go = new Tile("Go", 0, "Go");
        assertThrows(IllegalMonopolyActionException.class, () -> {
            Property property = new Property(Go);
        });
    }

    @Test
    void makePropertyAllDifferentTile() {
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Tile readingRailRoadTile = new RailroadTile("Reading RR", 5, "railroad", 200, 100, 4, "BLACK", -1);
        Tile waterWorksTile = new UtilityTile("Water Works", 28, "utility", 150, 75, 2, "WHITE", -1);
        assertDoesNotThrow(() -> {
            Property mediterraneanProperty = new Property(mediterraneanTile);
        });
        assertDoesNotThrow(() -> {
            Property readingRailRoadProperty = new Property(readingRailRoadTile);
        });
        assertDoesNotThrow(() -> {
            Property waterWorksProperty = new Property(waterWorksTile);
        });
    }

    @Test
    void testIfICanAccesTheHouseCost(){
        Tile mediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property mediterraneanProperty = new Property(mediterraneanTile);
        assertEquals(50,mediterraneanProperty.getHousePrice());
    }
}