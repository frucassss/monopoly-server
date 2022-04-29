package be.howest.ti.monopoly.logic.implementation;

public class StreetTile extends Tile{
    private int cost;
    private int rent;
    private int mortgage;
    private int rentWithOneHouse;
    private int rentWithTwoHouses;
    private int rentWithThreeHouses;
    private int rentWithFourHouses;
    private int rentWithHotel;
    private int housePrice;
    private String streetColor;
    private int groupSize;
    private String color;
    StreetTile(String name, int position, int cost, int mortgage, int rent, int rentWithOneHouse, int rentWithTwoHouses, int rentWithThreeHouses, int rentWithFourHouses, int rentWithHotel, int housePrice, String streetColor, int groupSize, String color, String type, String nameAsPathParameter) {
        super(name, position, nameAsPathParameter, type);
        this.cost = cost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.rentWithOneHouse = rentWithOneHouse;
        this.rentWithTwoHouses = rentWithTwoHouses;
        this.rentWithThreeHouses = rentWithThreeHouses;
        this.rentWithFourHouses = rentWithFourHouses;
        this.rentWithHotel = rentWithHotel;
        this.housePrice = housePrice;
        this.streetColor = streetColor;
        this.groupSize = groupSize;
        this.color = color;
    }

    public int getRent() {
        return rent;
    }

    public int getMortgage() {
        return mortgage;
    }

    public int getRentWithOneHouse() {
        return rentWithOneHouse;
    }

    public int getRentWithTwoHouses() {
        return rentWithTwoHouses;
    }

    public int getRentWithThreeHouses() {
        return rentWithThreeHouses;
    }

    public int getRentWithFourHouses() {
        return rentWithFourHouses;
    }

    public int getRentWithHotel() {
        return rentWithHotel;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public String getStreetColor() {
        return streetColor;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getColor() {
        return color;
    }

    public int getCost() {
        return cost;
    }

}
