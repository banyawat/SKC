package com.theteus.kubota.ChassisModule;

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

import com.theteus.kubota.ContactModule.DummyContactInstance;
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
        View view = inflater.inflate(R.layout.fragment_chassis_detail_main, container, false);

        final TextView title = (TextView) view.findViewById(R.id.chassis_detail_title);
        final TextView subtitle = (TextView) view.findViewById(R.id.chassis_detail_subtitle);
        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.chassis_detail_switcher);
        final TextView status = (TextView) view.findViewById(R.id.chassis_detail_status);
        final AutoCompleteTextView searchField = (AutoCompleteTextView) view.findViewById(R.id.chassis_detail_search_field);
        final InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final ImageView searchButton = (ImageView) view.findViewById(R.id.search_button);
        final ImageView qualifyButton = (ImageView) view.findViewById(R.id.approve_button);
        final ImageView disqualifyButton = (ImageView) view.findViewById(R.id.deactivate_button);
        final ImageView deleteButton = (ImageView) view.findViewById(R.id.delete_button);
        final View separator = view.findViewById(R.id.chassis_detail_button_separator);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.chassis_detail_fab);

        if(mChassis == null) {
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
            setUpCardTitle(title, subtitle, searchField, status);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyChassisInstance.KEYS);
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
                if(mChassis == null) {
                    status.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    qualifyButton.setVisibility(View.VISIBLE);
                    disqualifyButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String chassisKey = (String) parent.getItemAtPosition(position);
                mChassis = DummyChassisInstance.CHASSIS_MAP.get(DummyChassisInstance.ID_MAP.get(chassisKey));

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

    public void setUpCardTitle(TextView title, TextView subtitle, AutoCompleteTextView searchField, TextView status) {
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
                .replace(R.id.chassis_detail_content, fragment)
                .commit();
    }
}
