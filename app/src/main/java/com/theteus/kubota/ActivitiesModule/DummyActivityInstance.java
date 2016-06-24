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
        int status[] = new int[] {0, 1, 2, 0, 1, 2, 0, 1, 2, 0};
        String lastUser[] = new String[] {"someAdmin", "anyAdmin", "allAdmin", "noAdmin", "thisAdmin", "thatAdmin", "thoseAdmin", "theseAdmin", "justAdmin", "admin"};
        // Attributes
        int type[] = new int[] {2, 0, 1, 0, 0, 2, 0, 2, 1, 0};
        String subject[] = new String[] {"Trial Use", "FollowUp Call 1", "Customer Visit", "FollowUp Call 2", "FollowUp Call 1", "Trial Use", "FollowUp Call 1", "Trial Use", "Customer Visit", "FollowUp Call 1"};
        String leadId[] = new String[] {"L0001", "L0001", "L0002", "L0001", "L0003", "L0002", "L0004", "L0003", "L0004", "L0002"};
        String startDate[] = new String[] {"1 มกราคม 2558", "2 มกราคม 2558", "3 มกราคม 2558", "4 มกราคม 2558", "5 มกราคม 2558", "6 มกราคม 2558", "7 มกราคม 2558", "8 มกราคม 2558", "9 มกราคม 2558", "10 มกราคม 2558"};
        String dueDate[] = new String[] {"15 มกราคม 2558", "16 มกราคม 2558", "17 มกราคม 2558", "18 มกราคม 2558", "19 มกราคม 2558", "20 มกราคม 2558", "21 มกราคม 2558", "22 มกราคม 2558", "23 มกราคม 2558", "24 มกราคม 2558"};
        int decisionStatus[] = new int[] {0, -1, 2, -1, -1, 1, -1, 3, 0, -1};
        int decisionDuration[] = new int[] {4, -1, 3, -1, 2, -1, 1, -1, 0, -1};
        int reasonToBuy[] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        int reasonToNotBuy[] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
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
            activity.decisionStatus = decisionStatus[i];
            activity.decisionDuration = decisionDuration[i];
            activity.reasonToBuy = reasonToBuy[i];
            activity.reasonToNotBuy = reasonToNotBuy[i];
            activity.description = description[i];

            ACTIVITIES.add(activity);
            ACTIVITY_MAP.put(activity.id, activity);
        }
    }

    public static List<ActivityInstance> searchByLeadId(String leadId) {
        List<ActivityInstance> result = new ArrayList<>();

        for (ActivityInstance a : ACTIVITIES)
            if(a.leadId.equals(leadId)) result.add(a);

        return result;
    }
}
