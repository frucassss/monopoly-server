package be.howest.ti.monopoly.logic.implementation.checkers.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;

public class RentCheck {

    private final Game game;
    private final Player debtor;
    private final Property property;

    public RentCheck(Game game, Player debtor, Property property) {
        this.debtor = debtor;
        this.game = game;
        this.property = property;
    }

    public void checkIfDebtorIsOnPlayersTile(){
        if (!debtor.getCurrentTile().equals(property.getProperty())) {
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. Maybe your are not the owner of said property, or the other player did not land on your property? Also, you can only claim rent untill the next dice roll.");
        }
    }

    public void checkIfPlayerCanCollectRent(){
        Turn turn = game.receiveLastTurn();
        if (!debtor.getName().equals(turn.getPlayer())){
            throw new IllegalMonopolyActionException("You tried to do something which is against the rules of Monopoly. Maybe your are not the owner of said property, or the other player did not land on your property? Also, you can only claim rent untill the next dice roll.");
        }
    }

}
