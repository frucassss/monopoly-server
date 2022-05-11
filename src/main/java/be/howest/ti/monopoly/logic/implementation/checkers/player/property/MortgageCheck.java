package be.howest.ti.monopoly.logic.implementation.checkers.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;

public class MortgageCheck {

    private final Player player;
    private final Property property;

    public MortgageCheck(Player player, Property property){
        this.player = player;
        this.property = property;
    }

    public void checkIfPropertyIsNotMortgaged() {
        if (property.getMortgage()) {
            throw new IllegalMonopolyActionException("It's already mortgaged");
        }
    }

    public void checkIfPropertyIsMortgaged() {
        if (!property.getMortgage()) {
            throw new IllegalMonopolyActionException("It's not mortgaged");
        }
    }

    public void checkIfYouHaveEnoughMoneyToSettleMortgage() {
        if (player.getMoney() < (int) (property.receiveMortgageValue() + (property.receiveMortgageValue() * 0.1))) {
            throw new IllegalMonopolyActionException("You don't have enough money to un mortgage this property.");
        }
    }
}
