    package be.howest.ti.monopoly.logic.implementation.game.player;

    import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
    import be.howest.ti.monopoly.logic.implementation.game.Game;
    import be.howest.ti.monopoly.logic.implementation.tile.Tile;

    import java.util.*;

    public class Player {
        private final String name;
        private final Game game;
        private Tile currentTile = new Tile("Go", 0, "Go");
        private boolean jailed = false;
        private int money = 1500;
        private boolean bankrupt = false;
        private int getOutOfJailFreeCards = 0;
        private final List<Property> properties = new ArrayList<>();


        // CONSTRUCTOR
        public Player(String name, Game game) {
            this.name = name;
            this.game = game;
        }

        // METHODS
        public void pay(int amount) {
            checkIfIHaveEnoughMoney(amount);
            checkIfAmountIsNotNegative(amount);
            this.money -= amount;
        }

        public Property findPropertyInList(String propertyName) {
            for (Property property1 : properties) {
                if (propertyName.equals(property1.getPropertyName())){
                    return property1;
                }
            }
            throw new IllegalMonopolyActionException("You dont have this property (finder fails)");
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

        public void mortgageProperty(String propertyName) {
            Property property = findPropertyInList(propertyName);
            checkIfYouOwnProperty(propertyName);
            checkIfPropertyIsNotMortgaged(propertyName);
            this.collect(property.getMortgageValue());
            findPropertyInList(property.getPropertyName()).mortgageProperty();
        }

        public void unMortgageProperty(String propertyName) {
            Property property = findPropertyInList(propertyName);
            checkIfYouHaveEnoughMoneyToUnMortgageProperty(propertyName);
            checkIfYouOwnProperty(propertyName);
            checkIfPropertyIsMortgaged(propertyName);
            this.pay((int) (property.getMortgageValue() + (property.getMortgageValue() * 0.1)));
            property.unMortgageProperty();
        }

        public void buyHouse(String propertyName) {
            Property property = findPropertyInList(propertyName);
            checkIfYouOwnProperty(propertyName);
            checkIfYouHaveAllNeededPropertiesForImprovement(propertyName);
            checkIfYouDontWanneRunAheadOnProperty(propertyName);
            pay(property.getHousePrice());
            findPropertyInList(property.getPropertyName()).addHouse();
        }

        public void sellHouse(String propertyName) {
            Property property = findPropertyInList(propertyName);
            //Todo checkers for selling a house
            findPropertyInList(property.getPropertyName()).removeHouse();
            collect((int) (property.getHousePrice() * 0.5));
        }

        public void buyHotel(String propertyName) {
            Property property = findPropertyInList(propertyName);
            checkIfEveryPropertyHasAValueOf4Houses(property.getPropertyName());
            pay(property.getHousePrice());
            property.addHotel();
            for (int i = 0; i < 4; i++) {
                property.removeHouse();
            }
        }

        public void sellHotel(String propertyName) {
            Property property = findPropertyInList(propertyName);
            //Todo checkrs for selling a house
            collect(property.getHousePrice());
            property.removeHotel();
            for (int i = 0; i < 4; i++) {
                property.addHouse();
            }
        }

        // CHECKERS

        private void checkIfEveryPropertyHasAValueOf4Houses(String propertyName){
            String propertyColor = findPropertyInList(propertyName).getColor();
            for (Property property : properties) {
                if ((property.getColor().equals(propertyColor)) &&
                        (property.getHouseCount() + (property.getHotelCount() * 4) != 4)) {
                    throw new IllegalMonopolyActionException("You need to improve other properties first");
                }
            }
        }

        private void checkIfYouDontWanneRunAheadOnProperty(String propertyName) {
            Property property = findPropertyInList(propertyName);
            if ((getHighestHouseCountFromStreet(property.getPropertyName()) != getLowestHouseCountFromStreet(property.getPropertyName())) &&
                    (property.getHouseCount() != getLowestHouseCountFromStreet(property.getPropertyName()))) {
                    throw new IllegalMonopolyActionException("You need to improve your other properties from this street first.");
            }
        }

        private int getHighestHouseCountFromStreet(String propertyName) {
            String propertyColor = findPropertyInList(propertyName).getColor();
            int highest = -1;
            for (Property propertiesFromPlayer : properties) {
                if (propertiesFromPlayer.getColor().equals(propertyColor) && propertiesFromPlayer.getHouseCount() > highest) {
                    highest = propertiesFromPlayer.getHouseCount();
                }
            }
            return highest;
        }

        private int getLowestHouseCountFromStreet(String propertyName) {
            String propertyColor = findPropertyInList(propertyName).getColor();

            int lowest = 5;
            for (Property propertiesFromPlayer : properties) {
                if (propertiesFromPlayer.getColor().equals(propertyColor) && propertiesFromPlayer.getHouseCount() < lowest) {
                    lowest = propertiesFromPlayer.getHouseCount();
                }
            }
            return lowest;
        }

        private void checkIfYouHaveAllNeededPropertiesForImprovement(String propertyName) {
            Property property = findPropertyInList(propertyName);
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

        private void checkIfYouOwnProperty(String propertyName) {
            Property property = findPropertyInList(propertyName);
            if(!properties.contains(property)){
                throw new IllegalMonopolyActionException("You don't own this property.");
            }
        }

        private void checkIfPropertyIsNotMortgaged(String propertyName) {
            Property property = findPropertyInList(propertyName);
            if (property.isMortgage()) {
                throw new IllegalMonopolyActionException("It's already mortgaged");
            }
        }

        private void checkIfPropertyIsMortgaged(String propertyName) {
            Property property = findPropertyInList(propertyName);
            if (!property.isMortgage()) {
                throw new IllegalMonopolyActionException("It's not mortgaged");
            }
        }

        private void checkIfYouCanPayPrisonFine() {
            if (money < 50) {
                throw new IllegalMonopolyActionException("You can't pay the fine, you don't have enough money");
            }
        }

        private void checkIfYouHaveEnoughMoneyToUnMortgageProperty(String propertyName) {
            Property property = findPropertyInList(propertyName);
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
