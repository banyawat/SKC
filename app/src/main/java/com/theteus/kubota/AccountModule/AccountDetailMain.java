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
import com.theteus.kubota.OrganizationDataService.UpdateService;
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
    public static final String ARG_PARAM2 = "currentTab";
    // Contents
    protected JSONObject mAccount;
    protected JSONObject editBuffer;
    protected List<String> mAccountNameList;
    protected Map<String, String> mAccountIdMap;
    protected List<String> currencyList;
    protected Map<String, String> currencyMap;
    protected boolean loadingFlag1;
    protected boolean loadingFlag2;
    protected boolean loadingFlag3;
    // Views
    private TextView title;
    private TextView subtitle;
    private ViewSwitcher switcher;
    private AutoCompleteTextView searchField;
    private TextView status;
    private ImageView searchButton;
    private ImageView acceptButton;
    private ImageView rejectButton;
    private ImageView saveButton;
    private ImageView deleteButton;
    private View separator;
    private InputMethodManager keyboard;
    private FloatingActionButton fab;

    public AccountDetailMain() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(getArguments() != null && getArguments().containsKey(ARG_PARAM1)) getContent();
        else loadingFlag1 = true;
        getSearchList();
        getCurrencyList();
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
        saveButton = (ImageView) view.findViewById(R.id.save_button);
        separator = view.findViewById(R.id.button_separator);
        keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        toggleSaveButton();
        setUpCardTitle();
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
                    editBuffer = new JSONObject();
                } else {
                    mAccount = null;
                    editBuffer = null;
                }
                setUpSaveButton();
                setUpCardTitleData();
                loadingFlag1 = true;
                Log.i(">>>", "LOADING 1 FINISHED");
                if(loadingFlag1 && loadingFlag2 && loadingFlag3) setUpContentFragment();
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
                        if(getArguments() == null || !getArguments().containsKey(ARG_PARAM1) || !obj.getString(AccountSchema.IDENTIFIER).equals(getArguments().getString(ARG_PARAM1))) {
                            mAccountNameList.add(obj.getString(AccountSchema.ACCOUNT_NAME));
                            mAccountIdMap.put(obj.getString(AccountSchema.ACCOUNT_NAME), obj.getString(AccountSchema.IDENTIFIER));
                        }
                    }
                    setUpSearchMechanism();
                    Log.i(">>>", "LOADING 2 FINISHED");
                    loadingFlag2 = true;
                    if(loadingFlag1 && loadingFlag2 && loadingFlag3) setUpContentFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setEntity(AccountSchema.ENTITY_NAME)
                .setQueryString("$select=" + AccountSchema.ACCOUNT_NAME + "," + AccountSchema.IDENTIFIER)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void getCurrencyList() {
        new RetrieveService(getContext(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                currencyList = new ArrayList<>();
                currencyMap = new HashMap<>();
                try {
                    JSONArray arr = result.getJSONObject("d").optJSONArray("results");
                    for(int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Log.i("JSON", obj.toString());
                        currencyList.add(obj.getString("CurrencyName"));
                        currencyMap.put(obj.getString("CurrencyName"), obj.getString("TransactionCurrencyId"));
                    }
                    Log.i(">>>", "LOADING 3 FINISHED");
                    loadingFlag3 = true;
                    if(loadingFlag1 && loadingFlag2 && loadingFlag3) setUpContentFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setEntity("TransactionCurrency")
                .setQueryString("$select=TransactionCurrencyId,CurrencyName,CurrencySymbol")
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

    protected void toggleSaveButton() {
        if(editBuffer == null || editBuffer.length() == 0) saveButton.setVisibility(View.GONE);
        else saveButton.setVisibility(View.VISIBLE);
    }

    private void setUpSaveButton() {
        saveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new AlertDialog.Builder(getContext())
                        .setIcon(R.drawable.ic_48dp_black_save)
                        .setTitle("Apply Change to Account")
                        .setMessage("Updating the Account will change this Account record. Beware, Updating cannot be undone.")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    new UpdateService(getActivity(), new AsyncResponse() {
                                        @Override
                                        public void onFinishTask(JSONObject result) {
                                            redirect(getArguments().getString(ARG_PARAM1), 0);
                                        }
                                    })
                                            .setEntity(AccountSchema.ENTITY_NAME)
                                            .setGuid(mAccount.getString(AccountSchema.IDENTIFIER))
                                            .setJSONEntry(editBuffer)
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
        if(getArguments() == null || !getArguments().containsKey(ARG_PARAM1)) {
            searchButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);

            switcher.showNext();
            searchField.setText("");
            searchField.requestFocus();
            keyboard.showSoftInput(searchField, 0);
        }
    }

    private void setUpCardTitleData() {
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
        fragment.setmAccount(mAccount);
        fragment.setParent(this);
        if(getArguments() != null && getArguments().getInt(ARG_PARAM2) >= 0 && getArguments().getInt(ARG_PARAM2) <= 3) {
            Bundle args = new Bundle();
            args.putInt(ARG_PARAM2, getArguments().getInt(ARG_PARAM2));
            fragment.setArguments(args);
        }
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
        if(pageNumber >= 0 && pageNumber <= 3) args.putInt(AccountDetailMain.ARG_PARAM2, 0);
        fragment.setArguments(args);
        mPagerAdapter.setPage(0, fragment);
        mPagerAdapter.notifyDataSetChanged();
    }
}

