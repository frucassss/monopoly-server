package be.howest.ti.monopoly.logic.implementation.checkers.player;

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
}
