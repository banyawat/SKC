package com.theteus.kubota.AccountModule;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.DeleteService;
import com.theteus.kubota.OrganizationDataService.RetrieveService;
import com.theteus.kubota.OrganizationDataService.UpdateService;
import com.theteus.kubota.R;

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

        setUpCardTitle();
        setUpDeleteButton();
        setUpFab();
        toggleSaveButton();

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
                if(loadingFlag2 && loadingFlag3) setUpContentFragment();
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
                    loadingFlag2 = true;
                    if(loadingFlag1 && loadingFlag3) setUpContentFragment();
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
                        currencyList.add(obj.getString(TransactionCurrencySchema.CURRENCY_NAME));
                        currencyMap.put(obj.getString(TransactionCurrencySchema.CURRENCY_NAME), obj.getString(TransactionCurrencySchema.IDENTIFIER));
                    }
                    loadingFlag3 = true;
                    if(loadingFlag1 && loadingFlag2) setUpContentFragment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setEntity(TransactionCurrencySchema.ENTITY_NAME)
                .setQueryString("$select="
                        + TransactionCurrencySchema.IDENTIFIER + ","
                        + TransactionCurrencySchema.CURRENCY_NAME + ","
                        + TransactionCurrencySchema.CURRENCY_SYMBOL)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setUpSearchMechanism() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mAccountNameList);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    searchButton.setImageResource(R.drawable.ic_36dp_black_highlight_off);
                } else {
                    searchField.clearFocus();
                    keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    searchButton.setImageResource(R.drawable.ic_36dp_black_search);
                }
            }
        });

        searchField.setHint("Search Account ...");
        searchField.setAdapter(adapter);
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String accountName = (String) parent.getItemAtPosition(position);
                keyboard.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
                redirect(mAccountIdMap.get(accountName));
            }
        });
    }

    private void setUpDeleteButton() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                            redirect(null);
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
            }
        });
    }

    protected void toggleSaveButton() {
        if(editBuffer == null || editBuffer.length() == 0) saveButton.setVisibility(View.GONE);
        else saveButton.setVisibility(View.VISIBLE);
    }

    private void setUpSaveButton() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                            redirect(getArguments().getString(ARG_PARAM1));
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
            }
        });
    }

    private void setUpCardTitle() {
        LayoutTransition lt = new LayoutTransition();
        lt.setAnimator(LayoutTransition.APPEARING, ObjectAnimator.ofFloat(null, "alpha", 0.0f, 0.38f));
        lt.setAnimator(LayoutTransition.DISAPPEARING, ObjectAnimator.ofFloat(null, "alpha", 0.38f, 0.0f));
        cardPanel.setLayoutTransition(lt);
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

    protected void redirect(String accountId) {
        Home home = (Home) getActivity();
        home.goTo(5, accountId);
    }
}

