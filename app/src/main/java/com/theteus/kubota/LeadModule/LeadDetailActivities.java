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
import android.widget.TextView;

import com.theteus.kubota.ActivitiesModule.ActivityInstance;
import com.theteus.kubota.ActivitiesModule.DummyActivityInstance;
import com.theteus.kubota.R;

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
            holder.mSubject.setText(mActivities.get(position).subject);
            holder.mDueDate.setText(mActivities.get(position).dueDate);
            holder.mLastUser.setText(mActivities.get(position).lastUser);
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mSubject;
            public final TextView mDueDate;
            public final TextView mLastUser;

            public ViewHolder(View view) {
                super(view);
                mView = view;
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
