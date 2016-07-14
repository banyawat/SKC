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

public class AccountDetailDetailsPart1 extends Fragment{
    //Constant
    private static final String REPLACEMENT_STRING = "- - -";
    //Content
    private JSONObject mAccount;
    //Views

    public AccountDetailDetailsPart1() {}

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
        View view = inflater.inflate(R.layout.fragment_account_detail_details_part_1, container, false);

        if(mAccount != null) {
            try {
                if(mAccount.getJSONObject(AccountSchema.INDUSTRY).isNull("Value")) ((TextView) view.findViewById(R.id.industry)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.industry)).setText(getResources().getStringArray(R.array.account_company_industry)[mAccount.getJSONObject(AccountSchema.INDUSTRY).getInt("Value")]);

                if(mAccount.isNull(AccountSchema.SIC_CODE)) ((TextView) view.findViewById(R.id.sic_code)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.sic_code)).setText(mAccount.getString(AccountSchema.SIC_CODE));

                if(mAccount.getJSONObject(AccountSchema.OWNERSHIP).isNull("Value")) ((TextView) view.findViewById(R.id.ownership)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.ownership)).setText(getResources().getStringArray(R.array.account_company_ownership)[mAccount.getJSONObject(AccountSchema.OWNERSHIP).getInt("Value")]);

                //TODO: Add Redirect To Lead
                if(mAccount.getJSONObject(AccountSchema.ORIGINATING_LEAD).isNull("Name")) ((TextView) view.findViewById(R.id.originating_lead)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.originating_lead)).setText(mAccount.getJSONObject(AccountSchema.ORIGINATING_LEAD).getString("Name"));

                //TODO: Check How To Display Datetime Format
                if(mAccount.isNull(AccountSchema.LAST_CAMPAIGN_DATE)) ((TextView) view.findViewById(R.id.last_campaign)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.last_campaign)).setText(mAccount.getString(AccountSchema.LAST_CAMPAIGN_DATE));

                String marketingMaterial = mAccount.getBoolean(AccountSchema.MARKETING_MATERIAL) ? "Do Not Send" : "Send";
                ((TextView) view.findViewById(R.id.marketing_material)).setText(marketingMaterial);

                if(mAccount.getJSONObject(AccountSchema.CURRENCY).isNull("Name")) ((TextView) view.findViewById(R.id.currency)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.currency)).setText(mAccount.getJSONObject(AccountSchema.CURRENCY).getString("Name"));

                if(mAccount.getJSONObject(AccountSchema.CREDIT_LIMIT).isNull("Value")) ((TextView) view.findViewById(R.id.credit_limit)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.credit_limit)).setText(mAccount.getJSONObject(AccountSchema.CREDIT_LIMIT).getString("Value"));

                String creditOnHold = mAccount.getBoolean(AccountSchema.CREDIT_HOLD) ? "Yes" : "No";
                ((TextView) view.findViewById(R.id.credit_hold)).setText(creditOnHold);

                if(mAccount.getJSONObject(AccountSchema.PAYMENT_TERMS).isNull("Value")) ((TextView) view.findViewById(R.id.payment_terms)).setText(REPLACEMENT_STRING);
                else ((TextView) view.findViewById(R.id.payment_terms)).setText(getResources().getStringArray(R.array.account_billing_payment)[mAccount.getJSONObject(AccountSchema.PAYMENT_TERMS).getInt("Value")]);

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
