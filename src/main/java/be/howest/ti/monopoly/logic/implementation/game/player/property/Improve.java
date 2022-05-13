package be.howest.ti.monopoly.logic.implementation.game.player.property;

import be.howest.ti.monopoly.logic.implementation.checkers.game.player.PlayerCheck;
import be.howest.ti.monopoly.logic.implementation.checkers.game.player.property.ImproveCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

public class Improve {

    private final Player player;
    private final ImproveCheck improveCheck;
    private final Property property;
    private final int housePrice;

    public Improve(Player player, String propertyName){
        this.player = player;

        PlayerCheck playerCheck = new PlayerCheck(player);
        playerCheck.checkIfYouOwnProperty(propertyName);

        property = player.findProperty(propertyName);
        improveCheck = new ImproveCheck(player, property);
        housePrice = property.receiveHousePrice();
    }

    public void buyHouse() {
        improveCheck.checkIfYouHaveAllNeededPropertiesForImprovement();
        improveCheck.checkIfYouDontRunAheadOnProperty();
        player.pay(housePrice);
        property.addHouse();
    }

    public void sellHouse() {
        improveCheck.checkIfYouHaveAHouse();
        improveCheck.checkIfYouAreAllowedToSellHouse();
        player.collect((int) (housePrice * 0.5));
        property.removeHouse();
    }

    public void buyHotel() {
        improveCheck.checkIfEveryPropertyHasAValueOf4Houses();
        player.pay(housePrice);
        property.addHotel();
        for (int i = 0; i < 4; i++) {
            property.removeHouse();
        }
    }

    public void sellHotel() {
        improveCheck.checkIfYouHaveAHotel();
        improveCheck.checkWhileSellingAHotelIWontRunAhead();
        player.collect(housePrice);
        property.removeHotel();
        for (int i = 0; i < 4; i++) {
            property.addHouse();
        }
    }
}
