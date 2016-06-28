package com.theteus.kubota.LeadModule;

import java.util.Date;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadInstance {
    // Primary Key And Supplements
    public String id;
    public int status;
    public String owner;
    // Dealer Section
    public String shopName;
    public String recordDate;
    public String eventDate;
    public String eventName;
    public String salesName;
    public String eventLocation;
    public String code;
    public String contact;
    // General Section
    public String titleName;
    public String firstName;
    public String lastName;
    public String gender;
    public String idCardNumber;
    public String customerType;
    public String birthday;
    public String phone;
    public String mobile1;
    public String mobile2;
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
    // Wishlist Section
    public int followStatus;
    public double area;
    public int product1, product2, product3, product4, product5;
    public String model1, model2, model3, model4, model5;

    public LeadInstance() {}
}
