package com.theteus.kubota.ActivitiesModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.Home;
import com.theteus.kubota.LeadModule.DummyLeadInstance;
import com.theteus.kubota.LeadModule.Lead;
import com.theteus.kubota.LeadModule.LeadDetailMain;
import com.theteus.kubota.LeadModule.LeadInstance;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;
import com.theteus.kubota.ScreenSlidePagerAdapter;

/**
 * Created by whorangester on 6/24/16.
 */
public class ActivitiesDetailContent extends Fragment {
    private ActivityInstance mActivity;
    private LeadInstance mLead;

    public ActivitiesDetailContent() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(getArguments().containsKey(ActivitiesDetailMain.ARG_PARAM1)) {
            mActivity = DummyActivityInstance.ACTIVITY_MAP.get(getArguments().getString(ActivitiesDetailMain.ARG_PARAM1));
            mLead = DummyLeadInstance.LEAD_MAP.get(mActivity.leadId);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        if(mActivity != null) {
            view = inflater.inflate(R.layout.fragment_activities_detail_content, container, false);

            final TextView subject = (TextView) view.findViewById(R.id.activities_detail_subject);
            final TextView regard = (TextView) view.findViewById(R.id.activities_detail_regard);
            final TextView startDate = (TextView) view.findViewById(R.id.activities_detail_start_date);
            final TextView dueDate = (TextView) view.findViewById(R.id.activities_detail_due_date);
            final TextView decision = (TextView) view.findViewById(R.id.activities_detail_decision);
            final TextView duration = (TextView) view.findViewById(R.id.activities_detail_duration);
            final TextView reasonBuy = (TextView) view.findViewById(R.id.activities_detail_buy);
            final TextView reasonNotBuy = (TextView) view.findViewById(R.id.activities_detail_not_buy);
            final TextView description = (TextView) view.findViewById(R.id.activities_detail_description);

            subject.setText(mActivity.subject);

            regard.setText(mLead.firstName + " " + mLead.lastName);
            regard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home home = (Home) getActivity();
                    ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                    mPagerAdapter.clearPage();
                    LeadDetailMain fragment = new LeadDetailMain();
                    Bundle args = new Bundle();
                    args.putString(LeadDetailMain.ARG_PARAM1, mLead.id);
                    args.putInt(LeadDetailMain.ARG_PARAM2, 4);
                    fragment.setArguments(args);
                    mPagerAdapter.addPage(fragment);
                    mPagerAdapter.addPage(new Lead());
                    mPagerAdapter.notifyDataSetChanged();

                    home.changeMenu(3);
                }
            });

            startDate.setText(mActivity.startDate);

            dueDate.setText(mActivity.dueDate);

            if (Reference.MASTER_PURCHASEREASON.containsKey(mActivity.purchaseReason))
                decision.setText(Reference.MASTER_PURCHASEREASON.get(mActivity.purchaseReason));
            else
                decision.setText("- - -");

            if (Reference.MASTER_DECISIONPERIOD.containsKey(mActivity.decisionPeriod))
                duration.setText(Reference.MASTER_DECISIONPERIOD.get(mActivity.decisionPeriod));
            else
                duration.setText("- - -");

            if (Reference.MASTER_PURCHASEREASON.containsKey(mActivity.purchaseReason))
                reasonBuy.setText(Reference.MASTER_PURCHASEREASON.get(mActivity.purchaseReason));
            else
                reasonBuy.setText("- - -");

            if (Reference.MASTER_NOPURCHASEREASON.containsKey(mActivity.noPurchaseReason))
                reasonNotBuy.setText(Reference.MASTER_NOPURCHASEREASON.get(mActivity.noPurchaseReason));
            else
                reasonNotBuy.setText("- - -");

            if (mActivity.description != null && !mActivity.description.equals(""))
                description.setText(mActivity.description);
            else
                description.setText("- - -");
        } else {
            view = inflater.inflate(R.layout.fragment_detail_blank, container, false);
        }

        return view;
    }
}
