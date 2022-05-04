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
        checkIfYouCanAddGetOutOfJailFreeCard();
        this.getOutOfJailFreeCards += 1;
    }

    private void checkIfYouCanAddGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards > 2) {
            throw new IllegalMonopolyActionException("You already have 2 get out of jail cars");
        }
    }

    public void useGetOutOfJailFreeCard() {
        checkIfYouCanUseAGetOutOfJailFreeCard();
        this.getOutOfJailFreeCards -= 1;
    }

    public void checkIfYouCanUseAGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards == 0) {
            throw new IllegalMonopolyActionException("You don't have an get out of jail card");
        }
    }

    public void payPrisonFine() {
        checkIfYouCanPayPrisonFine();
        money -= 50;
        setJailed(false);
    }

    public void checkIfYouCanPayPrisonFine() {
        if (money < 50) {
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
        checkIfYouOwnProperty(property);
        checkIfPropertyIsNotMortgaged(property);
        this.collect(property.getMortgageValue());
        property.mortgageProperty();
    }

    public void checkIfYouOwnProperty(Property property) {
        for (Property propertiesFormPlayer : properties) {
            if (propertiesFormPlayer.equals(property)) {
                return;
            }
        }
        throw new IllegalMonopolyActionException("You don't own this property.");
    }

    public void checkIfPropertyIsNotMortgaged(Property property) {
        if (property.isMortgage()) {
            throw new IllegalMonopolyActionException("It's already mortgaged");
        }
    }

    public void checkIfPropertyIsMortgaged(Property property) {
        if (!property.isMortgage()) {
            throw new IllegalMonopolyActionException("It's not mortgaged");
        }
    }

    public void checkIfYouHaveEnoughMoneyToUnMortgageProperty(Property property) {
        if (this.money < (int) (property.getMortgageValue() + (property.getMortgageValue() * 0.1))) {
            throw new IllegalMonopolyActionException("You don't have enough money to un mortgage this property.");
        }
    }

    public void unMortgageProperty(Property property) {
        checkIfYouHaveEnoughMoneyToUnMortgageProperty(property);
        checkIfYouOwnProperty(property);
        checkIfPropertyIsMortgaged(property);
        this.pay((int) (property.getMortgageValue() + (property.getMortgageValue() * 0.1)));
        property.unMortgageProperty();
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
