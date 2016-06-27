package com.theteus.kubota;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by whorangester on 6/27/16.
 */
public class Reference {
    public static final Map<Integer, String> MASTER_OCCUPATION;
    public static final Map<Integer, String> MASTER_PLANT;
    public static final Map<Integer, String> MASTER_INTERESTPRODUCT;
    // Dealer Contact
    public static final Map<Integer, String> MASTER_LANDOWNER;
    public static final Map<Integer, String> MASTER_HARVESTMETHOD;
    public static final Map<Integer, String> MASTER_FARMINGMETHOD;
    // Lead
    public static final Map<Integer, String> MASTER_FOLLOWSTATUS;
    // Activities
    public static final Map<Integer, String> MASTER_PURCHASESTATUS;
    public static final Map<Integer, String> MASTER_DECISIONPERIOD;
    public static final Map<Integer, String> MASTER_PURCHASEREASON;
    public static final Map<Integer, String> MASTER_NOPURCHASEREASON;

    // Subject To Change
    public static final Map<Integer, String> MASTER_LEADSTATUS;
    public static final Map<Integer, String> MASTER_ACTIVITYTYPE;
    public static final Map<Integer, String> MASTER_ACTIVITYSTATUS;

    static {
        MASTER_OCCUPATION = new HashMap<>();
        MASTER_OCCUPATION.put(177980000, "(ยกเลิก) - เกษตรกร");
        MASTER_OCCUPATION.put(177980001, "(ยกเลิก) - ขายของชำ");
        MASTER_OCCUPATION.put(177980002, "(ยกเลิก) - ครู/อาจารย์(รร.เอกชน)");
        MASTER_OCCUPATION.put(177980003, "(ยกเลิก) - ค้าขาย");
        MASTER_OCCUPATION.put(177980004, "(ยกเลิก) - ชาวไร่");
        MASTER_OCCUPATION.put(177980005, "(ยกเลิก) - ชาวสวน");
        MASTER_OCCUPATION.put(177980006, "(ยกเลิก) - ธุรกิจ SML");
        MASTER_OCCUPATION.put(177980007, "(ยกเลิก) - ธุรกิจส่วนตัว/อาชีพอิสระ");
        MASTER_OCCUPATION.put(177980008, "(ยกเลิก) - นายหน้า");
        MASTER_OCCUPATION.put(177980009, "(ยกเลิก) - พนักงานบริษัท/รัฐวิสาหกิจ");
        MASTER_OCCUPATION.put(177980010, "(ยกเลิก) - พนักงานรัฐวิสาหกิจ");
        MASTER_OCCUPATION.put(177980011, "(ยกเลิก) - ร้บจ้าง");
        MASTER_OCCUPATION.put(177980012, "(ยกเลิก) - รับราชการ");
        MASTER_OCCUPATION.put(177980013, "(ยกเลิก) - ลูกจ้างชั่วคราว");
        MASTER_OCCUPATION.put(177980014, "(ยกเลิก) - ลูกจ้างประจำ");
        MASTER_OCCUPATION.put(177980015, "(ยกเลิก) - วัสดุก่อสร้าง");
        MASTER_OCCUPATION.put(177980016, "(ยกเลิก) - อื่น ๆ ( ธุรกิจส่วนตัว/อาชีพอิสระ )");
        MASTER_OCCUPATION.put(177980017, "(ยกเลิก) - อื่น ๆ ( พนักงานบริษัท/รัฐวิสาหกิจ ");
        MASTER_OCCUPATION.put(177980018, "(ยกเลิก) - อื่น ๆ ( ร้บจ้าง )");
        MASTER_OCCUPATION.put(177980019, "(ยกเลิก) - อื่น ๆ ( รับราชการ )");
        MASTER_OCCUPATION.put(177980020, "เกษตรกร");
        MASTER_OCCUPATION.put(177980021, "ข้าราชการ-อื่นๆ");
        MASTER_OCCUPATION.put(177980022, "ครู/อาจารย์");
        MASTER_OCCUPATION.put(177980023, "ทนายความ");
        MASTER_OCCUPATION.put(177980024, "ทหาร/ตำรวจ");
        MASTER_OCCUPATION.put(177980025, "ธุรกิจส่วนตัว-ก่อสร้าง/รับเหมา");
        MASTER_OCCUPATION.put(177980026, "ธุรกิจส่วนตัว-ขนส่ง");
        MASTER_OCCUPATION.put(177980027, "ธุรกิจส่วนตัว-ซื้อขายผลผลิตการเกษตร");
        MASTER_OCCUPATION.put(177980028, "ธุรกิจส่วนตัว-บริการ");
        MASTER_OCCUPATION.put(177980029, "ธุรกิจส่วนตัว-อาหาร,เครื่องดื่ม,ผักและผลไม้");
        MASTER_OCCUPATION.put(177980030, "ธุรกิจส่วนตัว-อื่นๆ");
        MASTER_OCCUPATION.put(177980031, "ประมง");
        MASTER_OCCUPATION.put(177980032, "ปศุสัตว์");
        MASTER_OCCUPATION.put(177980033, "พนักงานบริษัทเอกชน/รัฐวิสาหกิจ");
        MASTER_OCCUPATION.put(177980034, "แพทย์/พยาบาล");
        MASTER_OCCUPATION.put(177980035, "ลูกจ้าง/รับจ้าง - ก่อสร้าง");
        MASTER_OCCUPATION.put(177980036, "ลูกจ้าง/รับจ้าง-ขับรถ");
        MASTER_OCCUPATION.put(177980037, "ลูกจ้าง/รับจ้าง-ทางการเกษตร");
        MASTER_OCCUPATION.put(177980038, "ลูกจ้าง/รับจ้าง-อื่นๆ");
        MASTER_OCCUPATION.put(177980039, "ธุรกิจส่วนตัว - ซื้อขายผลผลิตการเกษตร");
        MASTER_OCCUPATION.put(177980040, "เจ้าหน้าที่ ธกส. / สกต.");
        MASTER_OCCUPATION.put(177980041, "อื่นๆ");

        MASTER_PLANT = new HashMap<>();
        MASTER_PLANT.put(117980000, "(ยกเลิก) -  ข้าว");
        MASTER_PLANT.put(117980001, "(ยกเลิก) -  ชาวทำเล");
        MASTER_PLANT.put(117980002, "(ยกเลิก) -  ถั่วเขียว");
        MASTER_PLANT.put(117980003, "(ยกเลิก) -  ถั่วเหลือง");
        MASTER_PLANT.put(117980004, "(ยกเลิก) -  ทำการประมง");
        MASTER_PLANT.put(117980005, "(ยกเลิก) -  ฟาร์ม");
        MASTER_PLANT.put(117980006, "(ยกเลิก) -  ฟาร์มไก่");
        MASTER_PLANT.put(117980007, "(ยกเลิก) -  ฟาร์มโคนม");
        MASTER_PLANT.put(117980008, "(ยกเลิก) -  ฟาร์มโคเนื้อ");
        MASTER_PLANT.put(117980009, "(ยกเลิก) -  ฟาร์มเป็ด");
        MASTER_PLANT.put(117980010, "(ยกเลิก) -  ฟาร์มสุกร");
        MASTER_PLANT.put(117980011, "(ยกเลิก) -  เลี้ยงกบ");
        MASTER_PLANT.put(117980012, "(ยกเลิก) -  เลี้ยงกุ้ง");
        MASTER_PLANT.put(117980013, "(ยกเลิก) -  เลี้ยงควาย");
        MASTER_PLANT.put(117980014, "(ยกเลิก) -  เลี้ยงปลา");
        MASTER_PLANT.put(117980015, "(ยกเลิก) -  เลี้ยงวัว");
        MASTER_PLANT.put(117980016, "(ยกเลิก) -  เลี้ยงสัตว์");
        MASTER_PLANT.put(117980017, "กระเทียม");
        MASTER_PLANT.put(117980018, "กล้วย");
        MASTER_PLANT.put(117980019, "กะหล่ำต่างๆ");
        MASTER_PLANT.put(117980020, "กาแฟ");
        MASTER_PLANT.put(117980021, "แก้วมังกร");
        MASTER_PLANT.put(117980022, "ขนุน");
        MASTER_PLANT.put(117980023, "ข้าวนาปรัง");
        MASTER_PLANT.put(117980024, "ข้าวนาปี");
        MASTER_PLANT.put(117980025, "ข้าวโพด");
        MASTER_PLANT.put(117980026, "ข้าวฟ่าง");
        MASTER_PLANT.put(117980027, "คะน้า");
        MASTER_PLANT.put(117980028, "แคนตาลูป");
        MASTER_PLANT.put(117980029, "เงาะ");
        MASTER_PLANT.put(117980030, "ชมพู่");
        MASTER_PLANT.put(117980031, "ดอกไม้ต่างๆ");
        MASTER_PLANT.put(117980032, "ตะไคร้");
        MASTER_PLANT.put(117980033, "แตงต่างๆ");
        MASTER_PLANT.put(117980034, "ถั่วต่างๆ");
        MASTER_PLANT.put(117980035, "ทุเรียน");
        MASTER_PLANT.put(117980036, "น้อยหน่า");
        MASTER_PLANT.put(117980037, "ใบหม่อน");
        MASTER_PLANT.put(117980038, "ปอ");
        MASTER_PLANT.put(117980039, "ปาล์มน้ำมัน");
        MASTER_PLANT.put(117980040, "ผลไม้ต่างๆ");
        MASTER_PLANT.put(117980041, "ผักกาดต่างๆ");
        MASTER_PLANT.put(117980042, "ผักต่างๆ");
        MASTER_PLANT.put(117980043, "ผักบุ้ง");
        MASTER_PLANT.put(117980044, "ฝรั่ง");
        MASTER_PLANT.put(117980045, "พริก");
        MASTER_PLANT.put(117980046, "พุทรา");
        MASTER_PLANT.put(117980047, "ฟัก");
        MASTER_PLANT.put(117980048, "มะขาม");
        MASTER_PLANT.put(117980049, "มะเขือต่างๆ");
        MASTER_PLANT.put(117980050, "มะนาว");
        MASTER_PLANT.put(117980051, "มะพร้าว");
        MASTER_PLANT.put(117980052, "มะม่วง");
        MASTER_PLANT.put(117980053, "มะม่วงหิมพานต์");
        MASTER_PLANT.put(117980054, "มะละกอ");
        MASTER_PLANT.put(117980055, "มังคุด");
        MASTER_PLANT.put(117980056, "มันต่างๆ");
        MASTER_PLANT.put(117980057, "มันสำปะหลัง");
        MASTER_PLANT.put(117980058, "ไม้ตะกู");
        MASTER_PLANT.put(117980059, "ไม้ไผ่");
        MASTER_PLANT.put(117980060, "ไม้สัก");
        MASTER_PLANT.put(117980061, "ไม้หอม(ไม้กฤษณา)");
        MASTER_PLANT.put(117980062, "ยางพารา");
        MASTER_PLANT.put(117980063, "ยาสูบ");
        MASTER_PLANT.put(117980064, "ยูคาลิปตัส");
        MASTER_PLANT.put(117980065, "ลำไย");
        MASTER_PLANT.put(117980066, "ลิ้นจี่");
        MASTER_PLANT.put(117980067, "สตอเบอรี่");
        MASTER_PLANT.put(117980068, "ส้มต่างๆ");
        MASTER_PLANT.put(117980069, "สับปะรด");
        MASTER_PLANT.put(117980070, "หญ้า");
        MASTER_PLANT.put(117980071, "หอมต่างๆ");
        MASTER_PLANT.put(117980072, "เห็ดต่างๆ");
        MASTER_PLANT.put(117980073, "องุ่น");
        MASTER_PLANT.put(117980074, "อโวคาโด");
        MASTER_PLANT.put(117980075, "อ้อย");
        MASTER_PLANT.put(117980076, "อื่นๆ");

        MASTER_INTERESTPRODUCT = new HashMap<>();
        MASTER_INTERESTPRODUCT.put(117980000, "รถแทรกเตอร์");
        MASTER_INTERESTPRODUCT.put(117980001, "รถเกี่ยวนวดข้าว");
        MASTER_INTERESTPRODUCT.put(117980002, "รถดำนา");
        MASTER_INTERESTPRODUCT.put(117980003, "รถขุด");
        MASTER_INTERESTPRODUCT.put(117980007, "ยังไม่สนใจ");

        MASTER_LANDOWNER = new HashMap<>();
        MASTER_LANDOWNER.put(117980000, "ทำการเกษตรในที่ดินเช่า");
        MASTER_LANDOWNER.put(117980001, "ทำการเกษตรในที่ดินญาติ");
        MASTER_LANDOWNER.put(117980002, "ทำเกษตรในที่ดินตัวเอง+ทำเกษตรในที่ดินญาติ");
        MASTER_LANDOWNER.put(117980003, "ทำการเกษตรในที่ดินญาติ + ที่ดินเช่า");
        MASTER_LANDOWNER.put(117980004, "ทำการเกษตรในที่ดินตนเอง");
        MASTER_LANDOWNER.put(117980005, "ทำการเกษตรในที่ดินตนเอง + ที่ดินเช่า");
        MASTER_LANDOWNER.put(117980006, "ทำเกษตรในที่ดินตัวเอง+ทำการเกษตรใรที่ดินเช่า+ทำเกษตรในที่ดินญาติ");

        MASTER_HARVESTMETHOD = new HashMap<>();
        MASTER_HARVESTMETHOD.put(117980000, "เกี่ยวมือ");
        MASTER_HARVESTMETHOD.put(117980001, "เกี่ยวมือ+จ้างรถเกี่ยวคูโบต้า");
        MASTER_HARVESTMETHOD.put(117980002, "เกี่ยวมือ+จ้างรถเกี่ยวใหญ่");
        MASTER_HARVESTMETHOD.put(117980003, "เกี่ยวมือ+จ้างรถเกี่ยวใหญ่+จ้างรถเกี่ยวคูโบต้า");
        MASTER_HARVESTMETHOD.put(117980004, "จ้างรถเกี่ยวคูโบต้า");
        MASTER_HARVESTMETHOD.put(117980005, "จ้างรถเกี่ยวใหญ่");
        MASTER_HARVESTMETHOD.put(117980006, "จ้างรถเกี่ยวใหญ่+จ้างรถเกี่ยวคูโบต้า");
        MASTER_HARVESTMETHOD.put(117980007, "รถเกี่ยวคูโบต้า(ส่วนตัว/ญาติ)");
        MASTER_HARVESTMETHOD.put(117980008, "รถเกี่ยวใหญ่(ส่วนตัว/ญาติ)");

        MASTER_FARMINGMETHOD = new HashMap<>();
        MASTER_FARMINGMETHOD.put(117980000, "หว่าน+ดำมือ+จ้างรถดำนา");
        MASTER_FARMINGMETHOD.put(117980001, "หว่าน+ดำมือ+นาโยน+จ้างรถดำนา");
        MASTER_FARMINGMETHOD.put(117980002, "นาโยน+จ้างรถดำนำ");
        MASTER_FARMINGMETHOD.put(117980003, "ดำมือ+จ้างรถดำนา");
        MASTER_FARMINGMETHOD.put(117980004, "หว่าน+จ้างรถดำนา");
        MASTER_FARMINGMETHOD.put(117980005, "หว่าน+ดำมือ");
        MASTER_FARMINGMETHOD.put(117980006, "หว่าน+นาโยน");
        MASTER_FARMINGMETHOD.put(117980007, "ดำมือ+นาโยน");
        MASTER_FARMINGMETHOD.put(117980008, "หว่าน+ดำมือ+นาโยน");
        MASTER_FARMINGMETHOD.put(117980009, "หว่าน");
        MASTER_FARMINGMETHOD.put(117980010, "ดำมือ");
        MASTER_FARMINGMETHOD.put(117980011, "นาโยน");
        MASTER_FARMINGMETHOD.put(117980012, "รถดำนาส่วนตัว(ญาติ)");
        MASTER_FARMINGMETHOD.put(117980013, "จ้างรถดำนา");
        MASTER_FARMINGMETHOD.put(117980014, "อื่นๆ");

        MASTER_FOLLOWSTATUS = new HashMap<>();
        MASTER_FOLLOWSTATUS.put(117980000, "สนใจซื้อสินค้าอย่างมาก");
        MASTER_FOLLOWSTATUS.put(117980001, "สอบถามลูกค้า");
        MASTER_FOLLOWSTATUS.put(117980002, "สนใจซื้อสินค้า");
        MASTER_FOLLOWSTATUS.put(117980003, "รอผลอนุมัติสินเชื่อ");
        MASTER_FOLLOWSTATUS.put(117980004, "วางเงินมัดจำแล้ว");

        MASTER_PURCHASESTATUS = new HashMap<>();
        MASTER_PURCHASESTATUS.put(117980000, "ปิดการขาย");
        MASTER_PURCHASESTATUS.put(117980001, "ไม่ซื้อสินค้า");
        MASTER_PURCHASESTATUS.put(117980002, "สนใจสินค้า");
        MASTER_PURCHASESTATUS.put(117980003, "รออนุมัติสินเชื่อ");

        MASTER_DECISIONPERIOD = new HashMap<>();
        MASTER_DECISIONPERIOD.put(117980000, "ภายใน 1 เดือน");
        MASTER_DECISIONPERIOD.put(117980001, "ภายใน 3 เดือน");
        MASTER_DECISIONPERIOD.put(117980002, "ภายใน 6 เดือน");
        MASTER_DECISIONPERIOD.put(117980003, "ภายใน 1 ปี");
        MASTER_DECISIONPERIOD.put(117980004, "มากกว่า 1 ปี");

        MASTER_PURCHASEREASON = new HashMap<>();
        MASTER_PURCHASEREASON.put(117980000, "โปรโมชั่น");
        MASTER_PURCHASEREASON.put(117980001, "เพื่อน/ญาติแนะนำ");
        MASTER_PURCHASEREASON.put(117980002, "ราคาถูก");
        MASTER_PURCHASEREASON.put(117980003, "สินค้าได้มาตรฐาน");
        MASTER_PURCHASEREASON.put(117980004, "มีอะไหล่พร้อม");
        MASTER_PURCHASEREASON.put(117980005, "ร้านอยู่ใกล้บ้าน");

        MASTER_NOPURCHASEREASON = new HashMap<>();
        MASTER_NOPURCHASEREASON.put(117980000, "โปรโมชั่นหรือของแถมไม่ถูกใจ");
        MASTER_NOPURCHASEREASON.put(117980001, "ไปซื้อสินค้ายี่ห้ออื่น");
        MASTER_NOPURCHASEREASON.put(117980002, "ไปซื้อสินค้าร้านอื่น");
        MASTER_NOPURCHASEREASON.put(117980003, "ไม่ผ่านประเมินสินเชื่อ SKL");
        MASTER_NOPURCHASEREASON.put(117980004, "ไม่ผ่านประเมินสินเชื่อ(ที่อื่นๆ)");
        MASTER_NOPURCHASEREASON.put(117980005, "ราคาสินค้าแพงเกินไป");
        MASTER_NOPURCHASEREASON.put(117980006, "สินค้าไม่เหมาะกับพื้นที่ใช้งาน");
        MASTER_NOPURCHASEREASON.put(117980007, "สเปคสินค้ายังไม่ได้ตามที่ต้องการ");
        MASTER_NOPURCHASEREASON.put(117980008, "สมาชิกในองค์กรหรือครอบครัวไม่สนับสนุน");
        MASTER_NOPURCHASEREASON.put(117980009, "หมดช่วงฤดูกาลใช้งาน");
        MASTER_NOPURCHASEREASON.put(117980010, "มีเงินไม่พอซื้อสินค้า");
        MASTER_NOPURCHASEREASON.put(117980011, "อื่นๆ");

        /* SUBJECT TO CHANGE */
        MASTER_LEADSTATUS = new HashMap<>();
        MASTER_LEADSTATUS.put(117980000, "New Lead");
        MASTER_LEADSTATUS.put(117980001, "Qualified");
        MASTER_LEADSTATUS.put(117980002, "Disqualified");

        MASTER_ACTIVITYTYPE = new HashMap<>();
        MASTER_ACTIVITYTYPE.put(117980000, "FollowUp Call");
        MASTER_ACTIVITYTYPE.put(117980001, "Customer Visit");
        MASTER_ACTIVITYTYPE.put(117980002, "Trial Use");

        MASTER_ACTIVITYSTATUS = new HashMap<>();
        MASTER_ACTIVITYSTATUS.put(117980000, "Open");
        MASTER_ACTIVITYSTATUS.put(117980001, "Close");
        MASTER_ACTIVITYSTATUS.put(117980002, "Complete");
    }
}
