package com.theteus.kubota.ActivitiesModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.CardViewPager;
import com.theteus.kubota.R;

import java.util.HashMap;

public class Activities extends Fragment {

    public Activities() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);

        final ActivitiesForm01 act01 = new ActivitiesForm01();
        CardViewPager activityView = new CardViewPager(this, view);
        activityView.addFragmentView(act01, "Activity");
        activityView.init(R.id.activities_form_pager, R.id.activities_nextButton);
        activityView.initFloatingButtonMethod(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> act01Result = act01.getAndValidateEntry();
                if(act01Result==null){
                    Log.i("FUCK", "FUCK");
                }
            }
        });

        return view;
    }
}
