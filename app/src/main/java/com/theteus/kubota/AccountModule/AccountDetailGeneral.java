package com.theteus.kubota.AccountModule;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.Home;
import com.theteus.kubota.R;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountDetailGeneral extends Fragment {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;

    public AccountDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if(getArguments() != null  && getArguments().containsKey(AccountDetailMain.ARG_PARAM2))
        try {
            mAccount = new JSONObject(getArguments().getString(AccountDetailMain.ARG_PARAM2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail_general, container, false);

        if(mAccount != null) {
            try {
                if(mAccount.isNull(AccountSchema.ACCOUNT_NAME)) ((TextView) view.findViewById(R.id.account_name)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.account_name)).setText(mAccount.getString(AccountSchema.ACCOUNT_NAME));

                if(mAccount.getJSONObject(AccountSchema.PARENT_ACCOUNT).isNull("Name")) ((TextView) view.findViewById(R.id.parent_account)).setText(REPLACEMENT_STRING);
                else {
                    ((TextView) view.findViewById(R.id.parent_account)).setText(mAccount.getJSONObject(AccountSchema.PARENT_ACCOUNT).optString("Name"));
                    ((TextView) view.findViewById(R.id.parent_account)).setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    view.findViewById(R.id.parent_account).setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Home home = (Home) getActivity();
                            ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                            mPagerAdapter.clearPage();
                            AccountDetailMain fragment = new AccountDetailMain();
                            Bundle args = new Bundle();
                            try {
                                args.putString(AccountDetailMain.ARG_PARAM1, mAccount.optJSONObject(AccountSchema.PARENT_ACCOUNT).getString("Id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            args.putInt(AccountDetailMain.ARG_PARAM3, 0);
                            fragment.setArguments(args);
                            mPagerAdapter.addPage(fragment);
                            mPagerAdapter.addPage(new Account());
                            mPagerAdapter.notifyDataSetChanged();

                            home.changeMenu(5);
                        }
                    });
                }

                if(mAccount.isNull(AccountSchema.TICKER_SYMBOL)) ((TextView) view.findViewById(R.id.ticker)).setText(REPLACEMENT_STRING);
                else((TextView) view.findViewById(R.id.ticker)).setText(mAccount.getString(AccountSchema.TICKER_SYMBOL));

                if(mAccount.isNull(AccountSchema.MAIN_PHONE)) ((TextView) view.findViewById(R.id.phone)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.phone)).setText(mAccount.getString(AccountSchema.MAIN_PHONE));

                if(mAccount.isNull(AccountSchema.FAX)) ((TextView) view.findViewById(R.id.fax)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.fax)).setText(mAccount.getString(AccountSchema.FAX));

                if(mAccount.isNull(AccountSchema.WEBSITE)) ((TextView) view.findViewById(R.id.website)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.website)).setText(mAccount.getString(AccountSchema.WEBSITE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
