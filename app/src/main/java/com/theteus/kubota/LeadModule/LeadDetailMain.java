package com.theteus.kubota.LeadModule;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailMain extends Fragment {
    private LeadInstance mLead;
    public static final String ARG_PARAM1 = "leadId";

    public LeadDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lead_detail_main, container, false);
        final TextView title = (TextView) view.findViewById(R.id.lead_detail_title);
        final TextView owner = (TextView) view.findViewById(R.id.lead_detail_subtitle);
        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.lead_detail_switcher);
        final TextView status = (TextView) view.findViewById(R.id.lead_detail_status);
        final AutoCompleteTextView searchField = (AutoCompleteTextView) view.findViewById(R.id.lead_detail_search_field);
        final InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final View searchButton = view.findViewById(R.id.search_button);
        final View qualifyButton = view.findViewById(R.id.qualify_button);
        final View disqualifyButton = view.findViewById(R.id.disqualify_button);
        final View deleteButton = view.findViewById(R.id.delete_button);
        final View separator = view.findViewById(R.id.lead_detail_button_separator);

        if(mLead == null) {
            status.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            qualifyButton.setVisibility(View.GONE);
            disqualifyButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);

            switcher.showNext();
            searchField.requestFocus();
            keyboard.showSoftInput(searchField, 0);
        } else {
            setUpCardTitle(title, owner, searchField, status);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyLeadInstance.KEYS);
        (view.findViewById(R.id.search_button)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switcher.showNext();

                if(!switcher.getNextView().isFocusable()) {
                    searchField.requestFocus();
                    keyboard.showSoftInput(searchField, 0);
                } else {
                    searchField.clearFocus();
                    keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

        searchField.setAdapter(adapter);
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mLead == null) {
                    status.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    qualifyButton.setVisibility(View.VISIBLE);
                    disqualifyButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String leadId = (String) parent.getItemAtPosition(position);
                mLead = DummyLeadInstance.LEAD_MAP.get(DummyLeadInstance.ID_MAP.get(leadId));

                setUpCardTitle(title, owner, searchField, status);
                setUpContentFragment();

                searchField.clearFocus();
                switcher.showNext();
                keyboard.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
            }
        });

        setUpContentFragment();

        return view;
    }
    public void setUpCardTitle(TextView titleView, TextView ownerView, AutoCompleteTextView searchField, TextView statusView) {
        String leadFullName = mLead.firstName + " " + mLead.lastName;
        String leadOwner = "Owner : " + mLead.owner;
        String leadKey = mLead.id + " : " + mLead.firstName + " " + mLead.lastName;

        titleView.setText(leadFullName);
        ownerView.setText(leadOwner);
        searchField.setText(leadKey);
        statusView.setText(mLead.status);

        if(mLead.status.equals("New Lead")) {
            statusView.setBackgroundResource(R.color.statusBlue);
            statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusBlueFont));
        } else if(mLead.status.equals("Qualified")) {
            statusView.setBackgroundResource(R.color.statusGreen);
            statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
        }
        else if(mLead.status.equals("Disqualified")) {
            statusView.setBackgroundResource(R.color.statusRed);
            statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
        }
        else {
            statusView.setBackgroundResource(R.color.statusUnknown);
            statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
        }
    }
    public void setUpContentFragment() {
        LeadDetailContent fragment = new LeadDetailContent();
        Bundle args = new Bundle();
        if(mLead != null) args.putString(LeadDetailMain.ARG_PARAM1, mLead.id);
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.lead_detail_content, fragment)
                .commit();
    }
}
