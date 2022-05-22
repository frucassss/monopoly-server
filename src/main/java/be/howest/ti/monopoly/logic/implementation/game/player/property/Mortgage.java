package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.implementation.checkers.game.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.checkers.game.player.property.MortgageCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

public class Mortgage {

    private final Player player;
    private final MortgageCheck mortgageCheck;
    private final Property property;
    private final int mortgageValue;

    private static final double BANK_MORTGAGE_TAKE = 0.1;

    public Mortgage(Player player, String propertyName){
        this.player = player;

        PlayerCheck playerCheck = new PlayerCheck(player);
        playerCheck.checkIfYouOwnProperty(propertyName);

        property = player.findProperty(propertyName);
        mortgageCheck = new MortgageCheck(player, property);
        mortgageValue = property.receiveMortgageValue();

    }

    public void takeMortgage() {
        mortgageCheck.checkIfPropertyIsNotMortgaged();
        mortgageCheck.checkIfYouHaveNoHouseOnProperties();
        player.collect(mortgageValue);
        property.tookMortgage();
    }

    public void settleMortgage() {
        mortgageCheck.checkIfYouHaveEnoughMoneyToSettleMortgage();
        mortgageCheck.checkIfPropertyIsMortgaged();
        player.pay((int) (mortgageValue + (mortgageValue * BANK_MORTGAGE_TAKE)));
        property.settledMortgage();
    }
}
