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
 * Created by whorangester on 7/14/16.
 */
public class AccountDetailDetailsPart2 extends Fragment {
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    //Views

    public AccountDetailDetailsPart2() {}

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
        View view = inflater.inflate(R.layout.fragment_account_detail_details_part_2, container, false);

        TextView descriptionText = (TextView) view.findViewById(R.id.description);
        TextView emailText = (TextView) view.findViewById(R.id.email);
        TextView bulkEmailText = (TextView) view.findViewById(R.id.bulk_email);
        TextView phoneText = (TextView) view.findViewById(R.id.phone);
        TextView faxText = (TextView) view.findViewById(R.id.fax);
        TextView mailText = (TextView) view.findViewById(R.id.mail);
        TextView shippingText = (TextView) view.findViewById(R.id.shipping_method);
        TextView freightText = (TextView) view.findViewById(R.id.freight_terms);

        if (mAccount != null) {
            try {
                if (mAccount.isNull(AccountSchema.DESCRIPTION)) descriptionText.setText(REPLACEMENT_STRING);
                else descriptionText.setText(mAccount.getString(AccountSchema.DESCRIPTION));

                String doNotEmail = mAccount.getBoolean(AccountSchema.CONTACT_PREFERENCE_EMAIL) ? "Do Not Allow" : "Allow";
                emailText.setText(doNotEmail);

                String doNotBulkEmail = mAccount.getBoolean(AccountSchema.CONTACT_PREFERENCE_BULK_EMAIL) ? "Do Not Allow" : "Allow";
                bulkEmailText.setText(doNotBulkEmail);

                String doNotPhone = mAccount.getBoolean(AccountSchema.CONTACT_PREFERENCE_PHONE) ? "Do Not Allow" : "Allow";
                phoneText.setText(doNotPhone);

                String doNotFax = mAccount.getBoolean(AccountSchema.CONTACT_PREFERENCE_FAX) ? "Do Not Allow" : "Allow";
                faxText.setText(doNotFax);

                String doNotMail = mAccount.getBoolean(AccountSchema.CONTACT_PREFERENCE_MAIL) ? "Do Not Allow" : "Allow";
                mailText.setText(doNotMail);

                if(mAccount.getJSONObject(AccountSchema.SHIPPING_METHOD).isNull("Value")) shippingText.setText(REPLACEMENT_STRING);
                else shippingText.setText(getResources().getStringArray(R.array.account_shipping_method)[mAccount.getJSONObject(AccountSchema.SHIPPING_METHOD).getInt("Value")]);

                if(mAccount.getJSONObject(AccountSchema.FREIGHT_TERMS).isNull("Value")) freightText.setText(REPLACEMENT_STRING);
                else freightText.setText(getResources().getStringArray(R.array.account_freight_terms)[mAccount.getJSONObject(AccountSchema.FREIGHT_TERMS).getInt("Value")]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
