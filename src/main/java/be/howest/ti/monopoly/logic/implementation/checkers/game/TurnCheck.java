package be.howest.ti.monopoly.logic.implementation.checkers.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

public class TurnCheck {

    private final Game game;
    private final Player player;
    private final Turn lastTurn;

    public TurnCheck(Game game, Player player){
        this.game = game;
        this.player = player;
        this.lastTurn = game.receiveLastTurn();
    }

    public void checkIfPlayerCanRoll(){
        checkIfLastTurnIsOver();
        checkIfPlayerIsCurrentPlayer();
        checkIfPlayerShouldBeBankrupt();
    }

    private void checkIfLastTurnIsOver(){
        if (lastTurn != null && !lastTurn.getFinished()){
            throw new IllegalMonopolyActionException("Previous turn isn't finished yet.");
        }
    }

    private void checkIfPlayerIsCurrentPlayer(){
        if (!game.receiveCurrentPlayer().equals(player)){
            throw new IllegalMonopolyActionException("Player is not allowed to roll.");
        }
    }

    private void checkIfPlayerShouldBeBankrupt(){
        if (hasNegativeBalance()){
            player.makeBankrupt();
            throw new IllegalMonopolyActionException("Player is bankrupt. Player is not allowed to roll.");
        }
    }

    private boolean hasNegativeBalance(){
        return player.getMoney() < 0;
    }
}
