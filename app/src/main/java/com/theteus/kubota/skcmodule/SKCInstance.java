package com.theteus.kubota.skcmodule;

/**
 * Created by user on 6/9/2016.
 */
public class SKCInstance {
    private boolean select;
    private String name;
    private String tel;
    private String email;

    public SKCInstance(String name, String tel, String email){
        this.name = name;
        this.tel = tel;
        this.email = email;
        select = false;
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

    public void changeSelect(){
        this.select = !this.select;
    }

    public boolean getSelect(){
        return this.select;
    }
}
