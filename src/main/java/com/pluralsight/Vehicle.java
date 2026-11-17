package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Vehicle extends Asset {
    private String makeModel;
    private int year;
    private int odometer;

    public Vehicle(String description, String dateAcquired, BigDecimal originalCost, String makeModel, int year, int odometer) {
        super(description, dateAcquired, originalCost);
        this.makeModel = makeModel;
        this.year = year;
        this.odometer = odometer;
    }

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

    @Override
    public BigDecimal getValue() {
        int age = LocalDate.now().getYear() - year;

        BigDecimal value;

        if (age > 10) {
            value = new BigDecimal("1000");
        } else {
            BigDecimal rate;
            if (age <= 3) {
                rate = new BigDecimal("0.03");
            } else if (age <= 6) {
                rate = new BigDecimal("0.06");
            } else {
                rate = new BigDecimal("0.08");
            }


            BigDecimal totalLoss = rate.multiply(new BigDecimal(age));

            value = getOriginalCost().multiply(BigDecimal.ONE.subtract(totalLoss));
        }

        String name = makeModel.toLowerCase();
        boolean isHondaOrToyota = name.contains("honda") || name.contains("toyota");

        if (odometer > 100000 && !isHondaOrToyota) {
            value = value.multiply(new BigDecimal("0.75"));
        }

        return value;
    }


}
