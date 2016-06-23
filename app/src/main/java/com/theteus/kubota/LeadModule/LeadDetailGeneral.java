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
public class LeadDetailGeneral extends Fragment {
    private LeadInstance mLead;

    public LeadDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(LeadDetailMain.ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(LeadDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_detail_general, container, false);

        if(mLead != null) {
            ((TextView) view.findViewById(R.id.lead_detail_name_title)).setText(mLead.titleName);
            ((TextView) view.findViewById(R.id.lead_detail_first_name)).setText(mLead.firstName);
            ((TextView) view.findViewById(R.id.lead_detail_last_name)).setText(mLead.lastName);
            ((TextView) view.findViewById(R.id.lead_detail_gender)).setText(mLead.gender);
            ((TextView) view.findViewById(R.id.lead_detail_id_card)).setText(mLead.idCardNumber);
            ((TextView) view.findViewById(R.id.lead_detail_cus_type)).setText(mLead.customerType);
            ((TextView) view.findViewById(R.id.lead_detail_birthday)).setText(mLead.birthday);
            ((TextView) view.findViewById(R.id.lead_detail_phone)).setText(mLead.phone);
            ((TextView) view.findViewById(R.id.lead_detail_mobile_1)).setText(mLead.mobile1);
            ((TextView) view.findViewById(R.id.lead_detail_mobile_2)).setText(mLead.mobile2);
        }

        return view;
    }
}
