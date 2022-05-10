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
            //todo in other branch -> checkers for buying house
            this.money -= property.getHousePrice();
            findPropertyInList(property).addHouse();
        }

        public void sellHouse(Property property) {
            //Todo checkers for selling a house
            findPropertyInList(property).removeHouse();
            this.money += (property.getHousePrice() * 0.5);
        }

        public void buyHotel(Property property) {
            //Todo checkers for buying hotel in other branch
            this.money -= property.getHousePrice();
            property.addHotel();
            for (int i = 0; i < 4; i++) {
                property.removeHouse();
            }
        }

        public void sellHotel(Property property) {
            //Todo checkrs for selling a house
            this.money += property.getHousePrice();
            property.removeHotel();
            for (int i = 0; i < 4; i++) {
                property.addHouse();
            }
        }

        // CHECKERS

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

        public void checkIfYouCanUseAGetOutOfJailFreeCard() {
            if (this.getOutOfJailFreeCards == 0) {
                throw new IllegalMonopolyActionException("You don't have an get out of jail card");
            }
        }

        public void checkIfYouOwnProperty(Property property) {
            if(!properties.contains(property)){
                throw new IllegalMonopolyActionException("You don't own this property.");
            }
        }

        public void checkIfPropertyIsNotMortgaged(Property property) {
            if (property.isMortgage()) {
                throw new IllegalMonopolyActionException("It's already mortgaged");
            }
        }

        public void checkIfPropertyIsMortgaged(Property property) {
            if (!property.isMortgage()) {
                throw new IllegalMonopolyActionException("It's not mortgaged");
            }
        }

        public void checkIfYouCanPayPrisonFine() {
            if (money < 50) {
                throw new IllegalMonopolyActionException("You can't pay the fine, you don't have enough money");
            }
        }

        public void checkIfYouHaveEnoughMoneyToUnMortgageProperty(Property property) {
            if (this.money < (int) (property.getMortgageValue() + (property.getMortgageValue() * 0.1))) {
                throw new IllegalMonopolyActionException("You don't have enough money to un mortgage this property.");
            }
        }

        public void

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
