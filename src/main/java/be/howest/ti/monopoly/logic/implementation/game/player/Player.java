    package be.howest.ti.monopoly.logic.implementation.game.player;

    import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
    import be.howest.ti.monopoly.logic.implementation.tile.Tile;

    import java.util.*;

    public class Player {
        private final String name;
        private Tile currentTile = new Tile("Go", 0, "Go");
        private boolean jailed = false;
        private int money = 1500;
        private boolean bankrupt = false;
        private int getOutOfJailFreeCards = 0;
        private final List<Property> properties = new ArrayList<>();


        // CONSTRUCTOR
        public Player(String name) {
            this.name = name;
        }

        // METHODS
        public void pay(int amount) {
            checkIfIHaveEnoughMoney(amount);
            checkIfAmountIsNotNegative(amount);
            this.money -= amount;
        }

        public Property findPropertyInList(Property property) {
            for (Property propertyInProperties : properties) {
                if (property.equals(propertyInProperties)) {
                    return propertyInProperties;
                }
            }
            throw new IllegalMonopolyActionException("You dont have this property");
        }

        public void collect(int amount) {
            checkIfAmountIsNotNegative(amount);
            this.money += amount;
        }

        public void addGetOutOfJailFreeCard() {
            checkIfYouCanAddGetOutOfJailFreeCard();
            this.getOutOfJailFreeCards += 1;
        }

        public void useGetOutOfJailFreeCard() {
            checkIfYouCanUseAGetOutOfJailFreeCard();
            this.getOutOfJailFreeCards -= 1;
        }

        public void payPrisonFine() {
            checkIfYouCanPayPrisonFine();
            money -= 50;
            setJailed(false);
        }

        public void addProperty(Property property) {
            properties.add(property);
        }

        public void removeProperty(Property property) {
            properties.remove(property);
        }

        public void mortgageProperty(Property property) {
            checkIfYouOwnProperty(property);
            checkIfPropertyIsNotMortgaged(property);
            this.collect(property.getMortgageValue());
            findPropertyInList(property).mortgageProperty();
        }

        public void unMortgageProperty(Property property) {
            checkIfYouHaveEnoughMoneyToUnMortgageProperty(property);
            checkIfYouOwnProperty(property);
            checkIfPropertyIsMortgaged(property);
            this.pay((int) (property.getMortgageValue() + (property.getMortgageValue() * 0.1)));
            property.unMortgageProperty();
        }

        public void buyHouse(Property property) {
            checkIfYouOwnProperty(property);
            checkIfYouHaveAllNeededPropertiesForImprovement(property);
            checkIfYouDontWanneRunAheadOnProperty(property);
            pay(property.getHousePrice());
            findPropertyInList(property).addHouse();
        }

        public void sellHouse(Property property) {
            checkIfYouHaveAHouse(property);
            checkIfYouAreAllowedToSellHouse(property);
            collect((int) (property.getHousePrice() * 0.5));
            findPropertyInList(property).removeHouse();
        }

        public void buyHotel(Property property) {
            checkIfEveryPropertyHasAValueOf4Houses(property.getColor());
            pay(property.getHousePrice());
            property.addHotel();
            for (int i = 0; i < 4; i++) {
                property.removeHouse();
            }
        }

        public void sellHotel(Property property) {
            checkIfYouHaveAHotel(property);
            checkWhileSellingAHotelIWontRunAhead(property.getColor());
            collect(property.getHousePrice());
            property.removeHotel();
            for (int i = 0; i < 4; i++) {
                property.addHouse();
            }
        }

        // CHECKERS

        private void checkIfYouAreAllowedToSellHouse(Property property){
            if ((getHighestHouseCountFromStreetExceptGivenProperty(property) > property.getHouseCount()) ||
                    (difference(getHighestHouseCountFromStreetExceptGivenProperty(property),property.getHouseCount()) > 1)){
                throw new IllegalMonopolyActionException("You need to sell other houses first");
            }
        }

        private int difference(int x, int y){
            int diff = x - y;
            return Math.abs(diff);
        }

        private void checkIfYouHaveAHouse(Property property) {
            if (findPropertyInList(property).getHouseCount() == 0){
                throw new IllegalMonopolyActionException("You dont have a house on this property");
            }
        }

        private void checkWhileSellingAHotelIWontRunAhead(String streetColor){
            for (Property property : properties){
                if (property.getColor().equals(streetColor) &&
                        (property.getHouseCount() != 0 && property.getHotelCount() == 1) &&
                        (property.getHouseCount() != 4 && property.getHotelCount() == 0)){
                    throw new IllegalMonopolyActionException("You need to sell all houses before you can sell a hotel");
                }
            }
        }

        private void checkIfYouHaveAHotel(Property property) {
            if (findPropertyInList(property).getHotelCount() != 1){
                throw new IllegalMonopolyActionException("You don't have a hotel to sell");
            }
        }

        private void checkIfEveryPropertyHasAValueOf4Houses(String streetColor){
            for (Property property : properties) {
                if ((property.getColor().equals(streetColor)) &&
                        (property.getHouseCount() + (property.getHotelCount() * 4) != 4)) {
                    throw new IllegalMonopolyActionException("You need to improve other properties first");
                }

            }
        }

        private void checkIfYouDontWanneRunAheadOnProperty(Property property) {
            if ((getHighestHouseCountFromStreet(property.getColor()) != getLowestHouseCountFromStreet(property.getColor())) &&
                    (property.getHouseCount() != getLowestHouseCountFromStreet(property.getColor()))) {
                    throw new IllegalMonopolyActionException("You need to improve your other properties from this street first.");
            }
        }

        private int getHighestHouseCountFromStreetExceptGivenProperty(Property property){
            int highest = -1;
            for (Property propertiesFromPlayer : properties) {
                if (propertiesFromPlayer.getColor().equals(property.getColor()) && propertiesFromPlayer.getHouseCount() > highest && !propertiesFromPlayer.equals(property)) {
                    highest = propertiesFromPlayer.getHouseCount();
                }
            }
            return highest;
        }

        private int getHighestHouseCountFromStreet(String streetColor) {
            int highest = -1;
            for (Property propertiesFromPlayer : properties) {
                if (propertiesFromPlayer.getColor().equals(streetColor) && propertiesFromPlayer.getHouseCount() > highest) {
                    highest = propertiesFromPlayer.getHouseCount();
                }
            }
            return highest;
        }

        private int getLowestHouseCountFromStreet(String streetColor) {
            int lowest = 5;
            for (Property propertiesFromPlayer : properties) {
                if (propertiesFromPlayer.getColor().equals(streetColor) && propertiesFromPlayer.getHouseCount() < lowest) {
                    lowest = propertiesFromPlayer.getHouseCount();
                }
            }
            return lowest;
        }

        private void checkIfYouHaveAllNeededPropertiesForImprovement(Property property) {
            int counter = 0;
            for (Property propertiesFormPlayer : properties) {
                if (propertiesFormPlayer.getColor().equals(property.getColor())) {
                    counter++;
                }
            }
            if ((counter != property.getGroupSize())) {
                throw new IllegalMonopolyActionException("you don't have all properties of this street, so you can't buy a house");
            }
        }

        private void checkIfAmountIsNotNegative(int amount){
            if (amount < 0){
                throw new IllegalMonopolyActionException("You're trying to pay a negative number?");
            }
        }

        private void checkIfIHaveEnoughMoney(int amount){
            if (amount > money){
                throw new IllegalMonopolyActionException("You don't have enough money");
            }
        }

        private void checkIfYouCanAddGetOutOfJailFreeCard() {
            if (this.getOutOfJailFreeCards > 2) {
                throw new IllegalMonopolyActionException("You already have 2 get out of jail cars");
            }
        }

        private void checkIfYouCanUseAGetOutOfJailFreeCard() {
            if (this.getOutOfJailFreeCards == 0) {
                throw new IllegalMonopolyActionException("You don't have an get out of jail card");
            }
        }

        private void checkIfYouOwnProperty(Property property) {
            if(!properties.contains(property)){
                throw new IllegalMonopolyActionException("You don't own this property.");
            }
        }

        private void checkIfPropertyIsNotMortgaged(Property property) {
            if (property.isMortgage()) {
                throw new IllegalMonopolyActionException("It's already mortgaged");
            }
        }

        private void checkIfPropertyIsMortgaged(Property property) {
            if (!property.isMortgage()) {
                throw new IllegalMonopolyActionException("It's not mortgaged");
            }
        }

        private void checkIfYouCanPayPrisonFine() {
            if (money < 50) {
                throw new IllegalMonopolyActionException("You can't pay the fine, you don't have enough money");
            }
        }

        private void checkIfYouHaveEnoughMoneyToUnMortgageProperty(Property property) {
            if (this.money < (int) (property.getMortgageValue() + (property.getMortgageValue() * 0.1))) {
                throw new IllegalMonopolyActionException("You don't have enough money to un mortgage this property.");
            }
        }

        // GETTERS
        public String getName() {
            return name;
        }

        public String getCurrentTile() {
            return currentTile.getName();
        }

        public boolean getJailed() {
            return jailed;
        }

        public int getMoney() {
            return money;
        }

        public boolean getBankrupt() {
            return bankrupt;
        }

        public int getGetOutOfJailFreeCards() {
            return getOutOfJailFreeCards;
        }

        public List<Property> getProperties() {
            return properties;
        }

        // SETTERS
        public void setCurrentTile(Tile currentTile) {
            this.currentTile = currentTile;
        }

        public void setJailed(boolean jailed) {
            this.jailed = jailed;
        }

        public void setBankrupt(boolean bankrupt) {
            this.bankrupt = bankrupt;
        }

        // BUILT-IN
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Player)) return false;
            Player player = (Player) o;
            return name.equals(player.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
