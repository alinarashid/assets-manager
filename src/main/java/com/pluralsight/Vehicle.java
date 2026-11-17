package com.pluralsight;

//? BigDecimal = exact money math (double can lose pennies)
import java.math.BigDecimal;
//? LocalDate = today's date, used to work out how old the car is
import java.time.LocalDate;

//* Vehicle IS-A Asset (child class)
//* It inherits description, dateAcquired, originalCost + their getters/setters from Asset
//* It only adds what makes a vehicle different: make/model, year, miles
public class Vehicle extends Asset {

    //* Vehicle's own fields (Asset's 3 fields live inside Asset, not here)
    private String makeModel;   //* e.g. "Honda Civic"
    private int year;           //* Model year, e.g. 2019
    private int odometer;       //* Miles driven

    //* Constructor: runs when main writes new Vehicle(...)
    //* Takes 6 values: 3 for the Asset (parent) part + 3 for the Vehicle (child) part
    public Vehicle(String description, String dateAcquired, BigDecimal originalCost,
                   String makeModel, int year, int odometer) {

        //! Step 1: build the Asset (parent) part FIRST with super(...)
        //! Must be the FIRST line, and values must be in the SAME ORDER as Asset's constructor
        super(description, dateAcquired, originalCost);

        //* Step 2: fill in Vehicle's own 3 fields
        this.makeModel = makeModel;
        this.year = year;
        this.odometer = odometer;
    }

    //* Getters and setters for Vehicle's own fields only
    //! Asset's getters (getDescription(), getOriginalCost()...) are inherited, no need to rewrite them
    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    //? OVERRIDING: replaces Asset's getValue() with car math
    //? In main, asset.getValue() automatically runs THIS version for vehicles (polymorphism)
    @Override
    public BigDecimal getValue() {
        //* Step 1: how old is the car? (this year - model year)
        int age = LocalDate.now().getYear() - year;

        BigDecimal value;

        //* Step 2: value based on age
        if (age > 10) {
            //* Over 10 years old = flat $1,000
            value = new BigDecimal("1000");
        } else {
            //* Pick how much value it loses PER YEAR
            BigDecimal rate;
            if (age <= 3) {
                rate = new BigDecimal("0.03");      //* 0-3 years: 3% per year
            } else if (age <= 6) {
                rate = new BigDecimal("0.06");      //* 4-6 years: 6% per year
            } else {
                rate = new BigDecimal("0.08");      //* 7-10 years: 8% per year
            }

            //* Total loss = rate × age (e.g. 0.06 × 5 years = 0.30, which is 30% off)
            BigDecimal totalLoss = rate.multiply(new BigDecimal(age));

            //* Keep the rest: cost × (1 - totalLoss), e.g. 30% off → keep 70%
            //! originalCost is private in Asset, so we use the inherited getter
            value = getOriginalCost().multiply(BigDecimal.ONE.subtract(totalLoss));
        }

        //* Step 3: high-mileage penalty, unless it's a Honda or Toyota
        //? toLowerCase() so "Honda", "HONDA" and "honda" all match
        String name = makeModel.toLowerCase();
        boolean isHondaOrToyota = name.contains("honda") || name.contains("toyota");

        //! ! means NOT, so: over 100,000 miles AND not a Honda/Toyota → keep 75%
        if (odometer > 100000 && !isHondaOrToyota) {
            value = value.multiply(new BigDecimal("0.75"));
        }

        //* Example: 2019 Ford F-150, $42,000, 120,000 miles (age 7 in 2026)
        //* 8% × 7 = 56% off → 42,000 × 0.44 = 18,480 → × 0.75 = $13,860
        return value;
    }
}