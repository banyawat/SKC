package com.theteus.kubota.ContactModule;

import com.theteus.kubota.ContactModule.ContactInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whorangester on 6/27/16.
 */
public class DummyContactInstance {
    public static final List<ContactInstance> CONTACTS = new ArrayList<>();
    //public static final List<String> KEYS = new ArrayList<>();
    public static final Map<String, ContactInstance> CONTACT_MAP = new HashMap<>();
    //public static final Map<String, String> ID_MAP = new HashMap<>();
    public static final int COUNT = 5;

    static {
        // Primary Key and Supplements
        String id[] = new String[] {"D001-C0001", "D002-C0001", "D003-C0001", "D004-C0001", "D005-C0001"};
        String owner[] = new String[] {"SomeAdmin", "AnyAdmin", "AllAdmin", "NoAdmin", "JustAdmin"};
        // General Section
        String titleName[] = new String[] {"นาย", "นาย", "นาง", "นาย", "นาย"};
        String firstName[] = new String[] {"Adam", "วิรุฬห์", "Yekaterina", "Hjalmar", "Enrico"};
        String lastName[] = new String[] {"Smith", "หิรัณยวิรุฬห์", "Alexeyevna", "Söderberg", "Dandolo"};
        String gender[] = new String[] {"ชาย", "ชาย", "หญิง", "ชาย", "ชาย"};
        String idCardNumber[] = new String[] {"1-2345-67890-12-3", "7-1231-84322-81-1", "9-8103-12801-89-5", "9-7791-81035-12-3", "7-7209-88820-99-1-4"};
        String customerType[] = new String[] {"บุคคลธรรมดา", "บุคคลธรรมดา", "บุคคลธรรมดา", "บุคคลธรรมดา", "บุคคลธรรมดา"};
        String birthday[] = new String[] {"10 กันยายน 2483", "12 มิถุนายน 2488", "12 มกราคม 2472", "3 ธันวาคม 2489", "7 มีนาคม 2485"};
        String phone[] = new String[] {"02-891-8458", "02-823-8128", "- - -", "02-464-5315", "02-899-0012"};
        String email[] = new String[] {"smith.a@somemail.com", "hirun.wiroon@anymail.com", "alexeyvna.y@allmail.com", "sod.hjal@nomail.com", "dandolo@email.com"};
        String fax[] = new String[] {"- - -", "- - -", "- - -", "- - -", "- - -"};
        String skcPhone[] = new String[] {"- - -", "- - -", "- - -", "- - -", "- - -"};
        String mobile1[] = new String[] {"- - -", "- - -", "081-621-2832", "082-349-1927", "087-172-1283"};
        String mobile2[] = new String[] {"- - -", "- - -", "- - -", "087-128-7120", "- - -"};
        int numberOfCar[] = new int[] {2, 3, 1, 0, 1};
        boolean flagWelcomeUpdate[] = new boolean[] {false, true, true, false, false};
        // Address Section
        String houseNumber[] = new String[] {"123/456", "21/234", "14/12", "56", "81/4"};
        String buildingName[] = new String[] {"อาคารหมายเลข 1", "คอนโดมิเนียมหรูกลางใจเมือง", "- - -", "- - -", "- - -"};
        String buildingFloor[] = new String[] {"12", "15", "- - -", "- - -", "- - -"};
        String buildingRoom[] = new String[] {"1201", "1522", "- - -", "- - -", "- - -"};
        String houseGroup[] = new String[] {"1", "2", "3", "4", "5"};
        String soi[] = new String[] {"ดำเนินสะดวก 12", "ราชดำริ 31", "ประชาราษฎร์ 16", "มไหสวรรค์ 2", "ประชาอุทิศ 17"};
        String street[] = new String[] {"ดำเนินสะดวก", "ราชดำริ", "ประชาราษฎร์", "มไหสวรรค์", "ประชาอุทิศ"};
        String subdistrict[] = new String[] {"บางรัก", "บางแค", "บางซื่อ", "บางกะปิ", "บางนา"};
        String district[] = new String[] {"บางรัก", "บางแค", "บางซื่อ", "บางกะปิ", "บางนา"};
        String province[] = new String[] {"กรุงเทพมหานคร", "ปทุมธานี", "นครสวรรค์", "ชุมพร", "หนองคาย"};
        String postalCode[] = new String[] {"12345", "91271", "71931", "68192", "17812"};
        // Questionnaire Section
        int occupation1[] = new int[] {177980020, 177980021, 177980022, 177980023, 177980024};
        int occupation2[] = new int[] {177980030, 177980031, 0, 177980033, 177980034};
        int occupation3[] = new int[] {177980040, 0, 0, 177980043, 177980044};
        int occupation4[] = new int[] {0, 0, 0, 0, 0};
        int occupation5[] = new int[] {0, 0, 0, 0, 0};
        int plant1[] = new int[] {177980020, 177980021, 177980022, 177980023, 177980024};
        int plant2[] = new int[] {177980030, 177980031, 0, 177980033, 177980034};
        int plant3[] = new int[] {177980040, 0, 0, 177980043, 177980044};
        int plant4[] = new int[] {0, 0, 0, 0, 0};
        int plant5[] = new int[] {0, 0, 0, 0, 0};
        double area1[] = new double[] {2, 3, 4, 5, 6};
        double area2[] = new double[] {1, 3, 0, 5, 7};
        double area3[] = new double[] {2, 0, 0, 3, 5};
        double area4[] = new double[] {0, 0, 0, 0, 0};
        double area5[] = new double[] {0, 0, 0, 0, 0};
        int product1[] = new int[] {117980000, 117980002, 117980000, 117980000, 117980003};
        int product2[] = new int[] {117980001, 117980000, 0, 117980000, 117980000};
        int product3[] = new int[] {117980002, 0, 0, 117980001, 117980000};
        int product4[] = new int[] {0, 0, 0, 0, 0};
        int product5[] = new int[] {0, 0, 0, 0, 0};
        int landOwner[] = new int[] {117980000, 117980001, 117980002, 117980003, 117980004};
        int harvestMethod[] = new int[] {117980000, 117980001, 117980002, 117980003, 117980004};
        int farmingMethod[] = new int[] {117980000, 117980001, 117980002, 117980003, 117980004};
        double yearlyIncome[] = new double[] {100000, 200000, 250000, 50000, 75000};

        for(int i = 0; i < COUNT; i++) {
            ContactInstance contact = new ContactInstance();
            contact.id = id[i];
            contact.owner = owner[i];
            contact.titleName = titleName[i];
            contact.firstName = firstName[i];
            contact.lastName = lastName[i];
            contact.gender = gender[i];
            contact.idCardNumber = idCardNumber[i];
            contact.customerType = customerType[i];
            contact.birthday = birthday[i];
            contact.phone = phone[i];
            contact.email = email[i];
            contact.fax = fax[i];
            contact.skcPhone = skcPhone[i];
            contact.mobile1 = mobile1[i];
            contact.mobile2 = mobile2[i];
            contact.numberOfCar = numberOfCar[i];
            contact.flagWelcomeUpdate = flagWelcomeUpdate[i];
            contact.houseNumber = houseNumber[i];
            contact.buildingName = buildingName[i];
            contact.buildingFloor = buildingFloor[i];
            contact.buildingRoom = buildingRoom[i];
            contact.houseGroup = houseGroup[i];
            contact.soi = soi[i];
            contact.street = street[i];
            contact.subdistrict = subdistrict[i];
            contact.district = district[i];
            contact.province = province[i];
            contact.postalCode = postalCode[i];
            contact.occupation1 = occupation1[i];
            contact.occupation2 = occupation2[i];
            contact.occupation3 = occupation3[i];
            contact.occupation4 = occupation4[i];
            contact.occupation5 = occupation5[i];
            contact.plant1 = plant1[i];
            contact.plant2 = plant2[i];
            contact.plant3 = plant3[i];
            contact.plant4 = plant4[i];
            contact.plant5 = plant5[i];
            contact.area1 = area1[i];
            contact.area2 = area2[i];
            contact.area3 = area3[i];
            contact.area4 = area4[i];
            contact.area5 = area5[i];
            contact.product1 = product1[i];
            contact.product2 = product2[i];
            contact.product3 = product3[i];
            contact.product4 = product4[i];
            contact.product5 = product5[i];
            contact.landOwner = landOwner[i];
            contact.harvestMethod = harvestMethod[i];
            contact.farmingMethod = farmingMethod[i];
            contact.yearlyIncome = yearlyIncome[i];

            CONTACTS.add(contact);
            CONTACT_MAP.put(contact.id, contact);
        }
    }
}
