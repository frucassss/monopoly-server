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
    private final List<Turn> previousTurns;
    private final Turn lastTurn;
    private final Player lastPlayer;

    public TurnCheck(Turn turn){
        this.turn = turn;
        this.game = turn.receiveGame();
        this.player = turn.receivePlayer();
        this.previousTurns = game.getTurns();
        this.lastTurn = previousTurns.get(previousTurns.size() - 1);
        this.lastPlayer = game.receiveCurrentPlayer();
    }

    public void checkIfPlayerCanRoll(){
        checkIfLastTurnIsOver();
    }

    private void checkIfLastTurnIsOver(){
        if (!lastTurn.getFinished()){
            throw new IllegalMonopolyActionException("Previous turn isn't finished yet.");
        }
    }

    // flag in Turn that tells if previous turn is finished.
        // Basic tile? set flag true by default.
        // Property tile? set flag when bought (or not bought) a property

    // 1. was the landed on tile of prev turn a property?
        // has prev player bought (or not) the property he landed on?
    // 2. check if player rolled double (prev)
        // check if player rolled double (3 times) -> use static?
    // 3. check if player is next in row


    // check if player is in jail
        // check for how long he/she has been in jail?
        // used jail free card?
        // paid prison fine?
    // check if player has negative balance... Yes? Bankrupt.

    public void checkIfPlayerShouldBeBankrupt(){
        if (hasNegativeBalance()){
            player.makeBankrupt();
        }
    }

    public boolean hasNegativeBalance(){
        return player.getMoney() < 0;
    }

}
