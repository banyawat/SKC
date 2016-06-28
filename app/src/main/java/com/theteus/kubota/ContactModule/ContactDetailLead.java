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

import com.theteus.kubota.Home;
import com.theteus.kubota.LeadModule.DummyLeadInstance;
import com.theteus.kubota.LeadModule.Lead;
import com.theteus.kubota.LeadModule.LeadDetailMain;
import com.theteus.kubota.LeadModule.LeadInstance;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import java.util.List;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailLead extends Fragment {
    private ContactInstance mContact;
    List<LeadInstance> mLead;
    ContactLeadAdapter contactLeadAdapter;
    RecyclerView recyclerView;

    public ContactDetailLead() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
            mLead = DummyLeadInstance.searchByContactId(mContact.id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_lead, container, false);

        if(mContact != null) {
            String leadCounter;
            switch (mLead.size()) {
                case 0:
                    leadCounter = "No Lead";
                    break;
                case 1:
                    leadCounter = "1 Lead";
                    break;
                default:
                    leadCounter = mLead.size() + " Leads";
            }
            ((TextView) view.findViewById(R.id.contact_detail_lead_count)).setText(leadCounter);

            contactLeadAdapter = new ContactLeadAdapter(mLead);
            View leadListView = view.findViewById(R.id.contact_detail_lead_list);
            assert leadListView != null;
            recyclerView = (RecyclerView) leadListView;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(contactLeadAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
        }
        return view;
    }

    public class ContactLeadAdapter extends RecyclerView.Adapter<ContactLeadAdapter.ViewHolder> {
        private final List<LeadInstance> mLead;

        public ContactLeadAdapter(List<LeadInstance> leads) {
            this.mLead = leads;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_detail_lead_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mStatus.setText(Reference.MASTER_LEADSTATUS.get(mLead.get(position).status));
            switch(mLead.get(position).status) {
                case 117980001:
                    holder.mStatus.setBackgroundResource(R.color.statusGreen);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
                    break;
                case 117980002:
                    holder.mStatus.setBackgroundResource(R.color.statusRed);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    break;
                case 117980000:
                    holder.mStatus.setBackgroundResource(R.color.statusBlue);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusBlueFont));
                    break;
                default:
                    holder.mStatus.setBackgroundResource(R.color.statusUnknown);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
                    break;
            }
            holder.mName.setText(mLead.get(position).firstName + " " + mLead.get(position).lastName);
            holder.mOwner.setText("Owner : " + mLead.get(position).owner);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home home = (Home) getActivity();
                    ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                    mPagerAdapter.clearPage();
                    LeadDetailMain fragment = new LeadDetailMain();
                    Bundle args = new Bundle();
                    args.putString(LeadDetailMain.ARG_PARAM1, mLead.get(position).id);
                    fragment.setArguments(args);
                    mPagerAdapter.addPage(fragment);
                    mPagerAdapter.addPage(new Lead());
                    mPagerAdapter.notifyDataSetChanged();

                    home.changeMenu(3);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mLead.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mStatus;
            public final TextView mName;
            public final TextView mOwner;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mStatus = (TextView) view.findViewById(R.id.lead_status) ;
                mName = (TextView) view.findViewById(R.id.lead_name);
                mOwner = (TextView) view.findViewById(R.id.lead_owner);
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
