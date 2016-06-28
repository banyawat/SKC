package com.theteus.kubota.ContactModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailChassis extends Fragment {
    private ContactInstance mContact;

    public ContactDetailChassis() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_chassis, container, false);

        /*if(mLead != null) {
            ((TextView) view.findViewById(R.id.lead_detail_name_title)).setText(mLead.titleName);
            ((TextView) view.findViewById(R.id.lead_detail_first_name)).setText(mLead.firstName);
            ((TextView) view.findViewById(R.id.lead_detail_last_name)).setText(mLead.lastName);
            ((TextView) view.findViewById(R.id.lead_detail_gender)).setText(mLead.gender);
            ((TextView) view.findViewById(R.id.lead_detail_id_card)).setText(mLead.idCardNumber);
            ((TextView) view.findViewById(R.id.lead_detail_cus_type)).setText(mLead.customerType);
            ((TextView) view.findViewById(R.id.lead_detail_birthday)).setText(mLead.birthday);
            ((TextView) view.findViewById(R.id.lead_detail_phone)).setText(mLead.phone);
            ((TextView) view.findViewById(R.id.lead_detail_mobile_1)).setText(mLead.mobile1);
            ((TextView) view.findViewById(R.id.lead_detail_mobile_2)).setText(mLead.mobile2);
        }*/

        return view;
    }
}
