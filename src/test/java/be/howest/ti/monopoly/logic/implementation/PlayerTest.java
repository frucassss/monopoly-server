package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.tile.PropertyTile;
import be.howest.ti.monopoly.logic.implementation.tile.StreetTile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void testMortgage() {
        Monopoly monopoly = new Monopoly();
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        PropertyTile MediterraneanTile = new StreetTile("Mediterranean", 1, "street", 60, 30, 2, "PURPLE", 2, 10, 30, 90, 160, 250, 50, "PURPLE");
        Property Mediterranean = new Property(MediterraneanTile);
        Michiel.addProperty(Mediterranean);
        Michiel.mortgageProperty(Mediterranean);
        assertEquals(1530, Michiel.getMoney());
    }

    @Test
    public void testUnMortgage() {
        Monopoly monopoly = new Monopoly();
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        PropertyTile MediterraneanTile = (PropertyTile) Monopoly.getTiles().get(5);
        Property Mediterranean = new Property(MediterraneanTile);
        Michiel.addProperty(Mediterranean);
        Michiel.mortgageProperty(Mediterranean);
        Michiel.unMortgageProperty(Mediterranean);
        assertEquals(1490, Michiel.getMoney());
    }

    @Test
    public void testMortgageNoMoney() {
        Monopoly monopoly = new Monopoly();
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        PropertyTile MediterraneanTile = (PropertyTile) Monopoly.getTiles().get(5);
        Property Mediterranean = new Property(MediterraneanTile);
        Michiel.addProperty(Mediterranean);
        Michiel.mortgageProperty(Mediterranean);
        Michiel.pay(1500);
        assertThrows(IllegalMonopolyActionException.class, () -> Michiel.unMortgageProperty(Mediterranean));
    }

    @Test
    public void testMortgageNotHavingTheProperty() {
        Monopoly monopoly = new Monopoly();
        Player Michiel = new Player("Michiel");
        Player Lucas = new Player("Lucas");
        List<Player> players = List.of(Michiel, Lucas);
        Property Mediterranean = new Property((PropertyTile) Monopoly.getTiles().get(5));
        assertThrows(IllegalMonopolyActionException.class, () -> Michiel.mortgageProperty(Mediterranean));
    }


}