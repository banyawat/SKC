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
        View view = inflater.inflate(R.layout.fragment_activities_detail_content, container, false);

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
                fragment.setArguments(args);
                mPagerAdapter.addPage(fragment);
                mPagerAdapter.addPage(new Lead());
                mPagerAdapter.notifyDataSetChanged();

                home.changeMenu(3);
            }
        });

        startDate.setText(mActivity.startDate);

        dueDate.setText(mActivity.dueDate);

        if(mActivity.decisionStatus >= 0 && mActivity.decisionStatus < ActivityInstance.MASTER_DECISION_STATUS.length)
            decision.setText(ActivityInstance.MASTER_DECISION_STATUS[mActivity.decisionStatus]);
        else
            decision.setText("- - -");

        if(mActivity.decisionDuration >= 0 && mActivity.decisionDuration < ActivityInstance.MASTER_DECISION_DURATION.length)
            duration.setText(ActivityInstance.MASTER_DECISION_DURATION[mActivity.decisionDuration]);
        else
            duration.setText("- - -");

        if(mActivity.reasonToBuy >= 0 && mActivity.reasonToBuy < ActivityInstance.MASTER_REASON_TO_BUY.length)
            reasonBuy.setText(ActivityInstance.MASTER_REASON_TO_BUY[mActivity.reasonToBuy]);
        else
            reasonBuy.setText("- - -");

        if(mActivity.reasonToNotBuy >= 0 && mActivity.reasonToNotBuy < ActivityInstance.MASTER_REASON_TO_NOT_BUY.length)
            reasonNotBuy.setText(ActivityInstance.MASTER_REASON_TO_NOT_BUY[mActivity.reasonToNotBuy]);
        else
            reasonNotBuy.setText("- - -");

        if(mActivity.description != null && !mActivity.description.equals(""))
            description.setText(mActivity.description);
        else
            description.setText("- - -");

        return view;
    }
}
