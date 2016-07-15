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
import org.w3c.dom.Text;

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

        TextView street1Text = (TextView) view.findViewById(R.id.street_1);
        TextView street2Text = (TextView) view.findViewById(R.id.street_2);
        TextView street3Text = (TextView) view.findViewById(R.id.street_3);
        TextView cityText = (TextView) view.findViewById(R.id.city);
        TextView stateText = (TextView) view.findViewById(R.id.state);
        TextView postalCodeText = (TextView) view.findViewById(R.id.postal_code);
        TextView countryText = (TextView) view.findViewById(R.id.country);

        if(mAccount != null) {
            try {
                if(mAccount.isNull(AccountSchema.ADDRESS_STREET1)) street1Text.setText(REPLACEMENT_STRING);
                else street1Text.setText(mAccount.getString(AccountSchema.ADDRESS_STREET1));

                if(mAccount.isNull(AccountSchema.ADDRESS_STREET2)) street2Text.setText(REPLACEMENT_STRING);
                else street2Text.setText(mAccount.getString(AccountSchema.ADDRESS_STREET2));

                if(mAccount.isNull(AccountSchema.ADDRESS_STREET3)) street3Text.setText(REPLACEMENT_STRING);
                else street3Text.setText(mAccount.getString(AccountSchema.ADDRESS_STREET3));

                if(mAccount.isNull(AccountSchema.ADDRESS_CITY)) cityText.setText(REPLACEMENT_STRING);
                else cityText.setText(mAccount.getString(AccountSchema.ADDRESS_CITY));

                if(mAccount.isNull(AccountSchema.ADDRESS_STATE)) stateText.setText(REPLACEMENT_STRING);
                else stateText.setText(mAccount.getString(AccountSchema.ADDRESS_STATE));

                if(mAccount.isNull(AccountSchema.ADDRESS_POSTAL_CODE)) postalCodeText.setText(REPLACEMENT_STRING);
                else postalCodeText.setText(mAccount.getString(AccountSchema.ADDRESS_POSTAL_CODE));

                if(mAccount.isNull(AccountSchema.ADDRESS_COUNTRY)) countryText.setText(REPLACEMENT_STRING);
                else countryText.setText(mAccount.getString(AccountSchema.ADDRESS_COUNTRY));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
