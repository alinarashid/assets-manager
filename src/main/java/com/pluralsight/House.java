package com.pluralsight;

//? BigDecimal = exact money math (double can lose pennies)
import java.math.BigDecimal;

//* House IS-A Asset: it inherits description, dateAcquired, originalCost + their getters
public class House extends Asset {

    //* House's own fields (Asset's fields live in Asset and are private there)
    private String address;
    private int condition;   //* (1 -excellent, 2 -good, 3 -fair, 4 -poor)
    private int squareFoot;
    private int lotSize;

    //* Constructor takes 7 values: 3 for the Asset part + 4 for the House part
    public House(String description, String dateAcquired, BigDecimal originalCost,
                 String address, int condition, int squareFoot, int lotSize) {

        //! Must be first: send the 3 Asset values UP to Asset's constructor
        //* (Asset's fields are private, so only Asset can fill them in)
        super(description, dateAcquired, originalCost);

        //* Fill in House's own 4 fields
        this.address = address;
        this.condition = condition;
        this.squareFoot = squareFoot;
        this.lotSize = lotSize;
    }

    //* Getters and setters for House's own fields only
    //! (Asset's getters like getDescription() are inherited, no need to rewrite them)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getSquareFoot() {
        return squareFoot;
    }

    public void setSquareFoot(int squareFoot) {
        this.squareFoot = squareFoot;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    //? Overriding: replaces Asset's getValue() (which just returned originalCost)
    //? @Override makes Java check that Asset really has a getValue() to replace
    @Override
    public BigDecimal getValue() {
        BigDecimal pricePerSquareFoot;

        if (condition == 1) {
            pricePerSquareFoot = new BigDecimal("180");
        } else if (condition == 2) {
            pricePerSquareFoot = new BigDecimal("130");
        } else if (condition == 3) {
            pricePerSquareFoot = new BigDecimal("90");
        } else {
            pricePerSquareFoot = new BigDecimal("80");
        }

        //* House = price per sq ft × square feet
        //! (BigDecimal can't use * so we use .multiply())
        BigDecimal houseValue = pricePerSquareFoot.multiply(new BigDecimal(squareFoot));

        //* Land = 25 cents x lot size
        BigDecimal lotValue = new BigDecimal("0.25").multiply(new BigDecimal(lotSize));

        //* Total = house + land (BigDecimal can't use + so we use .add())
        return houseValue.add(lotValue);
    }
}