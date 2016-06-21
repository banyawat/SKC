package com.theteus.kubota.LeadModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailGeneral extends Fragment {
    private static final String ARG_PARAM1 = "leadId";

    private String mLeadId;

    public LeadDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mLeadId = getArguments().getString("leadId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lead_detail_general, container, false);
    }
}
