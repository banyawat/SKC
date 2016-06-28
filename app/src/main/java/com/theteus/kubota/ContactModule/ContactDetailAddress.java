package com.theteus.kubota.ContactModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailAddress extends Fragment {
    private ContactInstance mContact;

    public ContactDetailAddress() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_address, container, false);

        if(mContact != null) {
            ((TextView) view.findViewById(R.id.contact_detail_house_number)).setText(mContact.houseNumber);
            ((TextView) view.findViewById(R.id.contact_detail_building_name)).setText(mContact.buildingName);
            ((TextView) view.findViewById(R.id.contact_detail_building_floor)).setText(mContact.buildingFloor);
            ((TextView) view.findViewById(R.id.contact_detail_building_room)).setText(mContact.buildingRoom);
            ((TextView) view.findViewById(R.id.contact_detail_house_group)).setText(mContact.houseGroup);
            ((TextView) view.findViewById(R.id.contact_detail_address_soi)).setText(mContact.soi);
            ((TextView) view.findViewById(R.id.contact_detail_address_street)).setText(mContact.street);
            ((TextView) view.findViewById(R.id.contact_detail_address_subdistrict)).setText(mContact.subdistrict);
            ((TextView) view.findViewById(R.id.contact_detail_address_district)).setText(mContact.district);
            ((TextView) view.findViewById(R.id.contact_detail_address_province)).setText(mContact.province);
            ((TextView) view.findViewById(R.id.contact_detail_address_postal)).setText(mContact.postalCode);
        }

        return view;
    }
}
