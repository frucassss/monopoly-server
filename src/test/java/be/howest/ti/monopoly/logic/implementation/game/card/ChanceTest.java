package be.howest.ti.monopoly.logic.implementation.game.card;

import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.logic.implementation.game.Game;
import be.howest.ti.monopoly.logic.implementation.game.Turn;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChanceTest {
    MonopolyService monopolyService = new MonopolyService();
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
    void testCollectingMoney(){
        String description = "";
        while (!description.equals("Bank pays you dividend of $50")){
            Game game2 = new Game("hallo",1,2,monopolyService.getChance(),monopolyService.getCommunityChest(),monopolyService.getTiles());
            game2.newPlayer("michiel2");
            game2.newPlayer("thibo2");
            Player michiel2 = game2.findPlayer("michiel2");
            Turn turn2 = new Turn(game2,michiel2);
            description = turn2.getMoves().get(0).getDescription();
            if (description.equals("Bank pays you dividend of $50")){
                assertEquals(1550,michiel2.getMoney());
            }
        }
    }

    @Test
    void testPayingMoney(){
        String description = "";
        while (!description.equals("Speeding fine $15")){
            Game game2 = new Game("hallo",1,2,monopolyService.getChance(),monopolyService.getCommunityChest(),monopolyService.getTiles());
            game2.newPlayer("michiel2");
            game2.newPlayer("thibo2");
            Player michiel2 = game2.findPlayer("michiel2");
            Turn turn2 = new Turn(game2,michiel2);
            description = turn2.getMoves().get(0).getDescription();
            if (description.equals("Speeding fine $15")){
                assertEquals(1485,michiel2.getMoney());
            }
        }
    }

    @Test
    void test3SpacesBack() {
        loopUntilChanceDescription("Go Back 3 Spaces",4);
    }

    @Test
    void testAdvanceToBoardWalk(){
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

    @Test
    void testPayEachPlayer(){
        String description = "";
        while (!description.equals("You have been elected Chairman of the Board. Pay each player $50")){
            Game game2 = new Game("hallo",1,2,monopolyService.getChance(),monopolyService.getCommunityChest(),monopolyService.getTiles());
            game2.newPlayer("michiel2");
            game2.newPlayer("thibo2");
            Player michiel2 = game2.findPlayer("michiel2");
            Player thibo2 = game2.findPlayer("thibo2");
            michiel2.setCurrentTile(monopolyService.getTile("North Carolina"));
            Turn turn = new Turn(game2,michiel2);
            description = turn.getMoves().get(0).getDescription();
            if (description.equals("You have been elected Chairman of the Board. Pay each player $50")){
                assertEquals(1450,michiel2.getMoney());
                assertEquals(1550,thibo2.getMoney());
            }
        }
    }


}
