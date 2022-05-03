package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import java.util.List;

public interface IService {
    String getVersion();
    List<Tile> getTiles();
    List<Object> getChance();

    List<Object> getCommunityChest();

    Tile getTile(int position);

    Tile getTile(String name);
}
