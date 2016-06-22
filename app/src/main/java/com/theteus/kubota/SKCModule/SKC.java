package com.theteus.kubota.SKCModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

public class SKC extends Fragment {
    public SKC() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skc, container, false);
        CardViewPager skcView = new CardViewPager(this, view);
        skcView.addFragmentView(new SKCForm01(), "ข้อมูลทั่วไป");
        skcView.addFragmentView(new SKCForm02(), "ที่อยู่");
        skcView.addFragmentView(new SKCForm03(), "แบบสอบถาม");
        skcView.init(R.id.skc_form_pager, R.id.skc_nextButton);

        return view;
    }
}
