package be.howest.ti.monopoly.logic.implementation.tile;

public class StreetTile extends PropertyTile{
    protected final int rentWithOneHouse;
    protected final int rentWithTwoHouses;
    protected final int rentWithThreeHouses;
    protected final int rentWithFourHouses;
    protected final int rentWithHotel;
    protected final int housePrice;
    protected final String streetColor;

    public StreetTile(String name, int position, String type, int cost, int mortgage, int groupSize, String color, int rent, int rentWithOneHouse, int rentWithTwoHouses, int rentWithThreeHouses, int rentWithFourHouses, int rentWithHotel, int housePrice, String streetColor) {
        super(name, position, type, cost, mortgage, groupSize, color, rent);
        this.rentWithOneHouse = rentWithOneHouse;
        this.rentWithTwoHouses = rentWithTwoHouses;
        this.rentWithThreeHouses = rentWithThreeHouses;
        this.rentWithFourHouses = rentWithFourHouses;
        this.rentWithHotel = rentWithHotel;
        this.housePrice = housePrice;
        this.streetColor = streetColor;
    }

    public int getRent(){
        return super.rent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return super.position == tile.position;
    }

    @Override
    public int hashCode() {
        return super.position;
    }
}
