package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.*;

public class Player {
    private final String name;
    private Tile currentTile = new Tile("Go", 0, "Go");
    private boolean jailed = false;
    private int money = 1500;
    private boolean bankrupt = false;
    private int getOutOfJailFreeCards = 0;
    private final List<Property> properties = new ArrayList<>();


    // CONSTRUCTOR
    public Player(String name) {
        this.name = name;
    }

    // METHODS
    public void pay(int amount) {
        this.money -= amount;
    }

    public void collect(int amount) {
        this.money += amount;
    }

    public void addGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards < 2) {
            this.getOutOfJailFreeCards += 1;
        }
    }

    public void useGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards > 0) {
            this.getOutOfJailFreeCards -= 1;
        }
    }

    public void payPrisonFine() {
        money -= 50;
        setJailed(false);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public void mortgageProperty(Property property) {
        this.properties.forEach(property1 -> {
            if (property.equals(property1) && !property.isMortgage()) {
                Monopoly().getTiles().forEach(tile -> {
                    if (tile.getName().equals(property.getProperty())) {
                        money = money + tile.getMortgage();
                        property.mortgageProperty();
                    }
                });
            }
        });
    }

    public void unMortgageProperty(Property property){
        this.properties.forEach(property1 -> {
            if (property.equals(property1) && property.isMortgage()) {
                Monopoly().getTiles().forEach(tile -> {
                    if (tile.getName().equals(property.getProperty())) {
                        money = money - (tile.getMortgage() + (tile.getMortgage()*0.1));
                        property.unMortgageProperty();
                    }
                });
            }
        });
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getCurrentTile() {
        return currentTile.getName();
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
