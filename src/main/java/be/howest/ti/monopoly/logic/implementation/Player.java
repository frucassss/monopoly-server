package be.howest.ti.monopoly.logic.implementation;

import java.util.*;

public class Player {
    private String name;
    private Tile currentTile = new Tile("Go", 0, "Go", "Go");
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
    public void buyProperty(Property property){
        // TODO: code to buy property
    }
    // TODO: auction/sell property method
    public void addGetOutOfJailFreeCard(){
        if(this.getOutOfJailFreeCards < 2){
            this.getOutOfJailFreeCards += 1;
        }
    }
    public void removeGetOutOfJailFreeCard(){
        if(this.getOutOfJailFreeCards > 0){
            this.getOutOfJailFreeCards -= 1;
        }
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
