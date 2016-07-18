package com.theteus.kubota.AccountModule;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.theteus.kubota.NtlmConnection;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDetailMain extends Fragment{
    // Constant
    public static final String ARG_PARAM1 = "accountId";
    public static final String ARG_PARAM2 = "accountInstance";
    public static final String ARG_PARAM3 = "currentTab";
    // Contents
    NtlmConnection connection;
    public String mAccountString;
    private JSONObject mAccount;
    private List<String> mAccountNameList;
    private Map<String, String> mAccountIdMap;
    // Views
    private TextView title;
    private TextView subtitle;
    private ViewSwitcher switcher;
    private AutoCompleteTextView searchField;
    private TextView status;
    private ImageView searchButton;
    private ImageView acceptButton;
    private ImageView rejectButton;
    private ImageView deleteButton;
    private View separator;
    private InputMethodManager keyboard;
    private FloatingActionButton fab;

    public AccountDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        connection = new NtlmConnection(
                Reference.PROTOCOL,
                Reference.HOSTNAME,
                Reference.PORT,
                Reference.DOMAIN,
                Reference.USERNAME,
                Reference.PASSWORD,
                Reference.ORGRANIZATION_PATH
        );

        try {
            connection.connect();
            connection.authenticate();
            if(connection.getAuthenticationState()) {
                if(getArguments() != null && getArguments().containsKey(ARG_PARAM1))
                    getContent(connection, getArguments().getString(ARG_PARAM1));
                getSearchList(connection);
            }
            connection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_main, container, false);

        title = (TextView) view.findViewById(R.id.title);
        subtitle = (TextView) view.findViewById(R.id.subtitle);
        switcher = (ViewSwitcher) view.findViewById(R.id.switcher);
        searchField = (AutoCompleteTextView) view.findViewById(R.id.search_field);
        status = (TextView) view.findViewById(R.id.status);
        searchButton = (ImageView) view.findViewById(R.id.search_button);
        acceptButton = (ImageView) view.findViewById(R.id.accept_button);
        rejectButton = (ImageView) view.findViewById(R.id.reject_button);
        deleteButton = (ImageView) view.findViewById(R.id.delete_button);
        separator = view.findViewById(R.id.button_separator);
        keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        setUpCardTitle();
        setUpSearchMechanism();
        setUpDeleteButton();
        setUpFab();
        setUpContentFragment();
        return view;
    }

    private void getContent(NtlmConnection connection, String guid) throws IOException, JSONException {
        NtlmConnection.Response response = connection.retrieve(
                AccountSchema.ENTITY_NAME,
                guid,
                "$select="
                        + AccountSchema.IDENTIFIER + ","
                        + AccountSchema.ACCOUNT_NAME + ","
                        + AccountSchema.MAIN_PHONE + ","
                        + AccountSchema.FAX + ","
                        + AccountSchema.WEBSITE + ","
                        + AccountSchema.PARENT_ACCOUNT + ","
                        + AccountSchema.TICKER_SYMBOL + ","
                        + AccountSchema.ADDRESS_STREET1 + ","
                        + AccountSchema.ADDRESS_STREET2 + ","
                        + AccountSchema.ADDRESS_STREET3 + ","
                        + AccountSchema.ADDRESS_CITY + ","
                        + AccountSchema.ADDRESS_STATE + ","
                        + AccountSchema.ADDRESS_POSTAL_CODE + ","
                        + AccountSchema.ADDRESS_COUNTRY + ","
                        + AccountSchema.INDUSTRY + ","
                        + AccountSchema.SIC_CODE + ","
                        + AccountSchema.OWNERSHIP + ","
                        + AccountSchema.DESCRIPTION + ","
                        + AccountSchema.ORIGINATING_LEAD + ","
                        + AccountSchema.LAST_CAMPAIGN_DATE + ","
                        + AccountSchema.MARKETING_MATERIAL + ","
                        + AccountSchema.CONTACT_METHOD + ","
                        + AccountSchema.CONTACT_PREFERENCE_EMAIL + ","
                        + AccountSchema.CONTACT_PREFERENCE_BULK_EMAIL + ","
                        + AccountSchema.CONTACT_PREFERENCE_PHONE + ","
                        + AccountSchema.CONTACT_PREFERENCE_FAX + ","
                        + AccountSchema.CONTACT_PREFERENCE_MAIL + ","
                        + AccountSchema.CURRENCY + ","
                        + AccountSchema.CREDIT_LIMIT + ","
                        + AccountSchema.CREDIT_HOLD + ","
                        + AccountSchema.PAYMENT_TERMS + ","
                        + AccountSchema.SHIPPING_METHOD + ","
                        + AccountSchema.FREIGHT_TERMS
        );
        if(response.getStatusCode().equals("200")) {
            mAccount = new JSONObject(response.getResponseBody()).optJSONObject("d");
            mAccountString = mAccount.toString();
        }
        else {
            mAccount = null;
            mAccountString = null;
        }
    }

    private void getSearchList(NtlmConnection connection) throws IOException, JSONException {
        NtlmConnection.Response response = connection.retrieve(
                AccountSchema.ENTITY_NAME,
                null,
                "$select=" + AccountSchema.ACCOUNT_NAME + "," + AccountSchema.IDENTIFIER);
        if(response.getStatusCode().equals("200")) {
            mAccountNameList = new ArrayList<>();
            mAccountIdMap = new HashMap<>();
            JSONArray arr = new JSONObject(response.getResponseBody()).getJSONObject("d").optJSONArray("results");
            for(int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                mAccountNameList.add(obj.getString(AccountSchema.ACCOUNT_NAME));
                mAccountIdMap.put(obj.getString(AccountSchema.ACCOUNT_NAME), obj.getString(AccountSchema.IDENTIFIER));
            }
        }
    }

    private void setUpSearchMechanism() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mAccountNameList);
        searchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switcher.showNext();

                if(!switcher.getNextView().isFocusable()) {
                    if(mAccount != null) try {
                        searchField.setText(mAccount.getString(AccountSchema.ACCOUNT_NAME));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

        searchField.setHint("Search Account ...");
        searchField.setAdapter(adapter);
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String accountName = (String) parent.getItemAtPosition(position);
                keyboard.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
                redirect(mAccountIdMap.get(accountName), 0);
            }
        });
    }

    private void setUpDeleteButton() {
        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.ic_48dp_black_delete)
                        .setTitle("Delete Account")
                        .setMessage("Deleting the Account will delete all records under the Account as well. Beware, Deleting cannot be undone.")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    connection.connect();
                                    connection.authenticate();
                                    connection.delete(AccountSchema.ENTITY_NAME, mAccount.getString(AccountSchema.IDENTIFIER));
                                    connection.disconnect();
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }

                                redirect(null, 0);
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return false;
            }
        });
    }

    private void setUpCardTitle() {
        status.setVisibility(View.GONE);
        acceptButton.setVisibility(View.GONE);
        rejectButton.setVisibility(View.GONE);
        if(mAccount == null) {
            searchButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);

            switcher.showNext();
            searchField.setText("");
            searchField.requestFocus();
            keyboard.showSoftInput(searchField, 0);
        } else {
            try {
                String titleText = mAccount.getString(AccountSchema.ACCOUNT_NAME);
                String subtitleText = "Account";

                title.setText(titleText);
                subtitle.setText(subtitleText);
                searchField.setText(mAccount.getString(AccountSchema.ACCOUNT_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setUpFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Home) getActivity()).getmPager().setCurrentItem(1);
            }
        });
    }

    private void setUpContentFragment() {
        AccountDetailContent fragment = new AccountDetailContent();
        Bundle args = new Bundle();
        if(mAccountString != null)
            args.putString(ARG_PARAM2, mAccountString);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM3))
            args.putInt(ARG_PARAM3, getArguments().getInt(ARG_PARAM3));
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    protected void redirect(String accountId, int pageNumber) {
        Home home = (Home) getActivity();
        ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();
        AccountDetailMain fragment = new AccountDetailMain();
        Bundle args = new Bundle();
        if(accountId != null) args.putString(AccountDetailMain.ARG_PARAM1, accountId);
        if(pageNumber >= 0 && pageNumber <= 3) args.putInt(AccountDetailMain.ARG_PARAM3, 0);
        fragment.setArguments(args);
        mPagerAdapter.setPage(0, fragment);
        mPagerAdapter.notifyDataSetChanged();
    }
}

