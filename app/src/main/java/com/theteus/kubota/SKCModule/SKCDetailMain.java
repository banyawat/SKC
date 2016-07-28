package com.theteus.kubota.SKCModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/30/16.
 */
public class SKCDetailMain extends Fragment {
    public static final String ARG_PARAM1 = "skcId";
    public static final String ARG_PARAM2 = "currentTab";

    public SKCDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            //mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_detail_main, container, false);
        return view;
    }
}
