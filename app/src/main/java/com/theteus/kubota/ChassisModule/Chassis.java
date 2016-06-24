package com.theteus.kubota.ChassisModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

public class Chassis extends Fragment {
    public Chassis() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis, container, false);

        CardViewPager chassisView = new CardViewPager(this, view);
        chassisView.addFragmentView(new ChassisForm01(), "ข้อมูลทั่วไป");
        chassisView.addFragmentView(new ChassisForm02(), "สัญญา");
        chassisView.addFragmentView(new ChassisForm03(), "วีไอพีการ์ด");

        chassisView.init(R.id.chassis_form_pager, R.id.chassis_nextButton);

        return view;
    }
}
