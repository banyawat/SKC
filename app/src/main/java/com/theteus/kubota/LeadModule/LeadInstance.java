package com.theteus.kubota.LeadModule;

public class LeadInstance {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String registerDate;
    private String accountId;

    public LeadInstance(String id, String name, String phone, String email, String registerDate, String accountId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.registerDate = registerDate;
        this.accountId = accountId;
    }
    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getPhone() { return this.phone; }
    public String getEmail() { return this.email; }
    public String getRegisterDate() { return this.registerDate; }
    public String getAccountId() { return this.accountId; }
}
