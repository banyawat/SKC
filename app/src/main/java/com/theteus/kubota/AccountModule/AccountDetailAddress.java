package com.theteus.kubota.AccountModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by whorangester on 7/13/16.
 */
public class AccountDetailAddress extends Fragment {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;

    public AccountDetailAddress() {}

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
        View view = inflater.inflate(R.layout.fragment_account_detail_address, container, false);

        if(mAccount != null) {
            try {
                if(mAccount.isNull(AccountSchema.ADDRESS_STREET1)) ((TextView) view.findViewById(R.id.street_1)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.street_1)).setText(mAccount.getString(AccountSchema.ADDRESS_STREET1));

                if(mAccount.isNull(AccountSchema.ADDRESS_STREET2)) ((TextView) view.findViewById(R.id.street_2)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.street_2)).setText(mAccount.getString(AccountSchema.ADDRESS_STREET2));

                if(mAccount.isNull(AccountSchema.ADDRESS_STREET3)) ((TextView) view.findViewById(R.id.street_3)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.street_3)).setText(mAccount.getString(AccountSchema.ADDRESS_STREET3));

                if(mAccount.isNull(AccountSchema.ADDRESS_CITY)) ((TextView) view.findViewById(R.id.city)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.city)).setText(mAccount.getString(AccountSchema.ADDRESS_CITY));

                if(mAccount.isNull(AccountSchema.ADDRESS_STATE)) ((TextView) view.findViewById(R.id.state)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.state)).setText(mAccount.getString(AccountSchema.ADDRESS_STATE));

                if(mAccount.isNull(AccountSchema.ADDRESS_POSTAL_CODE)) ((TextView) view.findViewById(R.id.postal_code)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.postal_code)).setText(mAccount.getString(AccountSchema.ADDRESS_POSTAL_CODE));

                if(mAccount.isNull(AccountSchema.ADDRESS_COUNTRY)) ((TextView) view.findViewById(R.id.country)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.country)).setText(mAccount.getString(AccountSchema.ADDRESS_COUNTRY));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
