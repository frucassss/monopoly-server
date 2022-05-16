package be.howest.ti.monopoly.logic.implementation.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.checkers.game.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;
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

    private int numberOfRollsWhileInJail = 0;
    private final PlayerCheck playerCheck = new PlayerCheck(this);



    // CONSTRUCTOR
    public Player(String name) {
        this.name = name;
    }

    // METHODS
    public void pay(int amount) {
        playerCheck.checkIfAmountIsNotNegative(amount);
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
        playerCheck.checkIfAmountIsNotNegative(amount);
        this.money += amount;
    }

    public void addGetOutOfJailFreeCard() {
        playerCheck.checkIfYouCanAddGetOutOfJailFreeCard();
        this.getOutOfJailFreeCards += 1;
    }

    public void useGetOutOfJailFreeCard() {
        playerCheck.checkIfPlayerIsInPrison();
        playerCheck.checkIfYouCanUseAGetOutOfJailFreeCard();
        this.getOutOfJailFreeCards -= 1;
        setJailed(false);
    }

    public void payPrisonFine() {
        playerCheck.checkIfPlayerIsInPrison();
        pay(50);
        setJailed(false);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public void incrementNumberOfRollsWhileInJail(){
        numberOfRollsWhileInJail += 1;
    }

    public void resetNumberOfRollsWhileInJail(){
        numberOfRollsWhileInJail = 0;
    }

    // RECEIVERS

    public Tile receiveCurrentTile() {
        return this.currentTile;
    }

    public int receiveNumberOfRollsWhileInJail(){
        return numberOfRollsWhileInJail;
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

    public void makeBankrupt() {
        playerCheck.checkBankrupt();
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
        for (Property property : properties) {
            if (propertyName.equals(property.getProperty())) {
                return true;
            }
        }
        return false;
    }

}
