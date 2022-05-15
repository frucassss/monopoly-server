package be.howest.ti.monopoly.logic.implementation.game;

import be.howest.ti.monopoly.logic.implementation.checkers.game.GameCheck;
import be.howest.ti.monopoly.logic.implementation.checkers.game.TurnCheck;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Turn {

    private final int[] dices = new int[2];
    private final Player player;
    private final Game game;
    private final List<Move> moves = new ArrayList<>();
    private boolean finished = false;

    private static int doubleCount = 0;
    private static final int MAX_ROLL_DOUBLE_COUNT = 3;
    private final Random random = new Random();
    private static final int MAX_DICE_NUMBER = 6;
    private static final int OFF_BY_ONE_ERROR_CORRECTION = 1;

    public Turn(Game game, Player player){
        GameCheck gameCheck = new GameCheck(game);
        gameCheck.checkIfGameStarted();
        gameCheck.checkIfGameIsNotEnded();

        this.player = player;
        this.game = game;

        roll();
        new Move(this, game, player);
        this.game.addTurn(this);
    }

    private void roll(){
        TurnCheck turnCheck = new TurnCheck(game, player);
        turnCheck.checkIfPlayerCanRoll();

        dices[0] = generateDiceNumber();
        dices[1] = generateDiceNumber();
        game.setLastDiceRoll(dices);
        setDoubleRoll();
    }

    private void setDoubleRoll(){
        if(dices[0] == dices[1]){
            rolledDouble();
        }
        else {
            resetDoubleCount();
        }
    }

    private void rolledDouble(){
        doubleCount += 1;
        if (doubleCount >= MAX_ROLL_DOUBLE_COUNT){
            player.setJailed(true);
            resetDoubleCount();
        }
    }

    public void makeFinished(){
        finished = true;
        setNextPlayer();
    }

    private void setNextPlayer(){
        if (doubleCount == 0){
            Player nextPlayer = shiftToNextPlayer(player);

            while (nextPlayer.getBankrupt()){
                nextPlayer = shiftToNextPlayer(nextPlayer);
            }

            game.setCurrentPlayer(nextPlayer);
        }
    }

    private Player shiftToNextPlayer(Player player){
        List<Player> players = game.getPlayers();
        int currentPlayerIdx = players.indexOf(player);
        int nextPlayerIdx = currentPlayerIdx + 1;

        if (nextPlayerIdx >= players.size()) {
            nextPlayerIdx = 0;
        }

        return players.get(nextPlayerIdx);
    }

    private int generateDiceNumber(){
        return random.nextInt(MAX_DICE_NUMBER) + OFF_BY_ONE_ERROR_CORRECTION;
    }

    public void resetDoubleCount(){
        doubleCount = 0;
    }

    public void addMove(Move move){
        this.moves.add(move);
    }

    // GETTERS

    public String getPlayer() {
        return player.getName();
    }

    public int[] getRoll() {
        return dices;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public boolean getFinished() {
        return finished;
    }
}
