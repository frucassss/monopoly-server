package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import java.util.List;


public class MonopolyService extends ServiceAdapter {

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public List<Tile> getTiles() {
        return List.of(
                new Tile("Go", 0, "Go", "Go"),
                new StreetTile("Mediterranean", 1, 60, 30, 2, 10, 30, 90, 160, 250, 50, "PURPLE", 2, "PURPLE", "street", "Mediterranean"),
                new Tile("Community Chest I", 2, "Community_Chest_I", "community chest"),
                new StreetTile("Baltic", 3, 60, 30, 4, 20, 60, 180, 320, 450, 50, "PURPLE", 2, "PURPLE", "street", "Baltic"),
                new Tile("Tax Income", 4, "Tax_Income", "Tax Income"),
                new RailroadTile("Reading RR", 5, 200, 100, 25, 4, "BLACK", "railroad", "Reading_RR"),
                new StreetTile("Oriental", 6, 100, 50, 6, 30, 90, 270, 400, 550, 50, "LIGHTBLUE", 3, "LIGHTBLUE", "street", "Oriental"),
                new Tile("Chance I", 7, "Chance_I", "chance"),
                new StreetTile("Vermont", 8, 100, 50, 6, 30, 90, 270, 400, 550, 50, "LIGHTBLUE", 3, "LIGHTBLUE", "street", "Vermont"),
                new StreetTile("Connecticut", 9, 120, 60, 8, 40, 100, 300, 450, 600, 50, "LIGHTBLUE", 3, "LIGHTBLUE", "street", "Connecticut"),
                new Tile("Jail", 10, "Jail", "Jail"),
                new StreetTile("Saint Charles Place", 11, 140, 70, 10, 50, 150, 450, 625, 750, 100, "VIOLET", 3, "VIOLET", "street", "Saint_Charles_Place"),
                new UtilityTile("Electric Company", 12, 150, 75, "4 or 10 times the dice roll", 2, "WHITE", "utility", "Electric_Company"),
                new StreetTile("States", 13, 140, 70, 10, 50, 150, 450, 625, 750, 100, "VIOLET", 3, "VIOLET", "street", "States"),
                new StreetTile("Virginia", 14, 160, 80, 12, 60, 180, 500, 700, 900, 100, "VIOLET", 3, "VIOLET", "street", "Virginia"),
                new RailroadTile("Pennsylvania RR", 15, 200, 100, 25, 4, "BLACK", "railroad", "Pennsylvania_RR"),
                new StreetTile("Saint James", 16, 180, 90, 14, 70, 200, 550, 750, 950, 100, "ORANGE", 3, "ORANGE", "street", "Saint_James"),
                new Tile("Community Chest II", 17, "community chest", "Community_Chest_II"),
                new StreetTile("Tennessee", 18, 180, 90, 14, 70, 200, 550, 750, 950, 100, "ORANGE", 3, "ORANGE", "street", "Tennessee"),
                new StreetTile("New York", 19, 200, 100, 16, 80, 220, 600, 800, 1000, 100, "ORANGE", 3, "ORANGE", "street", "New_York"),
                new Tile("Free Parking", 20, "Free_Parking", "Free Parking"),
                new StreetTile("Kentucky Avenue", 21, 220, 110, 18, 90, 250, 700, 875, 1050, 150, "RED", 3, "RED", "street", "Kentucky_Avenue"),
                new Tile("Chance II", 22, "chance", "Chance_II"),
                new StreetTile("Indiana Avenue", 23, 220, 110, 18, 90, 250, 700, 875, 1050, 150, "RED", 3, "RED", "street", "Indiana_Avenue"),
                new StreetTile("Illinois Avenue", 24, 240, 120, 20, 100, 300, 750, 925, 1100, 150, "RED", 3, "RED", "street", "Illinois_Avenue"),
                new RailroadTile("Baltimore and Ohio RR", 25, 200, 100, 25, 4, "BLACK", "railroad", "Baltimore_and_Ohio_RR"),
                new StreetTile("Atlantic", 26, 260, 130, 22, 110, 330, 800, 975, 1150, 150, "YELLOW", 3, "YELLOW", "street", "Atlantic"),
                new StreetTile("Ventnor", 27, 260, 130, 22, 110, 330, 800, 975, 1150, 150, "YELLOW", 3, "YELLOW", "street", "Ventnor"),
                new UtilityTile("Water Works", 28, 150, 75, "4 or 10 times the dice roll", 2, "WHITE", "utility", "Water_Works"),
                new StreetTile("Marvin Gardens", 29, 280, 140, 24, 120, 360, 850, 1025, 1200, 150, "YELLOW", 3, "YELLOW", "street", "Marvin_Gardens"),
                new Tile("Go to Jail", 30, "Go_to_Jail", "Go to Jail"),
                new StreetTile("Pacific", 31, 300, 150, 26, 130, 390, 900, 1100, 1275, 200, "DARKGREEN", 3, "DARKGREEN", "street", "Pacific"),
                new StreetTile("North Carolina", 32, 300, 150, 26, 130, 390, 900, 1100, 1275, 200, "DARKGREEN", 3, "DARKGREEN", "street", "North Caroline"),
                new Tile("Community Chest III", 33, "Community_Chest_III", "community chest"),
                new StreetTile("Pennsylvania", 34, 320, 160, 28, 150, 450, 1000, 1200, 1400, 200, "DARKGREEN", 3, "DARKGREEN", "street", "Pennsylvania"),
                new RailroadTile("Short Line RR", 35, 200, 100, 25, 4, "BLACK", "railroad", "Short_Line_RR"),
                new Tile("Chance III", 36, "chance", "Chance_III"),
                new StreetTile("Park Place", 37, 350, 175, 35, 175, 500, 1100, 1300, 1500, 200, "DARKBLUE", 2, "DARKBLUE", "street", "Park_Place"),
                new Tile("Luxury Tax", 38, "Luxury_Tax", "Luxury Tax"),
                new StreetTile("Boardwalk", 39, 400, 200, 50, 200, 600, 1400, 1700, 2000, 200, "DARKBLUE", 2, "DARKBLUE", "street", "Boardwalk")
        );
    }

