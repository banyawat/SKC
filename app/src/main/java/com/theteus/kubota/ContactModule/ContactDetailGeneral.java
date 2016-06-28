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
public class ContactDetailGeneral extends Fragment {
    private ContactInstance mContact;

    public ContactDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_general, container, false);

        if(mContact != null) {
            ((TextView) view.findViewById(R.id.contact_detail_name_title)).setText(mContact.titleName);
            ((TextView) view.findViewById(R.id.contact_detail_first_name)).setText(mContact.firstName);
            ((TextView) view.findViewById(R.id.contact_detail_last_name)).setText(mContact.lastName);
            ((TextView) view.findViewById(R.id.contact_detail_gender)).setText(mContact.gender);
            ((TextView) view.findViewById(R.id.contact_detail_id_card)).setText(mContact.idCardNumber);
            ((TextView) view.findViewById(R.id.contact_detail_cus_type)).setText(mContact.customerType);
            ((TextView) view.findViewById(R.id.contact_detail_birthday)).setText(mContact.birthday);
            ((TextView) view.findViewById(R.id.contact_detail_phone)).setText(mContact.phone);
            ((TextView) view.findViewById(R.id.contact_detail_mobile_1)).setText(mContact.mobile1);
            ((TextView) view.findViewById(R.id.contact_detail_mobile_2)).setText(mContact.mobile2);
            ((TextView) view.findViewById(R.id.contact_detail_skc_phone)).setText(mContact.skcPhone);
            ((TextView) view.findViewById(R.id.contact_detail_email)).setText(mContact.email);
            ((TextView) view.findViewById(R.id.contact_detail_fax)).setText(mContact.fax);
            ((TextView) view.findViewById(R.id.contact_detail_car)).setText(mContact.numberOfCar + " คัน");
        }

        return view;
    }
}
