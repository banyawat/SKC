package com.theteus.kubota.AccountModule;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.DeleteService;
import com.theteus.kubota.OrganizationDataService.RetrieveService;
import com.theteus.kubota.R;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Log.i(">>>", "ONCREATE()");
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1))
            getContent();
        getSearchList();
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

        if(getArguments() == null || !getArguments().containsKey(ARG_PARAM1)) {
            setUpCardTitle();
            setUpContentFragment();
        }
        setUpDeleteButton();
        setUpFab();

        return view;
    }

    private void getContent() {
        new RetrieveService(getContext(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                if(result.has("d")) {
                    mAccount = result.optJSONObject("d");
                    mAccountString = mAccount.toString();
                } else {
                    mAccount = null;
                    mAccountString = null;
                }
                setUpCardTitle();
                setUpContentFragment();
            }
        })
                .setEntity(AccountSchema.ENTITY_NAME)
                .setGuid(getArguments().getString(ARG_PARAM1))
                .setQueryString("$select="
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
                )
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void getSearchList() {
        new RetrieveService(getContext(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                mAccountNameList = new ArrayList<>();
                mAccountIdMap = new HashMap<>();
                try {
                    JSONArray arr = result.getJSONObject("d").optJSONArray("results");
                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        mAccountNameList.add(obj.getString(AccountSchema.ACCOUNT_NAME));
                        mAccountIdMap.put(obj.getString(AccountSchema.ACCOUNT_NAME), obj.getString(AccountSchema.IDENTIFIER));
                        setUpSearchMechanism();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setEntity(AccountSchema.ENTITY_NAME)
                .setQueryString("$select=" + AccountSchema.ACCOUNT_NAME + "," + AccountSchema.IDENTIFIER)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setUpSearchMechanism() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mAccountNameList);
        searchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switcher.showNext();

                if(!switcher.getNextView().isFocusable()) {
                    if(mAccount != null)
                        try {
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
                                    new DeleteService(getActivity(), new AsyncResponse() {
                                        @Override
                                        public void onFinishTask(JSONObject result) {
                                            redirect(null, 0);
                                        }
                                    })
                                            .setEntity(AccountSchema.ENTITY_NAME)
                                            .setGuid(mAccount.getString(AccountSchema.IDENTIFIER))
                                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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