    @Override
    public List<Object> getChance() {
        return List.of(
                "Advance to Boardwalk",
                "Advance to Go (Collect $200)",
                "Advance to Illinois Avenue. If you pass Go, collect $200",
                "Advance to St. Charles Place. If you pass Go, collect $200",
                "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay owner twice the rental to which they are otherwise entitled",
                "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.",
                "Bank pays you dividend of $50",
                "Get Out of Jail Free",
                "Go Back 3 Spaces",
                "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200",
                "Make general repairs on all your property. For each house pay $25. For each hotel pay $100",
                "Speeding fine $15",
                "Take a trip to Reading Railroad. If you pass Go, collect $200",
                "You have been elected Chairman of the Board. Pay each player $50",
                "Your building loan matures. Collect $150"
        );
    }

    @Override
    public List<Object> getCommunityChest() {
        return List.of(
                "Advance to Go (Collect $200)",
                "Bank error in your favor. Collect $200",
                "Doctor's fee. Pay $50",
                "From sale of stock you get $50",
                "Get Out of Jail Free",
                "Go to Jail. Go directly to jail, do not pass Go, do not collect $200",
                "Holiday fund matures. Receive $100",
                "Income tax refund. Collect $20",
                "It is your birthday. Collect $10 from every player",
                "Life insurance matures. Collect $100",
                "Pay hospital fees of $100 ",
                "Pay school fees of $50",
                "Receive $25 consultancy fee ",
                "You are assessed for street repair. $40 per house. $115 per hotel",
                "You have won second prize in a beauty contest. Collect $10",
                "You inherit $100"
        );
    }
}