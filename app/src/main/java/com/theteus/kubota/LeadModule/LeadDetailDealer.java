package com.theteus.kubota.LeadModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailDealer extends Fragment {
    private LeadInstance mLead;

    public LeadDetailDealer() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(LeadDetailMain.ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(LeadDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_detail_dealer, container, false);

        if(mLead != null) {
            ((TextView) view.findViewById(R.id.lead_detail_shop_name)).setText(mLead.shopName);
            ((TextView) view.findViewById(R.id.lead_detail_record_date)).setText(mLead.recordDate);
            ((TextView) view.findViewById(R.id.lead_detail_event_date)).setText(mLead.eventDate);
            ((TextView) view.findViewById(R.id.lead_detail_code)).setText(mLead.code);
            ((TextView) view.findViewById(R.id.lead_detail_event_name)).setText(mLead.eventName);
            ((TextView) view.findViewById(R.id.lead_detail_sales_name)).setText(mLead.salesName);
            ((TextView) view.findViewById(R.id.lead_detail_event_location)).setText(mLead.eventLocation);
            ((TextView) view.findViewById(R.id.lead_detail_contract)).setText(mLead.contact);
        }

        return view;
    }
}
