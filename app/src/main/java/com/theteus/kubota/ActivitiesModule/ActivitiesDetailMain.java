package com.theteus.kubota.ActivitiesModule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.theteus.kubota.Home;
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

        final TextView title = (TextView) view.findViewById(R.id.activities_detail_title);
        final TextView subtitle = (TextView) view.findViewById(R.id.activities_detail_subtitle);
        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.activities_detail_switcher);
        final AutoCompleteTextView searchField = (AutoCompleteTextView) view.findViewById(R.id.activities_detail_search_field);
        final TextView status = (TextView) view.findViewById(R.id.activities_detail_status);
        final InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final ImageView searchButton = (ImageView) view.findViewById(R.id.search_button);
        final ImageView completeButton = (ImageView) view.findViewById(R.id.complete_button);
        final ImageView closeButton = (ImageView) view.findViewById(R.id.close_button);
        final ImageView deleteButton = (ImageView) view.findViewById(R.id.delete_button);
        final View separator = view.findViewById(R.id.activities_detail_button_separator);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.activities_detail_fab);

        if(mActivity == null) {
            status.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            completeButton.setVisibility(View.GONE);
            closeButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);

            switcher.showNext();
            searchField.requestFocus();
            keyboard.showSoftInput(searchField, 0);
        }
        else {
            setUpCardTitle(title, subtitle, searchField, status);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyActivityInstance.KEYS);
        searchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switcher.showNext();

                if(!switcher.getNextView().isFocusable()) {
                    searchField.requestFocus();
                    keyboard.showSoftInput(searchField, 0);
                    searchButton.setImageResource(R.drawable.ic_48dp_black_highlight_off);
                } else {
                    searchField.clearFocus();
                    keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    searchButton.setImageResource(R.drawable.ic_48dp_black_search);
                }
                return false;
            }
        });

        searchField.setAdapter(adapter);
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mActivity == null) {
                    status.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    completeButton.setVisibility(View.VISIBLE);
                    closeButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String activityId = (String) parent.getItemAtPosition(position);
                mActivity = DummyActivityInstance.ACTIVITY_MAP.get(DummyActivityInstance.ID_MAP.get(activityId));
                mLead = DummyLeadInstance.LEAD_MAP.get(mActivity.leadId);

                setUpCardTitle(title, subtitle, searchField, status);
                setUpContentFragment();

                searchField.clearFocus();
                switcher.showNext();
                keyboard.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
                searchButton.setImageResource(R.drawable.ic_48dp_black_search);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home) getActivity()).getmPager().setCurrentItem(1);
            }
        });

        setUpContentFragment();

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
