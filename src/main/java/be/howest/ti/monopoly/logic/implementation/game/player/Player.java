package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.*;

public class Player {
    private final String name;
    private Tile currentTile = new Tile("Go", 0, "Go");
    private boolean jailed = false;
    private int money = 1500;
    private boolean bankrupt = false;
    private int getOutOfJailFreeCards = 0;
    private final List<Property> properties;


    // CONSTRUCTOR
    public Player(String name) {
        this.name = name;
    }

    // METHODS
    public void payMoney(int amount){
        this.money -= amount;
    }
    public void collectMoney(int amount){
        this.money += amount;
    }
    public void addGetOutOfJailFreeCard(){
        if(this.getOutOfJailFreeCards < 2){
            this.getOutOfJailFreeCards += 1;
        }
    }
    public void useGetOutOfJailFreeCard(){
        if(this.getOutOfJailFreeCards > 0){
            this.getOutOfJailFreeCards -= 1;
        }
    }
    public void payPrisonFine(){
        money -= 50;
        setJailed(false);
    }
    public void addProperty(Property property){
        properties.add(property);
    }
    public void removeProperty(Property property){
        properties.remove(property);
    }

    // GETTERS
    public String getName() {
        return name;
    }
    public Tile getCurrentTile() {
        return currentTile;
    }
    public boolean getJailed() {
        return jailed;
    }
    public int getMoney() {
        return money;
    }
    public boolean getBankrupt() {
        return bankrupt;
    }
    public int getGetOutOfJailFreeCards() {
        return getOutOfJailFreeCards;
    }
    public List<Property> getProperties() {
        return properties;
    }

    // SETTERS
    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }
    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }
    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    // BUILT-IN
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
