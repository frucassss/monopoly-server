package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.RailroadTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;
import be.howest.ti.monopoly.logic.implementation.tile.UtilityTile;

import java.util.List;

public class Monopoly {
    private static List<Tile> tiles = List.of(
                    new Tile("Go",0,"Go"),
                    new StreetTile("Mediterranean",1,"street",60,30,2,"PURPLE",2,10,30,90,160,250,50,"PURPLE"),
                    new Tile("Community Chest I",2,"community chest"),
                    new StreetTile("Baltic",3,"street",60,30,2,"PURPLE",4,20,60,180,320,450,50,"PURPLE"),
                    new Tile("Tax Income",4,"Tax Income"),
                    new RailroadTile("Reading RR",5,"railroad",200,100,4,"BLACK",-1),
                    new StreetTile("Oriental",6,"street",100,50,3,"LIGHTBLUE",6,30,90,270,400,550,50,"LIGHTBLUE"),
                    new Tile("Chance I",7,"chance"),
                    new StreetTile("Vermont",8,"street",100,50,3,"LIGHTBLUE",6,30,90,270,400,550,50,"LIGHTBLUE"),
                    new StreetTile("Connecticut",9,"street",120,60,3,"LIGHTBLUE",8,40,100,300,450,600,50,"LIGHTBLUE"),
                    new Tile("Jail",10,"Jail"),
                    new StreetTile("Saint Charles Place",11,"street",140,70,3,"VIOLET",10,50,150,450,625,750,100,"VIOLET"),
                    new UtilityTile("Electric Company",12,"utility",150,75,2,"WHITE",-1),
                    new StreetTile("States",13,"street",140,70,3,"VIOLET",10,50,150,450,625,750,100,"VIOLET"),
                    new StreetTile("Virginia",14,"street",160,80,3,"VIOLET",12,60,180,500,700,900,100,"VIOLET"),
                    new RailroadTile("Pennsylvania RR",15,"railroad",200,100,4,"BLACK",-1),
                    new StreetTile("Saint James",16,"street",180,90,3,"ORANGE",14,70,200,550,750,950,100,"ORANGE"),
                    new Tile("Community Chest II",17,"community chest"),
                    new StreetTile("Tennessee",18,"street",180,90,3,"ORANGE",14,70,200,550,750,950,100,"ORANGE"),
                    new StreetTile("New York",19,"street",200,100,3,"ORANGE",16,80,220,600,800,1000,100,"ORANGE"),
                    new Tile("Free Parking",20,"Free Parking"),
                    new StreetTile("Kentucky Avenue",21,"street",220,110,3,"RED",18,90,250,700,875,1050,150,"RED"),
                    new Tile("Chance II",22,"chance"),
                    new StreetTile("Indiana Avenue",23,"street",220,110,3,"RED",18,90,250,700,875,1050,150,"RED"),
                    new StreetTile("Illinois Avenue",24,"street",240,120,3,"RED",20,100,300,750,925,1100,150,"RED"),
                    new RailroadTile("Baltimore and Ohio RR",25,"railroad",200,100,4,"BLACK",-1),
                    new StreetTile("Atlantic",26,"street",260,130,3,"YELLOW",22,110,330,800,975,1150,150,"YELLOW"),
                    new StreetTile("Ventnor",27,"street",260,130,3,"YELLOW",22,110,330,800,975,1150,150,"YELLOW"),
                    new UtilityTile("Water Works",28,"utility",150,75,2,"WHITE",-1),
                    new StreetTile("Marvin Gardens",29,"street",280,140,3,"YELLOW",24,120,360,850,1025,1200,150,"YELLOW"),
                    new Tile("Go to Jail",30,"Go to Jail"),
                    new StreetTile("Pacific",31,"street",300,150,3,"DARKGREEN",26,130,390,900,1100,1275,200,"DARKGREEN"),
                    new StreetTile("North Carolina",32,"street",300,150,3,"DARKGREEN",26,130,390,900,1100,1275,200,"DARKGREEN"),
                    new Tile("Community Chest III",33,"community chest"),
                    new StreetTile("Pennsylvania",34,"street",320,160,3,"DARKGREEN",28,150,450,1000,1200,1400,200,"DARKGREEN"),
                    new RailroadTile("Short Line RR",35,"railroad",200,100,4,"BLACK",-1),
                    new Tile("Chance III",36,"chance"),
                    new StreetTile("Park Place",37,"street",350,175,2,"DARKBLUE",35,175,500,1100,1300,1500,200,"DARKBLUE"),
                    new Tile("Luxury Tax",38,"Luxury Tax"),
                    new StreetTile("Boardwalk",39,"street",400,200,2,"DARKBLUE",50,200,600,1400,1700,2000,200,"DARKBLUE")
    );

    public static List<Tile> getTiles() {
        return tiles;
    }
}
