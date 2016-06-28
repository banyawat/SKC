package com.theteus.kubota.ActivitiesModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.theteus.kubota.LeadModule.DummyLeadInstance;
import com.theteus.kubota.LeadModule.LeadInstance;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

/**
 * Created by whorangester on 6/24/16.
 */
public class ActivitiesDetailMain extends Fragment {
    private ActivityInstance mActivity;
    private LeadInstance mLead;
    public static final String ARG_PARAM1 = "activityId";

    public ActivitiesDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            mActivity = DummyActivityInstance.ACTIVITY_MAP.get(getArguments().getString(ARG_PARAM1));
            mLead = DummyLeadInstance.LEAD_MAP.get(mActivity.leadId);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities_detail_main, container, false);

        if(mActivity != null) {
            final TextView title = (TextView) view.findViewById(R.id.activities_detail_title);
            final TextView subtitle = (TextView) view.findViewById(R.id.activities_detail_subtitle);
            final AutoCompleteTextView searchField = (AutoCompleteTextView) view.findViewById(R.id.activities_detail_search_field);
            final TextView status = (TextView) view.findViewById(R.id.activities_detail_status);

            setUpCardTitle(title, subtitle, searchField, status);
            setUpContentFragment();
        }

        return view;
    }

    public void setUpCardTitle(TextView titleView, TextView subtitleView, AutoCompleteTextView searchField, TextView statusView) {
        String activitySubject = mActivity.subject;
        String leadName = "Type : " + Reference.MASTER_ACTIVITYTYPE.get(mActivity.type);
        String activityKey = mActivity.subject + " : " + mLead.firstName + " " + mLead.lastName;

        titleView.setText(activitySubject);
        subtitleView.setText(leadName);
        searchField.setText(activityKey);
        statusView.setText(Reference.MASTER_ACTIVITYSTATUS.get(mActivity.status));

        switch(mActivity.status) {
            case 117980000:
                statusView.setBackgroundResource(R.color.statusGreen);
                statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
                break;
            case 117980001:
                statusView.setBackgroundResource(R.color.statusRed);
                statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                break;
            case 117980002:
                statusView.setBackgroundResource(R.color.statusBlue);
                statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusBlueFont));
                break;
            default:
                statusView.setBackgroundResource(R.color.statusUnknown);
                statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
                break;
        }
    }
    public void setUpContentFragment() {
        ActivitiesDetailContent fragment = new ActivitiesDetailContent();
        Bundle args = new Bundle();
        if(mActivity != null) args.putString(ActivitiesDetailMain.ARG_PARAM1, mActivity.id);
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activities_detail_content, fragment)
                .commit();
    }
}
