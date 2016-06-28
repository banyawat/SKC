package com.theteus.kubota.LeadModule;

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
import android.widget.Button;
import android.widget.TextView;

import com.theteus.kubota.ActivitiesModule.Activities;
import com.theteus.kubota.ActivitiesModule.ActivitiesDetailMain;
import com.theteus.kubota.ActivitiesModule.ActivityInstance;
import com.theteus.kubota.ActivitiesModule.DummyActivityInstance;
import com.theteus.kubota.Home;
import com.theteus.kubota.R;
import com.theteus.kubota.Reference;
import com.theteus.kubota.ScreenSlidePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailActivities extends Fragment {
    private LeadInstance mLead;
    List<ActivityInstance> mLeadActivities;
    LeadActivitiesAdapter leadActivitiesAdapter;
    RecyclerView recyclerView;

    public LeadDetailActivities() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(LeadDetailMain.ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(LeadDetailMain.ARG_PARAM1));
            mLeadActivities = DummyActivityInstance.searchByLeadId(mLead.id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_detail_activities, container, false);

        if(mLead != null) {
            int activitiesCount = mLeadActivities.size();
            String activitiesCounter;
            switch(activitiesCount) {
                case 0:
                    activitiesCounter = "No Activity";
                    break;
                case 1:
                    activitiesCounter = "1 Activity";
                    break;
                default:
                    activitiesCounter = activitiesCount + " Activities";
            }
            ((TextView) view.findViewById(R.id.lead_detail_activities_count)).setText(activitiesCounter);

            leadActivitiesAdapter = new LeadActivitiesAdapter(mLeadActivities);
            View activitiesListView = view.findViewById(R.id.lead_detail_activities_list);
            assert activitiesListView != null;
            recyclerView = (RecyclerView) activitiesListView;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(leadActivitiesAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);

            ((Button) view.findViewById(R.id.lead_detail_activity_add_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home home = (Home) getActivity();
                    ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                    mPagerAdapter.clearPage();
                    //ActivitiesDetailMain fragment = new ActivitiesDetailMain();
                    //fragment.setArguments(new Bundle());
                    //mPagerAdapter.addPage(fragment);

                    Activities fragment = new Activities();
                    mPagerAdapter.addPage(new Activities());
                    mPagerAdapter.notifyDataSetChanged();

                    home.changeMenu(4);
                    home.getmPager().setCurrentItem(1);
                }
            });
        }

        return view;
    }

    public class LeadActivitiesAdapter extends RecyclerView.Adapter<LeadActivitiesAdapter.ViewHolder> {
        private final List<ActivityInstance> mActivities;

        public LeadActivitiesAdapter(List<ActivityInstance> activities) {
            this.mActivities = activities;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lead_detail_activities_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mStatus.setText(Reference.MASTER_ACTIVITYSTATUS.get(mActivities.get(position).status));
            switch(mActivities.get(position).status) {
                case 117980000:
                    holder.mStatus.setBackgroundResource(R.color.statusGreen);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusGreenFont));
                    break;
                case 117980001:
                    holder.mStatus.setBackgroundResource(R.color.statusRed);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusRedFont));
                    break;
                case 117980002:
                    holder.mStatus.setBackgroundResource(R.color.statusBlue);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusBlueFont));
                    break;
                default:
                    holder.mStatus.setBackgroundResource(R.color.statusUnknown);
                    holder.mStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.statusUnknownFont));
                    break;
            }
            holder.mSubject.setText(mActivities.get(position).subject);
            holder.mDueDate.setText("Due : " + mActivities.get(position).dueDate);
            holder.mLastUser.setText("Last Modified By : " + mActivities.get(position).lastUser);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Home home = (Home) getActivity();
                    ScreenSlidePagerAdapter mPagerAdapter = home.getmPagerAdapter();

                    mPagerAdapter.clearPage();
                    ActivitiesDetailMain fragment = new ActivitiesDetailMain();
                    Bundle args = new Bundle();
                    args.putString(ActivitiesDetailMain.ARG_PARAM1, mActivities.get(position).id);
                    fragment.setArguments(args);
                    mPagerAdapter.addPage(fragment);
                    mPagerAdapter.addPage(new Activities());
                    mPagerAdapter.notifyDataSetChanged();

                    home.changeMenu(4);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mStatus;
            public final TextView mSubject;
            public final TextView mDueDate;
            public final TextView mLastUser;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mStatus = (TextView) view.findViewById(R.id.activity_status) ;
                mSubject = (TextView) view.findViewById(R.id.activity_subject);
                mDueDate = (TextView) view.findViewById(R.id.activity_due_date);
                mLastUser = (TextView) view.findViewById(R.id.activity_last_user);
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
