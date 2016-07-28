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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.theteus.kubota.Home;
import com.theteus.kubota.R;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailMain extends Fragment {
    private ContactInstance mContact;
    public static final String ARG_PARAM1 = "contactId";
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

        searchField.setHint("Search Contact...");

        if(mContact == null) {
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, DummyContactInstance.KEYS);
        searchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                    acceptButton.setVisibility(View.VISIBLE);
                    rejectButton.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                }

                String leadId = (String) parent.getItemAtPosition(position);
                mContact = DummyContactInstance.CONTACT_MAP.get(DummyContactInstance.ID_MAP.get(leadId));

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
        String contactFullName = mContact.firstName + " " + mContact.lastName;
        String contactOwner = "Owner : " + mContact.owner;
        String contactKey = mContact.id + " : " + mContact.firstName + " " + mContact.lastName;

        title.setText(contactFullName);
        subtitle.setText(contactOwner);
        searchField.setText(contactKey);

        if(mContact.flagWelcomeUpdate) {
            status.setText("W. Update On");
            status.setBackgroundResource(R.color.statusGreen);
            status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
        } else {
            status.setText("W. Update Off");
            status.setBackgroundResource(R.color.statusRed);
            status.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
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
                .replace(R.id.content, fragment)
                .commit();
    }
}
