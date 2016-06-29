package com.theteus.kubota.ChassisModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whorangester on 6/28/16.
 */
public class DummyChassisInstance {
    public static final List<ChassisInstance> CHASSIS = new ArrayList<>();
    //public static final List<String> KEYS = new ArrayList<>();
    public static final Map<String, ChassisInstance> CHASSIS_MAP = new HashMap<>();
    //public static final Map<String, String> ID_MAP = new HashMap<>();
    public static final int COUNT = 10;

    static {
        String chassisNumber[] = new String[] {"CH000001", "CH000002", "CH000003", "CH000004", "CH000005", "CH000006", "CH000007", "CH000008", "CH000009", "CH000010"};
        String engineNumber[] = new String[] {"EN123KL", "EN921OK", "EN412OA", "EN123DO", "EN991RR", "EN456IO", "EN000TH", "EN091PE", "EN400RP", "EN141OO"};
        int productType[] = new int[] {177980000, 177980001, 177980002, 177980003, 177980004, 177980006, 177980006, 177980007, 177980000, 177980006, 177980003};
        String productModel[] = new String[] {"AT001", "AT002", "AT001", "AT002", "AT001", "AT002", "AT001", "AT002", "AT001", "AT002"};
        int productStatus[] = new int[] {177980000, 177980000, 177980001, 177980001, 177980002, 177980002, 177980003, 177980003, 177980000, 177980002};
        String statusChangeDate[] = new String[] {"1 มกราคม 2558", "2 มกราคม 2558", "3 มกราคม 2558", "4 มกราคม 2558", "5 มกราคม 2558", "6 มกราคม 2558", "7 มกราคม 2558", "8 มกราคม 2558", "9 มกราคม 2558", "10 มกราคม 2558"};
        String remarkADMS[] = new String[] {null, null, null, null, null, null, null, null, null, null};

        String contractNumber[] = new String[] {"1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009", "1010"};
        int saleCondition[] = new int[] {177980000, 177980001, 177980002, 177980003, 177980004, 177980005, 177980006, 177980007, 177980000, 177980005, 177980003};
        String purchaseDate[] = new String[] {"1 มกราคม 2558", "2 มกราคม 2558", "3 มกราคม 2558", "4 มกราคม 2558", "5 มกราคม 2558", "6 มกราคม 2558", "7 มกราคม 2558", "8 มกราคม 2558", "9 มกราคม 2558", "10 มกราคม 2558"};
        String user[] = new String[] {null, null, null, null, null, null, null, null, null, null};
        String contact[] = new String[] {"D001-C0001", "D003-C0001", "D005-C0001", "D002-C0001", "D001-C0001", "D001-C0001", "D002-C0001", "D003-C0001", "D004-C0001", "D002-C0001"};
        String userTel[] = new String[] {null, null, null, null, null, null, null, null, null, null};
        String dealer[] = new String[] {null, null, null, null, null, null, null, null, null, null};

        String vipCard[] = new String[] {null, null, null, null, null, null, null, null, null, null};
        String vipStartDate[] = new String[] {null, null, null, null, null, null, null, null, null, null};
        String vipEndDate[] = new String[] {null, null, null, null, null, null, null, null, null, null};

        for(int i = 0; i< 10; i++) {
            ChassisInstance chassis = new ChassisInstance();
            chassis.chassisNumber = chassisNumber[i];
            chassis.engineNumber = engineNumber[i];
            chassis.productType = productType[i];
            chassis.productModel = productModel[i];
            chassis.productStatus = productStatus[i];
            chassis.statusChangeDate = statusChangeDate[i];
            chassis.remarkADMS = remarkADMS[i];

            chassis.contractNumber = contractNumber[i];
            chassis.saleCondition = saleCondition[i];
            chassis.purchaseDate = purchaseDate[i];
            chassis.user = user[i];
            chassis.contact = contact[i];
            chassis.userTel = userTel[i];
            chassis.dealer = dealer[i];

            chassis.vipCard = vipCard[i];
            chassis.vipStartDate = vipStartDate[i];
            chassis.vipEndDate = vipEndDate[i];

            CHASSIS.add(chassis);
            CHASSIS_MAP.put(chassis.chassisNumber, chassis);
        }
    }
    public static List<ChassisInstance> searchByContactId(String contactId) {
        List<ChassisInstance> result = new ArrayList<>();

        for (ChassisInstance c : CHASSIS)
            if(c.contact.equals(contactId)) result.add(c);

        return result;
    }
}
