package com.pluralsight;

//? BigDecimal = exact money math (double can lose pennies, like 0.1 + 0.2 = 0.30000000000000004)
import java.math.BigDecimal;

//* House IS-A Asset (child class)
//* It inherits description, dateAcquired, originalCost + their getters/setters from Asset
//* It only adds what makes a house different: address, condition, size
public class House extends Asset {

    //* House's own fields (Asset's 3 fields live inside Asset, not here)
    private String address;
    private int condition;      //* 1 = excellent, 2 = good, 3 = fair, 4 = poor
    private int squareFoot;     //* Size of the house
    private int lotSize;        //* Size of the land

    //* Constructor: runs when main writes new House(...)
    //* Takes 7 values: 3 for the Asset (parent) part + 4 for the House (child) part
    public House(String description, String dateAcquired, BigDecimal originalCost,
                 String address, int condition, int squareFoot, int lotSize) {

        //! Step 1: build the Asset (parent) part FIRST, like a foundation before the upper floor
        //! super(...) jumps UP and runs Asset's constructor with these 3 values
        //! Must be the FIRST line, and values must be in the SAME ORDER as Asset's constructor
        //? Why not this.description = description? Asset's fields are private, so only Asset can fill them
        super(description, dateAcquired, originalCost);

        //* Step 2: jump back DOWN and fill in House's own 4 fields
        this.address = address;
        this.condition = condition;
        this.squareFoot = squareFoot;
        this.lotSize = lotSize;
    }

    //* Getters and setters for House's own fields only
    //! Asset's getters (getDescription(), getOriginalCost()...) are inherited, no need to rewrite them
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

    //? OVERRIDING: replaces Asset's getValue() (which just returned originalCost)
    //? @Override = Java double-checks that Asset really has a getValue() to replace (catches typos)
    //? In main, asset.getValue() automatically runs THIS version for houses (polymorphism)
    @Override
    public BigDecimal getValue() {
        BigDecimal pricePerSquareFoot;

        //* Better condition = higher price per square foot
        if (condition == 1) {
            pricePerSquareFoot = new BigDecimal("180");
        } else if (condition == 2) {
            pricePerSquareFoot = new BigDecimal("130");
        } else if (condition == 3) {
            pricePerSquareFoot = new BigDecimal("90");
        } else {
            pricePerSquareFoot = new BigDecimal("80");
        }

        //* House part = price per sq ft × square feet
        //! BigDecimal can't use * so we use .multiply()
        BigDecimal houseValue = pricePerSquareFoot.multiply(new BigDecimal(squareFoot));

        //* Land part = 25 cents × lot size
        BigDecimal lotValue = new BigDecimal("0.25").multiply(new BigDecimal(lotSize));

        //* Total = house + land
        //! BigDecimal can't use + so we use .add()
        //* Example: good house (130) × 1,800 sq ft = 234,000 + 0.25 × 7,000 lot = 1,750 → $235,750
        return houseValue.add(lotValue);
    }


}