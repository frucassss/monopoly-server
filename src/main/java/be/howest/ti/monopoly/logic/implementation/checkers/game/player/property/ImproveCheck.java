package be.howest.ti.monopoly.logic.implementation.checkers.game.player.property;

import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.implementation.game.player.Player;
import be.howest.ti.monopoly.logic.implementation.game.player.property.Property;

public class ImproveCheck {

    private final Player player;
    private final Property property;

    public ImproveCheck(Player player, Property property){
        this.player = player;
        this.property = property;
    }

    public void checkIfYouAreAllowedToSellHouse(){
        if ((getHighestHouseCountFromStreetExceptGivenProperty() > property.getHouseCount()) ||
                (difference(getHighestHouseCountFromStreetExceptGivenProperty(),property.getHouseCount()) > 1)){
            throw new IllegalMonopolyActionException("You need to sell other houses first");
        }
    }


    public void checkIfYouHaveAHouse() {
        if (property.getHouseCount() == 0){
            throw new IllegalMonopolyActionException("You dont have a house on this property");
        }
    }

    public void checkWhileSellingAHotelIWontRunAhead(){
        for (Property other : player.getProperties()){
            if (other.receiveColor().equals(property.receiveColor()) &&
                    (other.getHouseCount() != 0 && other.getHotelCount() == 1) &&
                    (other.getHouseCount() != 4 && other.getHotelCount() == 0)){
                throw new IllegalMonopolyActionException("You need to sell all houses before you can sell a hotel");
            }
        }
    }

    public void checkIfYouHaveAHotel() {
        if (property.getHotelCount() != 1){
            throw new IllegalMonopolyActionException("You don't have a hotel to sell");
        }
    }


    public void checkIfEveryPropertyHasAValueOf4Houses(){
        for (Property other : player.getProperties()) {
            if ((other.receiveColor().equals(property.receiveColor())) &&
                    (other.getHouseCount() + (other.getHotelCount() * 4) != 4)) {
                throw new IllegalMonopolyActionException("You need to improve other properties first");
            }
        }
    }

    public void checkIfYouDontRunAheadOnProperty() {
        if ((getHighestHouseCountFromStreet() != getLowestHouseCountFromStreet()) &&
                (property.getHouseCount() != getLowestHouseCountFromStreet())) {
            throw new IllegalMonopolyActionException("You need to improve your other properties from this street first.");
        }
    }

    public void checkIfYouHaveAllNeededPropertiesForImprovement() {
        int counter = 0;
        for (Property propertiesFormPlayer : player.getProperties()) {
            if (propertiesFormPlayer.receiveColor().equals(property.receiveColor())) {
                counter++;
            }
        }
        if ((counter != property.receiveGroupSize())) {
            throw new IllegalMonopolyActionException("you don't have all properties of this street, so you can't buy a house");
        }
    }


    // helpers

    private int getHighestHouseCountFromStreetExceptGivenProperty(){
        int highest = -1;
        for (Property propertiesFromPlayer : player.getProperties()) {
            if (propertiesFromPlayer.receiveColor().equals(property.receiveColor()) && propertiesFromPlayer.getHouseCount() > highest && !propertiesFromPlayer.equals(property)) {
                highest = propertiesFromPlayer.getHouseCount();
            }
        }
        return highest;
    }

    private int getHighestHouseCountFromStreet() {
        int highest = -1;
        for (Property propertiesFromPlayer : player.getProperties()) {
            if (propertiesFromPlayer.receiveColor().equals(property.receiveColor()) && propertiesFromPlayer.getHouseCount() > highest) {
                highest = propertiesFromPlayer.getHouseCount();
            }
        }
        return highest;
    }

    private int getLowestHouseCountFromStreet() {
        int lowest = 5;
        for (Property propertiesFromPlayer : player.getProperties()) {
            if (propertiesFromPlayer.receiveColor().equals(property.receiveColor()) && propertiesFromPlayer.getHouseCount() < lowest) {
                lowest = propertiesFromPlayer.getHouseCount();
            }
        }
        return lowest;
    }

    private int difference(int x, int y){
        int diff = x - y;
        return Math.abs(diff);
    }

}
