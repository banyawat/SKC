package com.theteus.kubota.ActivitiesModule;

/**
 * Created by whorangester on 6/24/16.
 */
public class ActivityInstance {
    // Master Tables
    // Primary Key and Supplements
    public String id;
    public int status;
    public String lastUser;
    // Attributes
    public int type;
    public String subject;
    public String leadId;
    public String startDate;
    public String dueDate;
    public int purchaseStatus;
    public int decisionPeriod;
    public int purchaseReason;
    public int noPurchaseReason;
    public String description;

    public ActivityInstance() {}
}
