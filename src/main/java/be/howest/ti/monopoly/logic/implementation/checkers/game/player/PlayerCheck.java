package be.howest.ti.monopoly.logic.implementation.checkers.game.player;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;

public class PlayerCheck {

    private final Player player;

    public PlayerCheck(Player player){
        this.player = player;
    }

    public void checkIfYouOwnProperty(String propertyName) {
        Property property = player.findProperty(propertyName);
        if(!player.getProperties().contains(property)){
            throw new IllegalMonopolyActionException("You don't own this property.");
        }
    }

    public void checkIfIHaveEnoughMoney(int amount) {
        if (amount > player.getMoney()) {
            throw new IllegalMonopolyActionException("You don't have enough money");
        }
    }

    public void checkIfAmountIsNotNegative(int amount) {
        if (amount < 0) {
            throw new IllegalMonopolyActionException("You're trying to pay a negative number?");
        }
    }

    public void checkIfYouCanAddGetOutOfJailFreeCard() {
        if (player.getGetOutOfJailFreeCards() > 2) {
            throw new IllegalMonopolyActionException("You already have 2 get out of jail cars");
        }
    }

    public void checkIfYouCanUseAGetOutOfJailFreeCard() {
        if (player.getGetOutOfJailFreeCards() == 0) {
            throw new IllegalMonopolyActionException("You don't have an get out of jail card");
        }
    }

    public void checkBankrupt() {
        if (player.getBankrupt()) {
            throw new IllegalMonopolyActionException("you are already bankrupt");
        }
    }

    public void checkIfPlayerIsInPrison() {
        if (!player.getJailed()){
            throw new IllegalMonopolyActionException("You aren't in jail");
        }
    }
}
