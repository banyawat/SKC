package com.theteus.kubota.ContactModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whorangester on 6/27/16.
 */
public class ContactInstance {
    // Primary Key And Supplements
    public String id;
    public String owner;
    // General Section
    public String titleName;
    public String firstName;
    public String lastName;
    public String gender;
    public String idCardNumber;
    public String customerType;
    public String birthday;
    public String phone;
    public String email;
    public String fax;
    public String skcPhone;
    public String mobile1;
    public String mobile2;
    public int numberOfCar;
    public boolean flagWelcomeUpdate;
    // Address Section
    public String houseNumber;
    public String buildingName;
    public String buildingFloor;
    public String buildingRoom;
    public String houseGroup;
    public String soi;
    public String street;
    public String subdistrict;
    public String district;
    public String province;
    public String postalCode;
    // Questionnaire Section
    public int occupation1, occupation2, occupation3, occupation4, occupation5;
    public int plant1, plant2, plant3, plant4, plant5;
    public double area1, area2, area3, area4, area5;
    public int product1, product2, product3, product4, product5;
    public int landOwner;
    public int harvestMethod;
    public int farmingMethod;
    public double yearlyIncome;
}
