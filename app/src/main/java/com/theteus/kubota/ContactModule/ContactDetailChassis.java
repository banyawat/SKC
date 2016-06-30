package com.theteus.kubota.ContactModule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.ChassisModule.Chassis;
import com.theteus.kubota.ChassisModule.ChassisDetailMain;
import com.theteus.kubota.ChassisModule.ChassisInstance;
import com.theteus.kubota.ChassisModule.DummyChassisInstance;
import com.theteus.kubota.Home;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import java.util.List;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailChassis extends Fragment {
    private ContactInstance mContact;
    List<ChassisInstance> mChassis;
    ContactChassisAdapter contactChassisAdapter;
    RecyclerView recyclerView;

    public ContactDetailChassis() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
            mChassis = DummyChassisInstance.searchByContactId(mContact.id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_chassis, container, false);

        if(mContact != null) {
            String leadCounter;
            switch (mChassis.size()) {
                case 0:
                    leadCounter = "No Chassis";
                    break;
                default:
                    leadCounter = mChassis.size() + " Chassis";
                    break;
            }
            ((TextView) view.findViewById(R.id.contact_detail_chassis_count)).setText(leadCounter);

            contactChassisAdapter = new ContactChassisAdapter(mChassis);
            View chassisListView = view.findViewById(R.id.contact_detail_chassis_list);
            assert chassisListView != null;
            recyclerView = (RecyclerView) chassisListView;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(contactChassisAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        return view;
    }

    public class ContactChassisAdapter extends RecyclerView.Adapter<ContactChassisAdapter.ViewHolder> {
        private final List<ChassisInstance> mChassis;

        public ContactChassisAdapter(List<ChassisInstance> chassis) {
            this.mChassis = chassis;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_detail_chassis_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            switch(mChassis.get(position).productStatus) {
                case 177980000:
                case 177980001:
                case 177980002:
                    holder.mStatus.setText(Reference.MASTER_PRODUCTSTATUS.get(mChassis.get(position).productStatus));
                    holder.mStatus.setBackgroundResource(R.color.statusGreen);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
                    break;
                case 177980003:
                    holder.mStatus.setText(Reference.MASTER_PRODUCTSTATUS.get(mChassis.get(position).productStatus));
                    holder.mStatus.setBackgroundResource(R.color.statusRed);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    break;
                default:
                    holder.mStatus.setText("Unknown");
                    holder.mStatus.setBackgroundResource(R.color.statusUnknown);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
                    break;
            }
            holder.mNumber.setText(mChassis.get(position).chassisNumber);
            holder.mEngine.setText("(Engine : " + mChassis.get(position).engineNumber + ")");
            holder.mType.setText(Reference.MASTER_PRODUCTTYPE.get(mChassis.get(position).productType));
            holder.mModel.setText("(" + mChassis.get(position).productModel + ")");

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home home = (Home) getActivity();
                    ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                    mPagerAdapter.clearPage();
                    ChassisDetailMain fragment = new ChassisDetailMain();
                    Bundle args = new Bundle();
                    args.putString(ChassisDetailMain.ARG_PARAM1, mChassis.get(position).chassisNumber);
                    args.putInt(ChassisDetailMain.ARG_PARAM2, 1);
                    fragment.setArguments(args);
                    mPagerAdapter.addPage(fragment);
                    mPagerAdapter.addPage(new Chassis());
                    mPagerAdapter.notifyDataSetChanged();

                    home.changeMenu(6);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mChassis.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mStatus;
            public final TextView mNumber;
            public final TextView mEngine;
            public final TextView mType;
            public final TextView mModel;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mStatus = (TextView) view.findViewById(R.id.chassis_status);
                mNumber = (TextView) view.findViewById(R.id.chassis_number);
                mEngine = (TextView) view.findViewById(R.id.chassis_engine);
                mType = (TextView) view.findViewById(R.id.chassis_type);
                mModel = (TextView) view.findViewById(R.id.chassis_model);
            }
        }
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
