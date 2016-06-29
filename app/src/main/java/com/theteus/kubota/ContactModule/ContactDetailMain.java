package com.theteus.kubota.ContactModule;

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
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailMain extends Fragment {
    private ContactInstance mContact;
    public static final String ARG_PARAM1 = "contactId";
    public static final String ARG_PARAM2 = "currentTab";

    public ContactDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_main, container, false);

        final TextView title = (TextView) view.findViewById(R.id.contact_detail_title);
        final TextView owner = (TextView) view.findViewById(R.id.contact_detail_subtitle);
        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.contact_detail_switcher);
        final TextView status = (TextView) view.findViewById(R.id.contact_detail_status);
        final AutoCompleteTextView searchField = (AutoCompleteTextView) view.findViewById(R.id.contact_detail_search_field);
        final InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final ImageView searchButton = (ImageView) view.findViewById(R.id.search_button);
        final ImageView qualifyButton = (ImageView) view.findViewById(R.id.approve_button);
        final ImageView disqualifyButton = (ImageView) view.findViewById(R.id.deactivate_button);
        final ImageView deleteButton = (ImageView) view.findViewById(R.id.delete_button);
        final View separator = view.findViewById(R.id.contact_detail_button_separator);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.contact_detail_fab);

        if(mContact == null) {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyContactInstance.KEYS);
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
                if(mContact == null) {
                    status.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.VISIBLE);
                    qualifyButton.setVisibility(View.VISIBLE);
                    disqualifyButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String leadId = (String) parent.getItemAtPosition(position);
                mContact = DummyContactInstance.CONTACT_MAP.get(DummyContactInstance.ID_MAP.get(leadId));

                setUpCardTitle(title, owner, searchField, status);
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
    public void setUpCardTitle(TextView titleView, TextView ownerView, AutoCompleteTextView searchField, TextView statusView) {
        String contactFullName = mContact.firstName + " " + mContact.lastName;
        String contactOwner = "Owner : " + mContact.owner;
        String contactKey = mContact.id + " : " + mContact.firstName + " " + mContact.lastName;

        titleView.setText(contactFullName);
        ownerView.setText(contactOwner);
        searchField.setText(contactKey);

        if(mContact.flagWelcomeUpdate) {
            statusView.setText("W. Update On");
            statusView.setBackgroundResource(R.color.statusGreen);
            statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
        } else {
            statusView.setText("W. Update Off");
            statusView.setBackgroundResource(R.color.statusRed);
            statusView.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
        }
    }

    public void setUpContentFragment() {
        ContactDetailContent fragment = new ContactDetailContent();
        Bundle args = new Bundle();
        if(mContact != null)
            args.putString(ARG_PARAM1, mContact.id);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM2))
            args.putInt(ARG_PARAM2, getArguments().getInt(ARG_PARAM2));
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contact_detail_content, fragment)
                .commit();
    }
}
