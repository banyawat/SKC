package com.theteus.kubota.ChassisModule;

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
 * Created by whorangester on 6/29/16.
 */
public class ChassisDetailMain extends Fragment {
    ChassisInstance mChassis;
    public static final String ARG_PARAM1 = "chassisNo";
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

    public ChassisDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            mChassis = DummyChassisInstance.CHASSIS_MAP.get(getArguments().getString(ARG_PARAM1));
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

        searchField.setHint("Search Chassis...");

        if(mChassis == null) {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyChassisInstance.KEYS);
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
                if(mChassis == null) {
                    status.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    acceptButton.setVisibility(View.VISIBLE);
                    rejectButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String chassisKey = (String) parent.getItemAtPosition(position);
                mChassis = DummyChassisInstance.CHASSIS_MAP.get(DummyChassisInstance.ID_MAP.get(chassisKey));

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
        title.setText(mChassis.chassisNumber);
        subtitle.setText(Reference.MASTER_PRODUCTTYPE.get(mChassis.productType));
        searchField.setText(mChassis.chassisNumber + " : " + Reference.MASTER_PRODUCTTYPE.get(mChassis.productType));
        status.setText(Reference.MASTER_PRODUCTSTATUS.get(mChassis.productStatus));

        switch(mChassis.productStatus) {
            case 177980000:
            case 177980001:
            case 177980002:
                status.setBackgroundResource(R.color.statusGreen);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
                break;
            case 177980003:
                status.setBackgroundResource(R.color.statusRed);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                break;
            default:
                status.setBackgroundResource(R.color.statusUnknown);
                status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
        }
    }

    public void setUpContentFragment() {
        ChassisDetailContent fragment = new ChassisDetailContent();
        Bundle args = new Bundle();
        if(mChassis != null)
            args.putString(ChassisDetailMain.ARG_PARAM1, mChassis.chassisNumber);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM2))
            args.putInt(ARG_PARAM2, getArguments().getInt(ARG_PARAM2));
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
