package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
import be.howest.ti.monopoly.logic.implementation.tile.Tile;

import java.util.*;

public class Player {
    private final Game game;
    private final String name;
    private Tile currentTile = new Tile("Go", 0, "Go");
    private boolean jailed = false;
    private int money = 1500;
    private boolean bankrupt = false;
    private int getOutOfJailFreeCards = 0;
    private final List<Property> properties = new ArrayList<>();
    private final PlayerCheck playerCheck = new PlayerCheck(this);


    // CONSTRUCTOR
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    // METHODS
    public void pay(int amount) {
        checkIfIHaveEnoughMoney(amount);
        checkIfAmountIsNotNegative(amount);
        this.money -= amount;
    }

    public Property findProperty(String propertyName) {
        for (Property property : properties) {
            if (propertyName.equals(property.getProperty())) {
                return property;
            }
        }
        throw new IllegalMonopolyActionException("You dont have this property");
    }

    public void collect(int amount) {
        checkIfAmountIsNotNegative(amount);
        this.money += amount;
    }

    public void addGetOutOfJailFreeCard() {
        checkIfYouCanAddGetOutOfJailFreeCard();
        this.getOutOfJailFreeCards += 1;
    }

    public void useGetOutOfJailFreeCard() {
        checkIfYouCanUseAGetOutOfJailFreeCard();
        this.getOutOfJailFreeCards -= 1;
    }

    public void payPrisonFine() {
        checkIfYouCanPayPrisonFine();
        money -= 50;
        setJailed(false);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }


    // CHECKERS

    private void checkIfAmountIsNotNegative(int amount) {
        if (amount < 0) {
            throw new IllegalMonopolyActionException("You're trying to pay a negative number?");
        }
    }

    private void checkIfIHaveEnoughMoney(int amount) {
        if (amount > money) {
            throw new IllegalMonopolyActionException("You don't have enough money");
        }
    }

    private void checkIfYouCanAddGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards > 2) {
            throw new IllegalMonopolyActionException("You already have 2 get out of jail cars");
        }
    }

    private void checkIfYouCanUseAGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards == 0) {
            throw new IllegalMonopolyActionException("You don't have an get out of jail card");
        }
    }

    private void checkIfYouCanPayPrisonFine() {
        if (money < 50) {
            throw new IllegalMonopolyActionException("You can't pay the fine, you don't have enough money");
        }
    }

    public void checkBankrupt() {
        if (getBankrupt()) {
            throw new IllegalMonopolyActionException("you are already bankrupt");
        }
    }

    // GETTERS

    public Tile receiveCurrentTile() {
        return this.currentTile;
    }

    public Game receiveGame() {
        return game;
    }

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

    public void makeBankrupt() {
        checkBankrupt();
        this.bankrupt = true;
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

    public boolean doIHaveProperty(String propertyName) {
        for (Property property1 : properties) {
            if (propertyName.equals(property1.getProperty())) {
                return true;
            }
        }
        return false;
    }

}
