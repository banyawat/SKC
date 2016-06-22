package com.theteus.kubota.LeadModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;
public class Lead extends Fragment {
    public Lead() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead, container, false);
        CardViewPager leadView = new CardViewPager(this, view);
        leadView.addFragmentView(new LeadForm01(), "ข้อมูลทั่วไป");
        leadView.addFragmentView(new LeadForm02(), "ที่อยู่");
        leadView.addFragmentView(new LeadForm03(), "สินค้าที่สนใจ");
        leadView.init(R.id.lead_form_pager, R.id.lead_nextButton);
        return view;
    }
}
