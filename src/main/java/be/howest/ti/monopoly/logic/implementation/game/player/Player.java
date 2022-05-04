package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
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
        if (money >= amount) {
            this.money -= amount;
        } else {
            throw new IllegalMonopolyActionException("You don't have enough money");
        }
    }

    public void collect(int amount) {
        this.money += amount;
    }

    public void addGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards < 2) {
            this.getOutOfJailFreeCards += 1;
        } else {
            throw new IllegalMonopolyActionException("You already have 2 get out of jail cars");
        }
    }

    public void useGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards > 0) {
            this.getOutOfJailFreeCards -= 1;
        } else {
            throw new IllegalMonopolyActionException("You don't have an get out of jail card");
        }
    }

    public void payPrisonFine() {
        if(money >= 50){
            money -= 50;
            setJailed(false);
        } else {
            throw new IllegalMonopolyActionException("You can't pay the fine, you don't have enough money");
        }
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public void mortgageProperty(Property property) {
        for (Property propertyFromPlayerProperties : properties) {
            if (property.equals(propertyFromPlayerProperties)) {
                if (!property.isMortgage()) {
                    this.collect(propertyFromPlayerProperties.getMortgageValue());
                    property.mortgageProperty();
                    return;
                } else {
                    throw new IllegalMonopolyActionException("It's already mortgaged");
                }
            }
        }
        throw new IllegalMonopolyActionException("You don't own this property.");
    }

    public void unMortgageProperty(Property property) {
        for (Property propertyFromPlayerProperties : properties) {
            if (property.equals(propertyFromPlayerProperties)) {
                if (property.isMortgage()) {
                    if (this.money >= (int) (propertyFromPlayerProperties.getMortgageValue() + (propertyFromPlayerProperties.getMortgageValue() * 0.1))) {
                        this.pay((int) (propertyFromPlayerProperties.getMortgageValue() + (propertyFromPlayerProperties.getMortgageValue() * 0.1)));
                        property.unMortgageProperty();
                        return;
                    } else {
                        throw new IllegalMonopolyActionException("You don't have enough money to un mortgage this property.");
                    }
                } else {
                    throw new IllegalMonopolyActionException("This isn't mortgaged yet.");
                }
            }
        }
        throw new IllegalStateException("You don't own this property.");
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
