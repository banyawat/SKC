package com.theteus.kubota.SKCModule;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;
import com.theteus.kubota.RepeatAddressForm;

import java.util.ArrayList;
import java.util.HashMap;

public class SKCForm02 extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    Button addrMenu1, addrMenu2, addrMenu3;
    EditText addrNoEdit, addrNameEdit, floorEdit, roomEdit,
            groupEdit, alleyEdit, streetEdit, subDistrictEdit,
            districtEdit, provinceEdit, postalEdit;
    ArrayList<addressInstance> addressList;
    int pageID=0;

    public SKCForm02() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skcform02, container, false);
        addressList = new ArrayList<addressInstance>();
        addressList.add(new addressInstance());
        addressList.add(new addressInstance());
        addressList.add(new addressInstance());
        findViewsById(view);
        initButton();

        return view;
    }

    private void findViewsById(View view){
        addrMenu1 = (Button) view.findViewById(R.id.skc_form2_menu01);
        addrMenu2 = (Button) view.findViewById(R.id.skc_form2_menu02);
        addrMenu3 = (Button) view.findViewById(R.id.skc_form2_menu03);
        addrNoEdit = (EditText) view.findViewById(R.id.repeat_addrnum);
        addrNameEdit = (EditText) view.findViewById(R.id.repeat_addrvillage);
        floorEdit = (EditText) view.findViewById(R.id.repeat_floor);
        roomEdit = (EditText) view.findViewById(R.id.repeat_room);
        groupEdit = (EditText) view.findViewById(R.id.repeat_group);
        alleyEdit = (EditText) view.findViewById(R.id.repeat_alley);
        streetEdit = (EditText) view.findViewById(R.id.repeat_street);
        subDistrictEdit = (EditText) view.findViewById(R.id.repeat_subdistrict);
        districtEdit = (EditText) view.findViewById(R.id.repeat_district);
        provinceEdit = (EditText) view.findViewById(R.id.repeat_province);
        postalEdit = (EditText) view.findViewById(R.id.repeat_postalcode);
    }

    private void initButton(){
        addrMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageID){
                    case 0:
                        return;
                    case 1:
                        saveStatusEditText();
                        break;
                    case 2:
                        saveStatusEditText();
                        break;
                    default: break;
                }
                pageID=0;
                restoreEditText();
            }
        });

        addrMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAGGY", "Teststesetset page"+pageID);
                switch (pageID){
                    case 0:
                        saveStatusEditText();
                        break;
                    case 1:
                        return;
                    case 2:
                        saveStatusEditText();
                        break;
                    default: break;
                }
                pageID=1;
                restoreEditText();
            }
        });

        addrMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageID){
                    case 0:
                        saveStatusEditText();
                        restoreEditText();
                        break;
                    case 1:
                        saveStatusEditText();
                        restoreEditText();
                        break;
                    case 2:
                        return;
                    default: break;
                }
                pageID=2;
                restoreEditText();
            }
        });
    }

    private void saveStatusEditText(){
        saveEditText();
        clearEditText();
    }

    private void restoreEditText(){
        addressList.get(pageID).getAddressField(addrNoEdit, addrNameEdit, floorEdit, roomEdit,
                groupEdit, alleyEdit, streetEdit, subDistrictEdit,
                districtEdit, provinceEdit, postalEdit);
        addrNoEdit.requestFocus();
    }

    private void saveEditText(){
        addressList.get(pageID).setAddress(addrNoEdit, addrNameEdit, floorEdit, roomEdit,
                groupEdit, alleyEdit, streetEdit, subDistrictEdit,
                districtEdit, provinceEdit, postalEdit);
    }
    
    private void clearEditText(){
        addrNoEdit.setText("");
        addrNameEdit.setText("");
        floorEdit.setText("");
        roomEdit.setText("");
        groupEdit.setText("");
        alleyEdit.setText("");
        streetEdit.setText("");
        subDistrictEdit.setText("");
        districtEdit.setText("");
        provinceEdit.setText("");
        postalEdit.setText("");

        addrNoEdit.setError(null);
        addrNameEdit.setError(null);
        floorEdit.setError(null);
        roomEdit.setError(null);
        groupEdit.setError(null);
        alleyEdit.setError(null);
        streetEdit.setError(null);
        subDistrictEdit.setError(null);
        districtEdit.setError(null);
        provinceEdit.setError(null);
        postalEdit.setError(null);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {

    }

    private class addressInstance{
        private HashMap<String, String> addressMap;

        public addressInstance(){
            addressMap = new HashMap<String, String>();
        }
        public void setAddress(EditText addressNoEdit,EditText addressNameEdit,EditText floorEdit,EditText roomEdit,
                               EditText groupEdit,EditText alleyEdit,EditText streetEdit,EditText subDistrictEdit,
                               EditText districtEdit,EditText provinceEdit,EditText postalEdit){
            addressMap.put("addressNo", addressNoEdit.getText().toString());
            addressMap.put("addressName", addressNameEdit.getText().toString());
            addressMap.put("Floor", floorEdit.getText().toString());
            addressMap.put("Room", roomEdit.getText().toString());
            addressMap.put("Group", groupEdit.getText().toString());
            addressMap.put("Alley", alleyEdit.getText().toString());
            addressMap.put("Street", streetEdit.getText().toString());
            addressMap.put("SubDistrict", subDistrictEdit.getText().toString());
            addressMap.put("District", districtEdit.getText().toString());
            addressMap.put("Province", provinceEdit.getText().toString());
            addressMap.put("Postal", postalEdit.getText().toString());
        }

        public void getAddressField(EditText addrNoEdit,EditText addrNameEdit,EditText floorEdit,EditText roomEdit,
                                    EditText groupEdit,EditText alleyEdit,EditText streetEdit,EditText subDistrictEdit,
                                    EditText districtEdit,EditText provinceEdit,EditText postalEdit){
            addrNoEdit.setText(addressMap.get("addressNo"));
            addrNameEdit.setText(addressMap.get("addressName"));
            floorEdit.setText(addressMap.get("Floor"));
            roomEdit.setText(addressMap.get("Room"));
            groupEdit.setText(addressMap.get("Group"));
            alleyEdit.setText(addressMap.get("Alley"));
            streetEdit.setText(addressMap.get("Street"));
            subDistrictEdit.setText(addressMap.get("SubDistrict"));
            districtEdit.setText(addressMap.get("District"));
            provinceEdit.setText(addressMap.get("Province"));
            postalEdit.setText(addressMap.get("Postal"));
        }
    }
}
