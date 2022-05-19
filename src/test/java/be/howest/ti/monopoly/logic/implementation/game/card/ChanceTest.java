package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Move;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    MonopolyService monopolyService = new MonopolyService();
    Game game = new Game("Hallo", 1, 2, monopolyService.getChance(), monopolyService.getCommunityChest(), monopolyService.getTiles());
    Player michiel;
    Move move;
    int amountOfMoneyAfterTurn;

    ChanceTest() {
        game.newPlayer("michiel");
        game.newPlayer("thibo");
        michiel = game.findPlayer("michiel");
        michiel.setJailed(false);
        amountOfMoneyAfterTurn = michiel.getMoney();
    }

    void loopUntilChanceDescription(String initialDescription, int expectedPosition){
        String description = "";
        while (!description.equals(initialDescription)){
            Game game2 = new Game("hallo",1,2,monopolyService.getChance(),monopolyService.getCommunityChest(),monopolyService.getTiles());
            game2.newPlayer("michiel2");
            game2.newPlayer("thibo2");
            Player michiel2 = game2.findPlayer("michiel2");
            Turn turn2 = new Turn(game2,michiel2);
            description = turn2.getMoves().get(0).getDescription();
            if (description.equals(initialDescription)){
                assertEquals(expectedPosition,michiel2.receiveCurrentTile().getPosition());
            }
        }
    }

    @Test
    void testPayAChanceFine() {
        Chance payer = new Chance("Speeding fine $15", michiel, game, move);
        assertEquals(amountOfMoneyAfterTurn - 15, michiel.getMoney());
    }

    @Test
    void test3SpacesBack() {
        loopUntilChanceDescription("Go Back 3 Spaces",4);
    }

    @Test
    void advanceToBoardWalk(){
        loopUntilChanceDescription("Advance to Boardwalk",39);
    }

    @Test
    void testPassingGOWhenChanceCard(){
        String description = "";
        while (!description.equals("Advance to Illinois Avenue. If you pass Go, collect $200")){
            Game game2 = new Game("hallo",1,2,monopolyService.getChance(),monopolyService.getCommunityChest(),monopolyService.getTiles());
            game2.newPlayer("michiel2");
            game2.newPlayer("thibo2");
            Player michiel2 = game2.findPlayer("michiel2");
            michiel2.setCurrentTile(monopolyService.getTile("North Carolina"));
            Turn turn = new Turn(game2,michiel2);
            description = turn.getMoves().get(0).getDescription();
            if (description.equals("Advance to Illinois Avenue. If you pass Go, collect $200")){
                assertEquals(24,michiel2.receiveCurrentTile().getPosition());
                assertEquals(1700,michiel2.getMoney());
            }
        }
    }
}
