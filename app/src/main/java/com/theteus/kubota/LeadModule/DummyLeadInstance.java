package com.theteus.kubota.LeadModule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whorangester on 6/22/16.
 */
public class DummyLeadInstance {
    public static final List<LeadInstance> LEADS = new ArrayList<>();
    public static final List<String> KEYS = new ArrayList<>();
    public static final Map<String, LeadInstance> LEAD_MAP = new HashMap<>();
    public static final Map<String, String> ID_MAP = new HashMap<>();
    public static final int COUNT = 5;

    static {
        // Primary Key and Supplement
        String id[] = new String[] {"L0001", "L0002", "L0003", "L0004", "L0005"};
        int status[] = new int[] {117980000, 117980002, 117980001, 117980002, 117980000};
        String owner[] = new String[] {"SomeAdmin", "AnyAdmin", "AllAdmin", "NoAdmin", "JustAdmin"};
        // Dealer Section
        String shopName[] = new String[] {"บจก.มิตรแท้เซาท์เทิร์นจักรกล", "หจก.บุญสยามสตีลกระบี่", "หจก. คูโบต้า ก.แสงยนต์ กาญจนบุรี", "บจก. ก.แสงยนต์ คอร์เปอเรชั่น", "หจก. คูโบต้า ก.แสงยนต์ กาญจนบุรี สาขาพนมทวน"};
        String recordDate[] = new String[] {"11 มกราคม 2504", "23 กุมภาพันธ์ 2508", "31 พฤษภาคม 2513", "6 กันยายน 2017", "15 มีนาคม 2021"};
        String eventDate[] = new String[] {"11 มกราคม 2504", "23 กุมภาพันธ์ 2508", "31 พฤษภาคม 2513", "6 กันยายน 2017", "15 มีนาคม 2021"};
        String eventName[] = new String[] {"งานแสดงสินค้าคูโบต้า", "เกษตรกรแฟร์", "งานสัมมนาการปลูกข้าวนาปลัง", "งานวัด", "กิจกรรมคูโบต้าคู่เกษตกร"};
        String salesName[] = new String[] {"มานี ขายดี", "มานา ขายเก่ง", "ปรีดี ขายรวย", "สมบัติ ขยันขาย", "มารวย คนดี"};
        String eventLocation[] = new String[] {"บจก.มิตรแท้เซาท์เทิร์นจักรกล", "หจก.บุญสยามสตีลกระบี่", "หจก. คูโบต้า ก.แสงยนต์ กาญจนบุรี", "บจก. ก.แสงยนต์ คอร์เปอเรชั่น", "หจก. คูโบต้า ก.แสงยนต์ กาญจนบุรี สาขาพนมทวน"};
        String code[] = new String[] {"ลูกค้า Walk-In", "ลูกค้า Walk-In", "ลูกค้า Walk-In", "ลูกค้า Walk-In", "ลูกค้า Walk-In"};
        String contact[] = new String[] {"D001-C0001", "D002-C0001", "D003-C0001", "D004-C0001", "D005-C0001"};
        //General Section
        String titleName[] = new String[] {"นาย", "นาย", "นาง", "นาย", "นาย"};
        String firstName[] = new String[] {"Adam", "วิรุฬห์", "Yekaterina", "Hjalmar", "Enrico"};
        String lastName[] = new String[] {"Smith", "หิรัณยวิรุฬห์", "Alexeyevna", "Söderberg", "Dandolo"};
        String gender[] = new String[] {"ชาย", "ชาย", "หญิง", "ชาย", "ชาย"};
        String idCardNumber[] = new String[] {"1-2345-67890-12-3", "7-1231-84322-81-1", "9-8103-12801-89-5", "9-7791-81035-12-3", "7-7209-88820-99-1-4"};
        String customerType[] = new String[] {"บุคคลธรรมดา", "บุคคลธรรมดา", "บุคคลธรรมดา", "บุคคลธรรมดา", "บุคคลธรรมดา"};
        String birthday[] = new String[] {"10 กันยายน 2483", "12 มิถุนายน 2488", "12 มกราคม 2472", "3 ธันวาคม 2489", "7 มีนาคม 2485"};
        String phone[] = new String[] {"02-891-8458", "02-823-8128", "- - -", "02-464-5315", "02-899-0012"};
        String mobile1[] = new String[] {"- - -", "- - -", "081-621-2832", "082-349-1927", "087-172-1283"};
        String mobile2[] = new String[] {"- - -", "- - -", "- - -", "087-128-7120", "- - -"};
        //Address Section
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
        //Wishlist Section
        int followStatus[] = new int[] {117980000, 117980002, 117980000, 117980003, 117980004};
        double area[] = new double[] {12, 15, 20, 30 , 100};
        int product1[] = new int[] {117980000, 117980002, 117980000, 117980000, 117980003};
        int product2[] = new int[] {117980001, 117980000, 0, 117980000, 117980000};
        int product3[] = new int[] {117980002, 0, 0, 117980001, 117980000};
        int product4[] = new int[] {0, 0, 0, 0, 0};
        int product5[] = new int[] {0, 0, 0, 0, 0};
        String model1[] = new String[] {"MX-12", "AP-13", "MX-12", "MX-04", "BH-01"};
        String model2[] = new String[] {"PP-11", "MX-12", null, "MX-12", "MX-12"};
        String model3[] = new String[] {"AP-13", null, null, "PP-11", "MX-04"};
        String model4[] = new String[] {null, null, null, null, null};
        String model5[] = new String[] {null, null, null, null, null};

        for(int i = 0; i < COUNT; i++) {
            LeadInstance lead = new LeadInstance();
            lead.id = id[i];
            lead.status = status[i];
            lead.owner = owner[i];
            lead.shopName = shopName[i];
            lead.recordDate = recordDate[i];
            lead.eventDate = eventDate[i];
            lead.eventName = eventName[i];
            lead.salesName = salesName[i];
            lead.eventLocation = eventLocation[i];
            lead.code = code[i];
            lead.contact = contact[i];
            lead.titleName = titleName[i];
            lead.firstName = firstName[i];
            lead.lastName = lastName[i];
            lead.gender = gender[i];
            lead.idCardNumber = idCardNumber[i];
            lead.customerType = customerType[i];
            lead.birthday = birthday[i];
            lead.phone = phone[i];
            lead.mobile1 = mobile1[i];
            lead.mobile2 = mobile2[i];
            lead.houseNumber = houseNumber[i];
            lead.buildingName = buildingName[i];
            lead.buildingFloor = buildingFloor[i];
            lead.buildingRoom = buildingRoom[i];
            lead.houseGroup = houseGroup[i];
            lead.soi = soi[i];
            lead.street = street[i];
            lead.subdistrict = subdistrict[i];
            lead.district = district[i];
            lead.province = province[i];
            lead.postalCode = postalCode[i];
            lead.followStatus = followStatus[i];
            lead.area = area[i];
            lead.product1 = product1[i];
            lead.product2 = product2[i];
            lead.product3 = product3[i];
            lead.product4 = product4[i];
            lead.product5 = product5[i];
            lead.model1 = model1[i];
            lead.model2 = model2[i];
            lead.model3 = model3[i];
            lead.model4 = model4[i];
            lead.model5 = model5[i];

            LEADS.add(lead);
            LEAD_MAP.put(lead.id, lead);
            KEYS.add(lead.id + " : " + lead.firstName + " " + lead.lastName);
            ID_MAP.put(lead.id + " : " + lead.firstName + " " + lead.lastName, lead.id);
        }
    }

    public static List<LeadInstance> searchByContactId(String contactId) {
        List<LeadInstance> result = new ArrayList<>();

        for (LeadInstance l : LEADS)
            if(l.contact.equals(contactId)) result.add(l);

        return result;
    }
}
