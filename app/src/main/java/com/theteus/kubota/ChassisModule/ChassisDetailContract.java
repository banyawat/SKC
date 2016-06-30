package com.theteus.kubota.ChassisModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.ActivitiesModule.DummyActivityInstance;
import com.theteus.kubota.ContactModule.Contact;
import com.theteus.kubota.ContactModule.ContactDetailMain;
import com.theteus.kubota.ContactModule.ContactInstance;
import com.theteus.kubota.ContactModule.DummyContactInstance;
import com.theteus.kubota.Home;
import com.theteus.kubota.LeadModule.LeadDetailMain;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;
import com.theteus.kubota.ScreenSlidePagerAdapter;

/**
 * Created by whorangester on 6/29/16.
 */
public class ChassisDetailContract extends Fragment {
    private ChassisInstance mChassis;
    private ContactInstance mContact;

    public ChassisDetailContract() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ChassisDetailMain.ARG_PARAM1)) {
            mChassis = DummyChassisInstance.CHASSIS_MAP.get(getArguments().getString(ChassisDetailMain.ARG_PARAM1));
            mContact = DummyContactInstance.CONTACT_MAP.get(mChassis.contact);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_detail_contract, container, false);

        if (mChassis != null) {
            ((TextView) view.findViewById(R.id.chassis_detail_contract_number)).setText(mChassis.contractNumber);
            ((TextView) view.findViewById(R.id.chassis_detail_purchase_date)).setText(mChassis.purchaseDate);
            ((TextView) view.findViewById(R.id.chassis_detail_contact)).setText(mContact.firstName + " " + mContact.lastName);
            ((TextView) view.findViewById(R.id.chassis_detail_dealer)).setText(mChassis.dealer);
            ((TextView) view.findViewById(R.id.chassis_detail_sale_condition)).setText(Reference.MASTER_SALECONDITION.get(mChassis.saleCondition));
            ((TextView) view.findViewById(R.id.chassis_detail_user)).setText(mChassis.user);
            ((TextView) view.findViewById(R.id.chassis_detail_phone)).setText(mChassis.userTel);

            ((TextView) view.findViewById(R.id.chassis_detail_contact)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home home = (Home) getActivity();
                    ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                    mPagerAdapter.clearPage();
                    ContactDetailMain fragment = new ContactDetailMain();
                    Bundle args = new Bundle();
                    args.putString(ContactDetailMain.ARG_PARAM1, mChassis.contact);
                    args.putInt(ContactDetailMain.ARG_PARAM2, 3);
                    fragment.setArguments(args);
                    mPagerAdapter.addPage(fragment);
                    mPagerAdapter.addPage(new Contact());
                    mPagerAdapter.notifyDataSetChanged();

                    home.changeMenu(2);
                }
            });
        }

        return view;
    }
}
