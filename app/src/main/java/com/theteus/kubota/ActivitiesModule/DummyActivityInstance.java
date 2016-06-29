package com.theteus.kubota.ActivitiesModule;

import com.theteus.kubota.LeadModule.DummyLeadInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whorangester on 6/24/16.
 */
public class DummyActivityInstance {
    public static final List<ActivityInstance> ACTIVITIES = new ArrayList<>();
    public static final List<String> KEYS = new ArrayList<>();
    public static final Map<String, ActivityInstance> ACTIVITY_MAP = new HashMap<>();
    public static final Map<String, String> ID_MAP = new HashMap<>();
    public static final int COUNT = 10;

    static {
        // Primary Key
        String id[] = new String[] {"A0001", "A0002", "A0003", "A0004", "A0005", "A0006", "A0007", "A0008", "A0009", "A0010"};
        int status[] = new int[] {117980000, 117980001, 117980002, 117980000, 117980001, 117980002, 117980000, 117980001, 117980002, 117980000};
        String lastUser[] = new String[] {"someAdmin", "anyAdmin", "allAdmin", "noAdmin", "thisAdmin", "thatAdmin", "thoseAdmin", "theseAdmin", "justAdmin", "admin"};
        // Attributes
        int type[] = new int[] {117980002, 117980000, 117980001, 117980000, 117980000, 117980002, 117980000, 117980002, 117980001, 117980000};
        String subject[] = new String[] {"Trial Use", "FollowUp Call 1", "Customer Visit", "FollowUp Call 2", "FollowUp Call 1", "Trial Use", "FollowUp Call 1", "Trial Use", "Customer Visit", "FollowUp Call 1"};
        String leadId[] = new String[] {"L0001", "L0001", "L0002", "L0001", "L0003", "L0002", "L0004", "L0003", "L0004", "L0002"};
        String startDate[] = new String[] {"1 มกราคม 2558", "2 มกราคม 2558", "3 มกราคม 2558", "4 มกราคม 2558", "5 มกราคม 2558", "6 มกราคม 2558", "7 มกราคม 2558", "8 มกราคม 2558", "9 มกราคม 2558", "10 มกราคม 2558"};
        String dueDate[] = new String[] {"15 มกราคม 2558", "16 มกราคม 2558", "17 มกราคม 2558", "18 มกราคม 2558", "19 มกราคม 2558", "20 มกราคม 2558", "21 มกราคม 2558", "22 มกราคม 2558", "23 มกราคม 2558", "24 มกราคม 2558"};
        int purchaseStatus[] = new int[] {177980000, 0, 177980002, 0, 0, 177980001, 0, 177980003, 177980000, 0};
        int decisionPeriod[] = new int[] {177980004, 0, 177980003, 0, 177980002, 0, 177980001, 0, 177980000, 0};
        int purchaseReason[] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int noPurchaseReason[] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String description[] = new String[] {null, null, null, null, null, null, null, null, null, null};

        for(int i = 0; i < COUNT; i++) {
            ActivityInstance activity = new ActivityInstance();
            activity.id = id[i];
            activity.status = status[i];
            activity.lastUser = lastUser[i];
            activity.type = type[i];
            activity.subject = subject[i];
            activity.leadId = leadId[i];
            activity.startDate = startDate[i];
            activity.dueDate = dueDate[i];
            activity.purchaseStatus = purchaseStatus[i];
            activity.decisionPeriod = decisionPeriod[i];
            activity.purchaseReason = purchaseReason[i];
            activity.noPurchaseReason = noPurchaseReason[i];
            activity.description = description[i];

            ACTIVITIES.add(activity);
            ACTIVITY_MAP.put(activity.id, activity);
            KEYS.add(DummyLeadInstance.LEAD_MAP.get(activity.leadId).firstName + " " + DummyLeadInstance.LEAD_MAP.get(activity.leadId).lastName + " : " + activity.subject);
            ID_MAP.put(DummyLeadInstance.LEAD_MAP.get(activity.leadId).firstName + " " + DummyLeadInstance.LEAD_MAP.get(activity.leadId).lastName + " : " + activity.subject, activity.id);
        }
    }

    public static List<ActivityInstance> searchByLeadId(String leadId) {
        List<ActivityInstance> result = new ArrayList<>();

        for (ActivityInstance a : ACTIVITIES)
            if(a.leadId.equals(leadId)) result.add(a);

        return result;
    }
}
