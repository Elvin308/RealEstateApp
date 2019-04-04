package com.example.androidrealestateapp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClassListItems
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

    //public ClassListItems(String name, String img)
    public ClassListItems(int PropertyID, String Email, String StreetName, String City, String State, String ZipCode, Double Price, Double NumOfBath, Double NumOfBed, Double NumOfGarages, String ListingType, boolean Fireplace, boolean Basement, boolean MainStHouse, boolean Pool, boolean BeachHouse, boolean AirCondition, boolean RentSpace, String SqFt, String LotSize, int YearBuilt, String HeatingSystem, String DistributionSystem)
    {
        //this.img = img;
        //this.name = name;
        this.PropertyID = PropertyID;
        this.Email = Email;
        this.StreetName = StreetName;
        this.City = City;
        this.State = State;
        this.ZipCode = ZipCode;
        this.Price = Price;
        this.NumOfBath = NumOfBath;
        this.NumOfBed = NumOfBed;
        this.NumOfGarages = NumOfGarages;
        this.ListingType = ListingType;
        this.Fireplace = Fireplace;
        this.Basement = Basement;
        this.MainStHouse = MainStHouse;
        this.Pool = Pool;
        this.BeachHouse = BeachHouse;
        this.AirCondition = AirCondition;
        this.RentSpace = RentSpace;
        this.SqFt = SqFt;
        this.LotSize = LotSize;
        this.YearBuilt = YearBuilt;
        this.HeatingSystem = HeatingSystem;
        this.DistributionSystem = DistributionSystem;
    }

    public ClassListItems(String Email, String StreetName, String City, String State, String ZipCode,
                          Double Price, Double NumOfBath, Double NumOfBed, Double NumOfGarages, String ListingType)
    {
        this.Email = Email;
        this.StreetName = StreetName;
        this.City = City;
        this.State = State;
        this.ZipCode = ZipCode;
        this.Price = Price;
        this.NumOfBath = NumOfBath;
        this.NumOfBed = NumOfBed;
        this.NumOfGarages = NumOfGarages;
        this.ListingType = ListingType;
    }

    public ClassListItems()
    {

    }

    //get methods

    public int getPropertyID() {return PropertyID;}
    public String getEmail() {return Email;}
    public String getStreetName() {return StreetName;}
    public String getCity() {return City;}
    public String getState() {return State;}
    public String getZipCode() {return ZipCode;}
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

    public String AddHouseSQL()
    {
        return  "INSERT INTO Listing (Email, StreetName, City, State, ZipCode, Price, NumOfBath, NumOfBed, NumOfGarages, ListingType, FirePlace, Basement, MainStHouse, Pool, BeachHouse, AirCondition, RentSpace, SqFt, LotSize, YearBuilt, HeatingSystem, DistributionSystem) " +
                "VALUES ('" + Email + "', '" + StreetName + "', '" + City + "', '" + State + "', '" + ZipCode + "', " + Price + ", " + NumOfBath + ", " +
                NumOfBed + ", " + NumOfGarages + ", '" + ListingType + "', " + Fireplace + ", " + Basement + ", " + MainStHouse + ", " +
                Pool + ", " + BeachHouse + ", " + AirCondition + ", " + RentSpace + ", '" + SqFt + "', '" + LotSize + "', " + YearBuilt + ", '" +
                HeatingSystem + "', '" + DistributionSystem + "', );";
    }
    /*public void ADDHouse()
    {
        ConnectionClass connectionClass = new ConnectionClass(); // Connection Class Initialization
        Connection conn = (Connection) connectionClass.CONN(); //Connection Object
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = stmt.executeQuery(Navigation.newhouse.AddHouseSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public void senHouse()
    {

    }
    public void insertHouse() throws SQLException {

        String SQL = AddHouseSQL();
        ConnectionClass con = new ConnectionClass();

        long id = 0;

        try (Connection conn = (Connection) con.CONN();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {


            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        //return id;
    }


}
