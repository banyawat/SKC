package com.theteus.kubota.SKCModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SKC extends Fragment{
    private CardViewPager skcView;
    private Fragment SKCForm01;
    private Fragment SKCForm02;
    private Fragment SKCForm03;

    Bundle form1Bundle;
    ArrayList<HashMap<String, String>> form2Bundle, form3Bundle;
    int lastPage=0;

    public SKC() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skc, container, false);

        SKCForm01 = new SKCForm01();
        SKCForm02 = new SKCForm02();
        SKCForm03 = new SKCForm03();

        skcView = new CardViewPager(this, view);
        skcView.addFragmentView(SKCForm01, "ข้อมูลทั่วไป");
        skcView.addFragmentView(SKCForm02, "ที่อยู่");
        skcView.addFragmentView(SKCForm03, "แบบสอบถาม");
        skcView.init(R.id.skc_form_pager, R.id.skc_nextButton);
        skcView.initFloatingButtonMethod(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("is last page", String.valueOf(skcView.isLastPage()));
                skcView.goToNextPage();
                if(skcView.isLastPage()){
                    postInformation();
                }
            }
        });

        skcView.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                retrieveData(lastPage);
                lastPage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

    private void retrieveData(int position){
        switch (position){
            case 0:
                SKCForm01 skcform1 = (com.theteus.kubota.SKCModule.SKCForm01) skcView.getFragment(0);
                if (skcform1 != null) {
                    form1Bundle = skcform1.getAllData();
                }
                break;
            case 1:
                SKCForm02 skcform2 = (com.theteus.kubota.SKCModule.SKCForm02) skcView.getFragment(1);
                if(skcform2 != null){
                    form2Bundle = skcform2.getAllData();
                }
                break;
            default:
                SKCForm03 skcform3 = (com.theteus.kubota.SKCModule.SKCForm03) skcView.getFragment(2);
                if(skcform3!=null){
                    form3Bundle = skcform3.getAllData();
                }
                break;
        }
    }

    private void postInformation(){
        //post all information to server
    }

}
