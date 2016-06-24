package com.theteus.kubota.ContactModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

public class Contact extends Fragment {
    public Contact() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        CardViewPager contactView = new CardViewPager(this, view);
        contactView.addFragmentView(new ContactForm01(), "ข้อมูลทั่วไป");
        contactView.addFragmentView(new ContactForm02(), "ที่อยู่");
        contactView.addFragmentView(new ContactForm03(), "แบบสอบถาม");
        contactView.init(R.id.contact_form_pager, R.id.contact_nextButton);

        return view;
    }
}
