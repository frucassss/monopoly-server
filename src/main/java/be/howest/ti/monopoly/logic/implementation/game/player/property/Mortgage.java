package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.implementation.checkers.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.checkers.player.property.MortgageCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

public class Mortgage {

    private final Player player;
    private final MortgageCheck mortgageCheck;
    private final Property property;
    private final int mortgageValue;

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
        player.collect(mortgageValue);
        property.tookMortgage();
    }

    public void settleMortgage() {
        mortgageCheck.checkIfYouHaveEnoughMoneyToSettleMortgage();
        mortgageCheck.checkIfPropertyIsMortgaged();
        player.pay((int) (mortgageValue + (mortgageValue* 0.1)));
        property.settledMortgage();
    }
}
