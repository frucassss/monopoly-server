package be.howest.ti.monopoly.logic.implementation.checkers.game;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

import java.util.List;

public class TurnCheck {

    private final Turn turn;
    private final Game game;
    private final Player player;
    private final Turn lastTurn;

    public TurnCheck(Turn turn){
        this.turn = turn;
        this.game = turn.receiveGame();
        this.player = turn.receivePlayer();
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


    // ### Solution for 1:
    // flag in Turn that tells if previous turn is finished.
        // Basic tile? set flag true by default.
        // Property tile? set flag when bought (or not bought) a property

    // 1. was the landed on tile of prev turn a property?
        // has prev player bought (or not) the property he landed on?


    // 2. check if player rolled double (prev)
        // check if player rolled double (3 times) -> use static?
    // 3. check if player is next in row
    // 4. check if player has negative balance... Yes? Bankrupt.


    // MOVE when... but can roll.
    // check if player is in jail
        // check for how long he/she has been in jail?
        // used jail free card?
        // paid prison fine?

}
