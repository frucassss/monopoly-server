package be.howest.ti.monopoly.logic.implementation.checkers.game;

import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;

public class TurnCheck {

    private final Turn turn;
    /*private final Game game;
    private final Player player;*/

    public TurnCheck(Turn turn){
        this.turn = turn;
        /*this.game = turn.receiveGame();
        this.player = turn.receivePlayer();*/
    }

    public void checkIfPlayerShouldBeBankrupt(String playerName){
        Player p = game.findPlayer(playerName);
        if (checkNegativeBalance(p)){
            p.makeBankrupt();
        }

    }

    public boolean checkNegativeBalance(Player player){
        return player.getMoney() < 0;
    }

}