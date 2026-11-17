package com.pluralsight;

//? BigDecimal = exact money math (double can lose pennies)
import java.math.BigDecimal;

//* Asset = the PARENT class: anything you own that's worth money
//* House and Vehicle extend this, so they inherit everything here
public class Asset {

    //* Shared fields every asset has
    //! private = only Asset can touch these directly; children use the getters/setters
    private String description;
    private String dateAcquired;
    private BigDecimal originalCost;

    //* Constructor: fills in the 3 shared fields
    //! Children MUST call this with super(description, dateAcquired, originalCost)
    public Asset(String description, String dateAcquired, BigDecimal originalCost) {
        this.description = description;
        this.dateAcquired = dateAcquired;
        this.originalCost = originalCost;
    }

    //* Getters: inherited by House and Vehicle (they don't need to rewrite these)
    public String getDescription() {
        return description;
    }

    public String getDateAcquired() {
        return dateAcquired;
    }

    public BigDecimal getOriginalCost() {
        return originalCost;
    }

    //* Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateAcquired(String dateAcquired) {
        this.dateAcquired = dateAcquired;
    }

    public void setOriginalCost(BigDecimal originalCost) {
        this.originalCost = originalCost;
    }

    //? Default value = what you paid
    //? House and Vehicle OVERRIDE this with their own math (polymorphism)
    public BigDecimal getValue() {
        return originalCost;
    }

}