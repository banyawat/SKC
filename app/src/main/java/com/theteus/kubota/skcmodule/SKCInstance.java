package com.theteus.kubota.skcmodule;

public class SKCInstance {
    private String name;
    private String tel;
    private String email;

    public SKCInstance(String name, String tel, String email){
        this.name = name;
        this.tel = tel;
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getTel(){
        return this.tel;
    }

    public String getEmail(){
        return this.email;
    }

}
