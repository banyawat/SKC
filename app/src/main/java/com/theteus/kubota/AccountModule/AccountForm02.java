package com.theteus.kubota.AccountModule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountForm02 extends Fragment {
    private EditText mRepeat_addrnum;
    private EditText mRepeat_addrvillage;
    private EditText mRepeat_floor;
    private EditText mRepeat_room;
    private EditText mRepeat_group;
    private EditText mRepeat_alley;
    private EditText mRepeat_street;
    private EditText mRepeat_subdistrict;
    private EditText mRepeat_district;
    private EditText mRepeat_province;
    private EditText mRepeat_postalcode;

    public AccountForm02() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form02, container, false);
        findViewsById(view);
        return view;
    }

    private void findViewsById(View view){
        mRepeat_addrnum = (EditText) view.findViewById(R.id.repeat_addrnum);
        mRepeat_addrvillage = (EditText) view.findViewById(R.id.repeat_addrvillage);
        mRepeat_floor = (EditText) view.findViewById(R.id.repeat_floor);
        mRepeat_room = (EditText) view.findViewById(R.id.repeat_room);
        mRepeat_group = (EditText) view.findViewById(R.id.repeat_group);
        mRepeat_alley = (EditText) view.findViewById(R.id.repeat_alley);
        mRepeat_street = (EditText) view.findViewById(R.id.repeat_street);
        mRepeat_subdistrict = (EditText) view.findViewById(R.id.repeat_subdistrict);
        mRepeat_district = (EditText) view.findViewById(R.id.repeat_district);
        mRepeat_province = (EditText) view.findViewById(R.id.repeat_province);
        mRepeat_postalcode = (EditText) view.findViewById(R.id.repeat_postalcode);
    }

    public JSONObject getAllData(){
        JSONObject args = new JSONObject();
        String Repeat_addrnum = mRepeat_addrnum.getText().toString();
        String Repeat_addrvillage = mRepeat_addrvillage.getText().toString();
        String Repeat_floor = mRepeat_floor.getText().toString();
        String Repeat_room = mRepeat_room.getText().toString();
        String Repeat_group = mRepeat_group.getText().toString();
        String Repeat_alley = mRepeat_alley.getText().toString();
        String Repeat_street = mRepeat_street.getText().toString();
        String Repeat_subdistrict = mRepeat_subdistrict.getText().toString();
        String Repeat_district = mRepeat_district.getText().toString();
        String Repeat_province = mRepeat_province.getText().toString();
        String Repeat_postalcode = mRepeat_postalcode.getText().toString();

        String addrLine1 = "";
        if(Repeat_addrnum.length()>0)
            addrLine1 += " " + Repeat_addrnum;
        if(Repeat_addrvillage.length()>0)
            addrLine1 += " " + Repeat_addrvillage;
        if(Repeat_floor.length()>0)
            addrLine1 += " " + Repeat_floor;
        if(Repeat_room.length()>0)
            addrLine1 += " "+Repeat_room;

        String addrLine2 = "";
        if(Repeat_group.length()>0)
            addrLine2 += " "+Repeat_group;
        if(Repeat_alley.length()>0)
            addrLine2 += " "+Repeat_alley;
        if(Repeat_street.length()>0)
            addrLine2 += " "+Repeat_street;

        String addrLine3 = "";
        if(Repeat_subdistrict.length()>0)
            addrLine3 += " "+Repeat_subdistrict;
        if(Repeat_district.length()>0)
            addrLine3 += " "+Repeat_district;

        try {
            if(addrLine1.length()!=0)
                args.put(AccountSchema.ADDRESS_STREET1, addrLine1);
            if(addrLine2.length()!=0)
                args.put(AccountSchema.ADDRESS_STREET2, addrLine2);
            if(addrLine3.length()!=0)
                args.put(AccountSchema.ADDRESS_STREET3, addrLine3);
            if(Repeat_district.length()!=0)
                args.put(AccountSchema.ADDRESS_CITY, Repeat_district);
            if(Repeat_province.length()!=0)
                args.put(AccountSchema.ADDRESS_STATE, Repeat_province);
            if(Repeat_postalcode.length()!=0)
                args.put(AccountSchema.ADDRESS_POSTAL_CODE, Repeat_postalcode);
            if(Repeat_province.length()!=0)
                args.put(AccountSchema.ADDRESS_COUNTRY, Repeat_province);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return args;
    }
}
