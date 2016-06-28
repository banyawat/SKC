package com.theteus.kubota.LeadModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.ContactModule.Contact;
import com.theteus.kubota.ContactModule.ContactDetailMain;
import com.theteus.kubota.ContactModule.DummyContactInstance;
import com.theteus.kubota.Home;
import com.theteus.kubota.R;
import com.theteus.kubota.ScreenSlidePagerAdapter;

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
            if(DummyContactInstance.CONTACT_MAP.containsKey(mLead.contact)) {
                String contactName = DummyContactInstance.CONTACT_MAP.get(mLead.contact).firstName + " " + DummyContactInstance.CONTACT_MAP.get(mLead.contact).lastName;
                ((TextView) view.findViewById(R.id.lead_detail_contract)).setText(contactName);
                ((TextView) view.findViewById(R.id.lead_detail_contract)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Home home = (Home) getActivity();
                        ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                        mPagerAdapter.clearPage();
                        ContactDetailMain fragment = new ContactDetailMain();
                        Bundle args = new Bundle();
                        args.putString(ContactDetailMain.ARG_PARAM1, mLead.contact);
                        args.putInt(ContactDetailMain.ARG_PARAM2, 4);
                        fragment.setArguments(args);
                        mPagerAdapter.addPage(fragment);
                        mPagerAdapter.addPage(new Contact());
                        mPagerAdapter.notifyDataSetChanged();

                        home.changeMenu(2);
                    }
                });
            } else {
                ((TextView) view.findViewById(R.id.lead_detail_contract)).setText("- - -");
            }
        }

        return view;
    }
}
