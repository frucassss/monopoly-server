package be.howest.ti.monopoly.logic.implementation.checkers.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;

public class MortgageCheck {

    private final Player player;
    private final Property property;

    private static final double BANK_MORTGAGE_TAKE = 0.1;

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
        if (player.getMoney() < (int) (property.receiveMortgageValue() + (property.receiveMortgageValue() * BANK_MORTGAGE_TAKE))) {
            throw new IllegalMonopolyActionException("You don't have enough money to un mortgage this property.");
        }
    }

    public void checkIfYouHaveNoHouseOnProperties(){
        for (Property playerProperty : player.getProperties()){
            if(doYouOwnAnyPropertiesOnStreet(playerProperty)) {
                throw new IllegalMonopolyActionException("You're trying to do something illegal here");
            }
        }
    }

    private boolean doYouOwnAnyPropertiesOnStreet(Property playerProperty){
        return((property.receiveColor().equals(playerProperty.receiveColor())) && (playerProperty.getHouseCount() != 0 || playerProperty.getHotelCount() != 0));
    }
}
