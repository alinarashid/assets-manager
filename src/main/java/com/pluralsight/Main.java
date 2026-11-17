package com.pluralsight;

//? BigDecimal = exact money math
import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //* Step 1: make ONE empty list that can hold ANY kind of Asset
        //? Works because a House IS-A Asset and a Vehicle IS-A Asset
        ArrayList<Asset> myAssets = new ArrayList<>();

        //* Step 2: create each asset with new, then .add() it into the list
        //? new House(...) builds the object, .add(...) drops it into the myAssets list
        //? Without .add(), the object would be created and then lost, and the loop would never see it

        //* Houses: (description, date bought, cost, address, condition, sqFt, lotSize)
        myAssets.add(new House("Family Home", "2020-05-01", new BigDecimal("300000"),
                "100 Main Street, Dallas TX", 2, 2000, 8000));
        myAssets.add(new House("Beach House", "2024-07-04", new BigDecimal("200000"),
                "5 Ocean Drive, Galveston TX", 1, 1000, 4000));

        //* Vehicles: (description, date bought, cost, makeModel, year, odometer)
        myAssets.add(new Vehicle("Everyday Car", "2023-01-15", new BigDecimal("25000"),
                "Toyota Corolla", 2023, 30000));
        myAssets.add(new Vehicle("Old Work Truck", "2018-03-10", new BigDecimal("40000"),
                "Ford F-150", 2018, 150000));

        //* Step 3: loop through every asset in the list, one at a time
        //? Inside the loop, each one is labeled just "Asset" (Java doesn't know if it's a House or Vehicle)
        for (Asset asset : myAssets) {

            //* Step 4: DOWNCASTING, to get child-only info
            //! getAddress(), getYear(), getMakeModel() exist ONLY in House/Vehicle, NOT in Asset
            //! An "Asset" label can't see them, so we:
            //!   1. check the real type with instanceof (is it really a House?)
            //!   2. cast it: (House) asset = "treat this Asset as the House it really is"
            //!   3. now the House-only methods are unlocked
            //! Always check BEFORE casting: casting a Vehicle to House would crash
            String message = "";
            if (asset instanceof House) {
                House house = (House) asset;
                message = "House at " + house.getAddress();
            } else if (asset instanceof Vehicle) {
                Vehicle vehicle = (Vehicle) asset;
                message = "Vehicle: " + vehicle.getYear() + " " + vehicle.getMakeModel();
            }

            //* Step 5: print everything
            //* No cast needed here: getDescription(), getDateAcquired(), getOriginalCost(), getValue() all exist in Asset
            //? asset.getValue() runs the House or Vehicle version automatically (polymorphism)
            System.out.println("----------------------------------------");
            System.out.println(asset.getDescription());
            System.out.println(message);
            System.out.printf("Acquired: %s%n", asset.getDateAcquired());
            System.out.printf("Paid:     $%,.2f%n", asset.getOriginalCost());
            System.out.printf("Value:    $%,.2f%n", asset.getValue());
            System.out.println();
        }
    }
}