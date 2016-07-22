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
    private JSONObject editBuffer;
    //Views

    public AccountDetailDetailsPart1() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //if(getArguments() != null  && getArguments().containsKey(AccountDetailMain.ARG_PARAM2))
        //    try {
        //        mAccount = new JSONObject(getArguments().getString(AccountDetailMain.ARG_PARAM2));
        //    } catch (JSONException e) {
        //        e.printStackTrace();
        //    }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail_details_part_1, container, false);

        TextView industryText = (TextView) view.findViewById(R.id.industry);
        TextView sicCodeText = (TextView) view.findViewById(R.id.sic_code);
        TextView ownershipText = (TextView) view.findViewById(R.id.ownership);
        TextView originatingLeadText = (TextView) view.findViewById(R.id.originating_lead);
        TextView lastCampaignText = (TextView) view.findViewById(R.id.last_campaign);
        TextView marketingMaterialText = (TextView) view.findViewById(R.id.marketing_material);
        TextView currencyText = (TextView) view.findViewById(R.id.currency);
        TextView creditLimitText = (TextView) view.findViewById(R.id.credit_limit);
        TextView creditHoldText = (TextView) view.findViewById(R.id.credit_hold);
        TextView paymentTermsText = (TextView) view.findViewById(R.id.payment_terms);

        if(mAccount != null) {
            try {
                if(mAccount.getJSONObject(AccountSchema.INDUSTRY).isNull("Value")) industryText.setText(REPLACEMENT_STRING);
                else industryText.setText(getResources().getStringArray(R.array.account_company_industry)[mAccount.getJSONObject(AccountSchema.INDUSTRY).getInt("Value")]);

                if(mAccount.isNull(AccountSchema.SIC_CODE)) sicCodeText.setText(REPLACEMENT_STRING);
                else sicCodeText.setText(mAccount.getString(AccountSchema.SIC_CODE));

                if(mAccount.getJSONObject(AccountSchema.OWNERSHIP).isNull("Value")) ownershipText.setText(REPLACEMENT_STRING);
                else ownershipText.setText(getResources().getStringArray(R.array.account_company_ownership)[mAccount.getJSONObject(AccountSchema.OWNERSHIP).getInt("Value")]);

                //TODO: Add Redirect To Lead
                if(mAccount.getJSONObject(AccountSchema.ORIGINATING_LEAD).isNull("Name")) originatingLeadText.setText(REPLACEMENT_STRING);
                else originatingLeadText.setText(mAccount.getJSONObject(AccountSchema.ORIGINATING_LEAD).getString("Name"));

                //TODO: Check How To Display Datetime Format
                if(mAccount.isNull(AccountSchema.LAST_CAMPAIGN_DATE)) lastCampaignText.setText(REPLACEMENT_STRING);
                else lastCampaignText.setText(mAccount.getString(AccountSchema.LAST_CAMPAIGN_DATE));

                String marketingMaterial = mAccount.getBoolean(AccountSchema.MARKETING_MATERIAL) ? "Do Not Send" : "Send";
                marketingMaterialText.setText(marketingMaterial);

                if(mAccount.getJSONObject(AccountSchema.CURRENCY).isNull("Name")) currencyText.setText(REPLACEMENT_STRING);
                else currencyText.setText(mAccount.getJSONObject(AccountSchema.CURRENCY).getString("Name"));

                if(mAccount.getJSONObject(AccountSchema.CREDIT_LIMIT).isNull("Value")) creditLimitText.setText(REPLACEMENT_STRING);
                else creditLimitText.setText(mAccount.getJSONObject(AccountSchema.CREDIT_LIMIT).getString("Value"));

                String creditOnHold = mAccount.getBoolean(AccountSchema.CREDIT_HOLD) ? "Yes" : "No";
                creditHoldText.setText(creditOnHold);

                if(mAccount.getJSONObject(AccountSchema.PAYMENT_TERMS).isNull("Value")) paymentTermsText.setText(REPLACEMENT_STRING);
                else paymentTermsText.setText(getResources().getStringArray(R.array.account_billing_payment)[mAccount.getJSONObject(AccountSchema.PAYMENT_TERMS).getInt("Value")]);

            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public void setmAccount(JSONObject mAccount) { this.mAccount = mAccount; }
    public void setEditBuffer(JSONObject editBuffer) { this.editBuffer = editBuffer; }
}
