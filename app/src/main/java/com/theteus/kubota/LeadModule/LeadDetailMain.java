package com.theteus.kubota.LeadModule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.theteus.kubota.Home;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailMain extends Fragment {
    private LeadInstance mLead;
    public static final String ARG_PARAM1 = "leadId";
    public static final String ARG_PARAM2 = "currentTab";
    // Views
    private ViewGroup cardPanel;
    private TextView title;
    private TextView subtitle;
    private ViewSwitcher switcher;
    private AutoCompleteTextView searchField;
    private TextView status;
    private ImageButton searchButton;
    private ImageButton acceptButton;
    private ImageButton rejectButton;
    private ImageButton saveButton;
    private ImageButton deleteButton;
    private View separator;
    private InputMethodManager keyboard;
    private FloatingActionButton fab;

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
        View view = inflater.inflate(R.layout.fragment_general_detail_main, container, false);

        cardPanel = (ViewGroup) view.findViewById(R.id.card_panel);
        title = (TextView) view.findViewById(R.id.title);
        subtitle = (TextView) view.findViewById(R.id.subtitle);
        switcher = (ViewSwitcher) view.findViewById(R.id.switcher);
        searchField = (AutoCompleteTextView) view.findViewById(R.id.search_field);
        status = (TextView) view.findViewById(R.id.status);
        searchButton = (ImageButton) view.findViewById(R.id.search_button);
        acceptButton = (ImageButton) view.findViewById(R.id.accept_button);
        rejectButton = (ImageButton) view.findViewById(R.id.reject_button);
        deleteButton = (ImageButton) view.findViewById(R.id.delete_button);
        saveButton = (ImageButton) view.findViewById(R.id.save_button);
        separator = view.findViewById(R.id.button_separator);
        keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        searchField.setHint("Search Lead...");

        if(mLead == null) {
            status.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            acceptButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);

            switcher.showNext();
            searchField.requestFocus();
            keyboard.showSoftInput(searchField, 0);
        } else {
            saveButton.setVisibility(View.GONE);
            setUpCardTitle();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyLeadInstance.KEYS);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.showNext();

                if(!switcher.getNextView().isFocusable()) {
                    searchField.requestFocus();
                    keyboard.showSoftInput(searchField, 0);
                    searchButton.setImageResource(R.drawable.ic_36dp_black_highlight_off);
                } else {
                    searchField.clearFocus();
                    keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    searchButton.setImageResource(R.drawable.ic_36dp_black_search);
                }
            }
        });

        searchField.setAdapter(adapter);
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mLead == null) {
                    status.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    acceptButton.setVisibility(View.VISIBLE);
                    rejectButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String leadId = (String) parent.getItemAtPosition(position);
                mLead = DummyLeadInstance.LEAD_MAP.get(DummyLeadInstance.ID_MAP.get(leadId));

                setUpCardTitle();
                setUpContentFragment();

                searchField.clearFocus();
                switcher.showNext();
                keyboard.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
                searchButton.setImageResource(R.drawable.ic_36dp_black_search);
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
    public void setUpCardTitle() {
        String leadFullName = mLead.firstName + " " + mLead.lastName;
        String leadOwner = "Owner : " + mLead.owner;
        String leadKey = mLead.id + " : " + mLead.firstName + " " + mLead.lastName;

        title.setText(leadFullName);
        subtitle.setText(leadOwner);
        searchField.setText(leadKey);
        status.setText(Reference.MASTER_LEADSTATUS.get(mLead.status));

        switch(mLead.status) {
            case 117980000:
                status.setBackgroundResource(R.color.statusBlue);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusBlueFont));
                break;
            case 117980001:
                status.setBackgroundResource(R.color.statusGreen);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
                break;
            case 117980002:
                status.setBackgroundResource(R.color.statusRed);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                break;
            default:
                status.setBackgroundResource(R.color.statusUnknown);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
        }
    }
    public void setUpContentFragment() {
        LeadDetailContent fragment = new LeadDetailContent();
        Bundle args = new Bundle();
        if(mLead != null)
            args.putString(LeadDetailMain.ARG_PARAM1, mLead.id);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM2))
            args.putInt(ARG_PARAM2, getArguments().getInt(ARG_PARAM2));
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
