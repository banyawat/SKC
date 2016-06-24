package com.theteus.kubota.AccountModule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.theteus.kubota.R;

public class AccountForm03 extends Fragment {
    Button menu1, menu2;
    LinearLayout layoutLoader;
    View viewPart1, viewPart2;
    int pageID = 0;

    Spinner industrySpinner, ownershipSpinner, billingPaymentSpinner, shippingMethodSpinner;

    public AccountForm03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_form03, container, false);

        findViewsById(view);
        initButton(view);
        initPage1(viewPart1);
        initPage2(viewPart2);

        return view;
    }

    private void findViewsById(View view){
        menu1 = (Button) view.findViewById(R.id.account_form3_menu1);
        menu2 = (Button) view.findViewById(R.id.account_form3_menu2);
        layoutLoader = (LinearLayout) view.findViewById(R.id.account_form3_layoutloader);
        viewPart1 = LayoutInflater.from(view.getContext()).inflate(
                R.layout.account_form03_1, null);
        viewPart2 = LayoutInflater.from(view.getContext()).inflate(
                R.layout.account_form03_2, null);
        layoutLoader.addView(viewPart1);
    }

    private void initPage1(View view){
        industrySpinner = (Spinner) view.findViewById(R.id.account_form3_1_industry);
        ArrayAdapter<CharSequence> industryAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_company_industry, android.R.layout.simple_spinner_item);
        industryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        industrySpinner.setAdapter(industryAdapter);

        ownershipSpinner = (Spinner) view.findViewById(R.id.account_form3_1_ownership);
        ArrayAdapter<CharSequence> ownershipAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_company_ownership, android.R.layout.simple_spinner_item);
        ownershipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ownershipSpinner.setAdapter(ownershipAdapter);

        billingPaymentSpinner = (Spinner) view.findViewById(R.id.account_form3_1_paymentterm);
        ArrayAdapter<CharSequence> billingAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_billing_payment, android.R.layout.simple_spinner_item);
        billingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billingPaymentSpinner.setAdapter(billingAdapter);
    }

    private void initPage2(View view){
        shippingMethodSpinner = (Spinner) view.findViewById(R.id.account_form3_2_shippingmethod);
        ArrayAdapter<CharSequence> shipAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.account_shipping_method, android.R.layout.simple_spinner_item);
        shipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shippingMethodSpinner.setAdapter(shipAdapter);
    }

    private void initButton(final View view){
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageID){
                    case 0:
                        break;
                    case 1:
                        layoutLoader.removeAllViews();
                        layoutLoader.addView(viewPart1);
                        pageID=0;
                        break;
                    default:
                        break;
                }
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pageID){
                    case 0:
                        layoutLoader.removeAllViews();
                        layoutLoader.addView(viewPart2);
                        pageID=1;
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
