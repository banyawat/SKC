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
public class LeadDetailAddress extends Fragment {
    private LeadInstance mLead;

    public LeadDetailAddress() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(LeadDetailMain.ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(LeadDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_detail_address, container, false);

        if(mLead != null) {
            ((TextView) view.findViewById(R.id.lead_detail_house_number)).setText(mLead.houseNumber);
            ((TextView) view.findViewById(R.id.lead_detail_building_name)).setText(mLead.buildingName);
            ((TextView) view.findViewById(R.id.lead_detail_building_floor)).setText(mLead.buildingFloor);
            ((TextView) view.findViewById(R.id.lead_detail_building_room)).setText(mLead.buildingRoom);
            ((TextView) view.findViewById(R.id.lead_detail_house_group)).setText(mLead.houseGroup);
            ((TextView) view.findViewById(R.id.lead_detail_address_soi)).setText(mLead.soi);
            ((TextView) view.findViewById(R.id.lead_detail_address_street)).setText(mLead.street);
            ((TextView) view.findViewById(R.id.lead_detail_address_subdistrict)).setText(mLead.subdistrict);
            ((TextView) view.findViewById(R.id.lead_detail_address_district)).setText(mLead.district);
            ((TextView) view.findViewById(R.id.lead_detail_address_province)).setText(mLead.province);
            ((TextView) view.findViewById(R.id.lead_detail_address_postal)).setText(mLead.postalCode);
        }

        return view;
    }
}
