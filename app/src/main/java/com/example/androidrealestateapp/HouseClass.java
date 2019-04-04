package com.example.androidrealestateapp;

public class HouseClass {
   public int propertyId;
   public String streetName;
   public String city;
   public String state;
   public String zipcode;
   public int price;
   public double numOfBath;
   public int numOfBed;
   public int numOfGarage;
   public String listingType;
   public boolean fireplace;
   public boolean basement;
   public boolean mainStHouse;
   public boolean pool;
   public boolean beachHouse;
   public boolean airConditining;
   public boolean rentSpace;
   public String sqFt;
   public int yearBuilt;
   public String heatingSystem;
   public String distributionSystem;


    public HouseClass(String streetName, String city, String state, String zipcode, int price, int numOfBed) {
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.price = price;
        this.numOfBed = numOfBed;
    }

    public HouseClass(String streetName, String city, String state, String zipcode, int price, double numOfBath, int numOfBed, int numOfGarage, String listingType, boolean fireplace, boolean basement, boolean mainStHouse, boolean pool, boolean beachHouse, boolean airConditining, boolean rentSpace, String sqFt, int yearBuilt, String heatingSystem, String distributionSystem) {
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.price = price;
        this.numOfBath = numOfBath;
        this.numOfBed = numOfBed;
        this.numOfGarage = numOfGarage;
        this.listingType = listingType;
        this.fireplace = fireplace;
        this.basement = basement;
        this.mainStHouse = mainStHouse;
        this.pool = pool;
        this.beachHouse = beachHouse;
        this.airConditining = airConditining;
        this.rentSpace = rentSpace;
        this.sqFt = sqFt;
        this.yearBuilt = yearBuilt;
        this.heatingSystem = heatingSystem;
        this.distributionSystem = distributionSystem;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public int getPrice() {
        return price;
    }

    public double getNumOfBath() {
        return numOfBath;
    }

    public int getNumOfBed() {
        return numOfBed;
    }

    public int getNumOfGarage() {
        return numOfGarage;
    }

    public String getListingType() {
        return listingType;
    }

    public boolean isFireplace() {
        return fireplace;
    }

    public boolean isBasement() {
        return basement;
    }

    public boolean isMainStHouse() {
        return mainStHouse;
    }

    public boolean isPool() {
        return pool;
    }

    public boolean isBeachHouse() {
        return beachHouse;
    }

    public boolean isAirConditining() {
        return airConditining;
    }

    public boolean isRentSpace() {
        return rentSpace;
    }

    public String getSqFt() {
        return sqFt;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    public String getHeatingSystem() {
        return heatingSystem;
    }

    public String getDistributionSystem() {
        return distributionSystem;
    }
}
