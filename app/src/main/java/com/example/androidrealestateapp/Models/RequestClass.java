package com.example.androidrealestateapp.Models;

public class RequestClass {
    int Id;
    String REmail;
    String SEmail;
    int PropertyID;
    String Message;
    String Date;
    String FirstName;
    String LastName;
    String Phone;

    public RequestClass(int id, String REmail, String SEmail, int propertyID, String message, String date, String firstName, String lastName, String phone) {
        Id = id;
        this.REmail = REmail;
        this.SEmail = SEmail;
        PropertyID = propertyID;
        Message = message;
        Date = date;
        FirstName = firstName;
        LastName = lastName;
        Phone = phone;
    }

    public RequestClass(int id, String SEmail, int propertyID, String message, String firstName, String lastName, String phone,String Date) {
        Id = id;
        this.SEmail = SEmail;
        PropertyID = propertyID;
        Message = message;
        FirstName = firstName;
        LastName = lastName;
        Phone = phone;
        this.Date = Date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getREmail() {
        return REmail;
    }

    public void setREmail(String REmail) {
        this.REmail = REmail;
    }

    public String getSEmail() {
        return SEmail;
    }

    public void setSEmail(String SEmail) {
        this.SEmail = SEmail;
    }

    public int getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(int propertyID) {
        PropertyID = propertyID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
