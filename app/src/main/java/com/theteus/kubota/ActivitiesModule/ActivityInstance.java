package com.theteus.kubota.ActivitiesModule;

/**
 * Created by whorangester on 6/24/16.
 */
public class ActivityInstance {
    // Master Tables
    public static final String[] MASTER_ACTIVITIES_TYPE = new String[] {
            "FollowUp Call",
            "Customer Visit",
            "Trial Use"};
    public static final String[] MASTER_DECISION_STATUS = new String[] {
            "ปิดการขาย",
            "สนใจสินค้า",
            "รอผลอนุมัติสินเชื่อ",
            "ไม่ซื้อสินค้า"
    };
    public static final String[] MASTER_DECISION_DURATION = new String[] {
            "ภายใน 1 เดือน",
            "ภายใน 3 เดือน",
            "ภายใน 6 เดือน",
            "ภายใน 1 ปี",
            "มากกว่า 1 ปี"
    };
    public static final String[] MASTER_REASON_TO_BUY = new String[] {
            "โปรโมชั่น",
            "เพื่อน/ญาติแนะนำ",
            "ราคาถูก",
            "สินค้าได้มาตรฐาน",
            "มีอะไหล่พร้อม",
            "ร้านอยู่ใกล้บ้าน"
    };
    public static final String[] MASTER_SEASON_TO_NOT_BUY = new String[] {
            "โปรโมชั่นหรือของแถมไม่ถูกใจ",
            "ไปซื้อสินค้ายี่ห้ออื่น",
            "ไปซื้อสินค้าร้านอื่น",
            "ไม่ผ่านการประเมินสินเชื่อ SKL",
            "ไม่ผ่านการประเมินสินเชื่อ (ที่อื่นๆ)",
            "สินค้าราคาแพงเกินไป",
            "สินค้าไม่เหมาะกับพื้นที่ใช้งาน",
            "สเปคสินค้ายังไม่ได้ตามที่ต้องการ",
            "สมาชิกในองค์กรหรือครอบครัวไม่สนับสนุน",
            "หมดช่วงฤดูกาลใช้งาน",
            "มีเงินไม่พอซื้อสินค้า",
            "อื่นๆ"
    };
    // Primary Key and Supplements
    public String id;
    public boolean flag_complete;
    public String lastUser;
    // Attributes
    public int type;
    public String subject;
    public String leadId;
    public String startDate;
    public String dueDate;
    public int decisionStatus;
    public int decisionDuration;
    public int reasonToBuy;
    public int reasonToNotBuy;
    public String description;

    public ActivityInstance() {}
}
