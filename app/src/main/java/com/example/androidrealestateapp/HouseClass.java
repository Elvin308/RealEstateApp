package com.example.androidrealestateapp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HouseClass
{

    //public String img; //Image URL
    //public String name; //Name

    public String Email;
    //These can not be null
    public int PropertyID;
    public String StreetName;
    public String City;
    public String State;
    public String ZipCode;
    public Double Price;
    public Double NumOfFloors;
    public Double NumOfBath;
    public Double NumOfBed;
    public Double NumOfGarages;
    public String ListingType;
    //These can be null
    public boolean Fireplace;
    public boolean Basement;
    public boolean MainStHouse;
    public boolean Pool;
    public boolean BeachHouse;
    public boolean AirCondition;
    public boolean RentSpace;
    public String SqFt = "0";
    public String LotSize = "0";
    public int YearBuilt = 0000;
    public String HeatingSystem = "null";
    public String DistributionSystem = "null";

    public HouseClass(String email, int propertyID, String streetName, String city, String state, String zipCode, Double price, Double numOfFloors, Double numOfBath, Double numOfBed, Double numOfGarages, String listingType, boolean fireplace, boolean basement, boolean mainStHouse, boolean pool, boolean beachHouse, boolean airCondition, boolean rentSpace, String sqFt, String lotSize, int yearBuilt, String heatingSystem, String distributionSystem) {
        Email = email;
        PropertyID = propertyID;
        StreetName = streetName;
        City = city;
        State = state;
        ZipCode = zipCode;
        Price = price;
        NumOfFloors = numOfFloors;
        NumOfBath = numOfBath;
        NumOfBed = numOfBed;
        NumOfGarages = numOfGarages;
        ListingType = listingType;
        Fireplace = fireplace;
        Basement = basement;
        MainStHouse = mainStHouse;
        Pool = pool;
        BeachHouse = beachHouse;
        AirCondition = airCondition;
        RentSpace = rentSpace;
        SqFt = sqFt;
        LotSize = lotSize;
        YearBuilt = yearBuilt;
        HeatingSystem = heatingSystem;
        DistributionSystem = distributionSystem;
    }

    public HouseClass(int propertyID, String streetName, String city, String state, String zipcode, double price, double numOfFloors, double numOfBed, double numOfBath, double numOfGarages,String listingType) {
        this.PropertyID = propertyID;
        this.StreetName = streetName;
        this.City = city;
        this.State = state;
        this.ZipCode = zipcode;
        this.Price = price;
        this.NumOfFloors=numOfFloors;
        this.NumOfBed = numOfBed;
        this.NumOfBath = numOfBath;
        this.NumOfGarages = numOfGarages;
        this.ListingType=listingType;
    }

    public void addHouseInfo( int propertyID, String streetName, String city, String state, String zipCode, Double price, Double numOfFloors, Double numOfBath, Double numOfBed, Double numOfGarages, String listingType, boolean fireplace, boolean basement, boolean mainStHouse, boolean pool, boolean beachHouse, boolean airCondition, boolean rentSpace, String sqFt, String lotSize, int yearBuilt, String heatingSystem, String distributionSystem) {

        PropertyID = propertyID;
        StreetName = streetName;
        City = city;
        State = state;
        ZipCode = zipCode;
        Price = price;
        NumOfFloors = numOfFloors;
        NumOfBath = numOfBath;
        NumOfBed = numOfBed;
        NumOfGarages = numOfGarages;
        ListingType = listingType;
        Fireplace = fireplace;
        Basement = basement;
        MainStHouse = mainStHouse;
        Pool = pool;
        BeachHouse = beachHouse;
        AirCondition = airCondition;
        RentSpace = rentSpace;
        SqFt = sqFt;
        LotSize = lotSize;
        YearBuilt = yearBuilt;
        HeatingSystem = heatingSystem;
        DistributionSystem = distributionSystem;
    }


    public HouseClass()
    {

    }

    //get methods
    public int getPropertyID() {return PropertyID;}
    public String getEmail() {return Email;}
    public String getStreetName() {return StreetName;}
    public String getCity() {return City;}
    public String getState() {return State;}
    public String getZipCode() {return ZipCode;}
    public Double getNumOfFloors() {return NumOfFloors;}
    public Double getPrice() {return Price;}
    public Double getNumOfBath() {return NumOfBath;}
    public Double getNumOfBed() {return NumOfBed;}
    public Double getNumOfGarages() {return NumOfGarages;}
    public String getListingType() {return ListingType;}
    public boolean getFireplace() {return Fireplace;}
    public boolean getBasement() {return Basement;}
    public boolean getMainStHouse() {return MainStHouse;}
    public boolean getPool() {return Pool;}
    public boolean getBeachHouse() {return BeachHouse;}
    public boolean getAirCondition() {return AirCondition;}
    public boolean getRentSpace() {return RentSpace;}
    public String getSqFt() {return SqFt;}
    public String getLotSize() {return LotSize;}
    public int getYearBuilt() {return YearBuilt;}
    public String getHeatingSystem() {return HeatingSystem;}
    public String getDistributionSystem() {return DistributionSystem;}

    //set methods
    public void setPropertyID(int PropertyID) {this.PropertyID = PropertyID;}
    public void setEmail(String Email){this.Email = Email;}
    public void setStreetName(String StreetName) {this.StreetName = StreetName;}
    public void setCity(String City) {this.City = City;}
    public void setState(String State) {this.State = State;}
    public void setZipCode(String ZipCode) {this.ZipCode = ZipCode;}
    public void setNumOfFloors(Double numOfFloors) {NumOfFloors = numOfFloors;}
    public void setPrice(Double Price) {this.Price = Price;}
    public void setNumOfBath(Double NumOfBath) {this.NumOfBath = NumOfBath;}
    public void setNumOfBed(Double NumOfBed) {this.NumOfBed = NumOfBed;}
    public void setNumOfGarages(Double NumOfGarages) {this.NumOfGarages = NumOfGarages;}
    public void setListingType(String ListingType) {this.ListingType = ListingType;}
    public void setFireplace(boolean Fireplace) {this.Fireplace = Fireplace;}
    public void setBasement(boolean Basement) {this.Basement = Basement;}
    public void setMainStHouse(boolean MainStHouse) {this.MainStHouse = MainStHouse;}
    public void setPool(boolean Pool) {this.Pool = Pool;}
    public void setBeachHouse(boolean BeachHouse) {this.BeachHouse = BeachHouse;}
    public void setAirCondition(boolean AirCondition) {this.AirCondition = AirCondition;}
    public void setRentSpace(boolean RentSpace) {this.RentSpace = RentSpace;}
    public void setSqFt(String SqFt) {this.SqFt = SqFt;}
    public void setLotSize(String LotSize) {this.LotSize = LotSize;}
    public void setYearBuilt(int YearBuilt) {this.YearBuilt = YearBuilt;}
    public void setHeatingSystem(String HeatingSystem) {this.HeatingSystem = HeatingSystem;}
    public void setDistributionSystem(String DistributionSystem) {this.DistributionSystem = DistributionSystem;}
}
