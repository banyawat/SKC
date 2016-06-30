package com.theteus.kubota.SKCModule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

import java.util.*;

public class SKC extends Fragment{
    Fragment SKCForm01;
    Fragment SKCForm02;
    Fragment SKCForm03;

    public SKC() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skc, container, false);

        SKCForm01 = new SKCForm01();
        SKCForm02 = new SKCForm02();
        SKCForm03 = new SKCForm03();

        final CardViewPager skcView = new CardViewPager(this, view);
        skcView.addFragmentView(SKCForm01, "ข้อมูลทั่วไป");
        skcView.addFragmentView(SKCForm02, "ที่อยู่");
        skcView.addFragmentView(SKCForm03, "แบบสอบถาม");
        skcView.init(R.id.skc_form_pager, R.id.skc_nextButton);
        skcView.initFloatingButtonMethod(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (skcView.getCurrentItem()){
                    case 0:
                        SKCForm01 skcform1 = (com.theteus.kubota.SKCModule.SKCForm01) skcView.getFragment();
                        if(skcform1!=null) {
                            Bundle a;
                            a = skcform1.getAllData();
                            Log.i("bundle test", a.getString("test"));
                            Log.i("bundle test", a.getString("prefix"));

                        }
                        break;
                    case 1:
                        SKCForm02 skcform2 = (com.theteus.kubota.SKCModule.SKCForm02) skcView.getFragment();
                        break;
                    default:
                        break;
                }
                skcView.goToNextPage();
            }
        });
        return view;
    }

}
