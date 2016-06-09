package com.theteus.kubota.skcmodule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.R;

public class SKCpager extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager pager;
    SKCPagerAdaper adapter;


    public SKCpager() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skcpager, container, false);

        adapter = new SKCPagerAdaper(getFragmentManager(),getActivity());

        pager = (ViewPager) view.findViewById(R.id.skc_pager);
        pager.setAdapter(adapter);
        //pager.setCurrentItem(1);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skcpager, container, false);
    }

}
