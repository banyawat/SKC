package com.theteus.kubota.AccountModule;

public class AccountSchema {
    public static final String ENTITY_NAME = "Account";
    public static final String IDENTIFIER = "AccountId";
    // General
    public static final String ACCOUNT_NAME = "Name";
    public static final String MAIN_PHONE = "Telephone1";
    public static final String FAX = "Fax";
    public static final String WEBSITE = "WebSiteURL";
    public static final String PARENT_ACCOUNT = "ParentAccountId";
    public static final String TICKER_SYMBOL = "TickerSymbol";
    // Address
    public static final String ADDRESS_STREET1 = "Address1_Line1";
    public static final String ADDRESS_STREET2 = "Address1_Line2";
    public static final String ADDRESS_STREET3 = "Address1_Line3";
    public static final String ADDRESS_CITY = "Address1_City";
    public static final String ADDRESS_STATE = "Address1_StateOrProvince";
    public static final String ADDRESS_POSTAL_CODE = "Address1_PostalCode";
    public static final String ADDRESS_COUNTRY = "Address1_Country";
    // Details
    public static final String INDUSTRY = "IndustryCode";
    public static final String SIC_CODE = "SIC";
    public static final String OWNERSHIP = "OwnershipCode";
    public static final String DESCRIPTION = "Description";
    public static final String ORIGINATING_LEAD = "OriginatingLeadId";
    public static final String LAST_CAMPAIGN_DATE = "LastUsedInCampaign";
    public static final String MARKETING_MATERIAL = "DoNotSendMM";
    public static final String CONTACT_METHOD = "PreferredContactMethodCode";
    public static final String CONTACT_PREFERENCE_EMAIL = "DoNotEMail";
    public static final String CONTACT_PREFERENCE_BULK_EMAIL = "DoNotBulkEMail";
    public static final String CONTACT_PREFERENCE_PHONE = "DoNotPhone";
    public static final String CONTACT_PREFERENCE_FAX = "DoNotFax";
    public static final String CONTACT_PREFERENCE_MAIL = "DoNotPostalMail";
    public static final String CURRENCY = "TransactionCurrencyId";
    public static final String CREDIT_LIMIT = "CreditLimit";
    public static final String CREDIT_HOLD = "CreditOnHold";
    public static final String PAYMENT_TERMS = "PaymentTermsCode";
    public static final String SHIPPING_METHOD = "Address1_ShippingMethodCode";
    public static final String FREIGHT_TERMS = "Address1_FreightTermsCode";
}
