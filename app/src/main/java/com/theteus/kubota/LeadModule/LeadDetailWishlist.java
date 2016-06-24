package com.theteus.kubota.LeadModule;

import android.content.ClipData;
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

import com.theteus.kubota.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whorangester on 6/21/16.
 */
public class LeadDetailWishlist extends Fragment {
    private LeadInstance mLead;
    ItemAdapter itemAdapter;
    RecyclerView recyclerView;

    public LeadDetailWishlist() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(LeadDetailMain.ARG_PARAM1)) {
            mLead = DummyLeadInstance.LEAD_MAP.get(getArguments().getString(LeadDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_detail_wishlist, container, false);

        if(mLead != null) {
            ((TextView) view.findViewById(R.id.lead_detail_follow)).setText(mLead.followingStatus);
            ((TextView) view.findViewById(R.id.lead_detail_area)).setText(mLead.area);
            ((TextView) view.findViewById(R.id.lead_detail_listlimit)).setText(itemCounter() + " / 5");

            itemAdapter = new ItemAdapter(mLead);
            View itemListView = view.findViewById(R.id.lead_detail_wishlist);
            assert itemListView != null;
            recyclerView = (RecyclerView) itemListView;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(itemAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);


        }

        return view;
    }

    public int itemCounter() {
        int count = 0;
        if(!mLead.itemType1.equals("- - -")) count++;
        if(!mLead.itemType2.equals("- - -")) count++;
        if(!mLead.itemType3.equals("- - -")) count++;
        if(!mLead.itemType4.equals("- - -")) count++;
        if(!mLead.itemType5.equals("- - -")) count++;
        return count;
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        private final LeadInstance mLead;
        private final List<String> itemType;
        private final List<String> itemModel;

        public ItemAdapter(LeadInstance lead) {
            this.mLead = lead;
            itemType = new ArrayList<>();
            itemModel = new ArrayList<>();
            itemType.add(mLead.itemType1);
            itemType.add(mLead.itemType2);
            itemType.add(mLead.itemType3);
            itemType.add(mLead.itemType4);
            itemType.add(mLead.itemType5);
            itemModel.add(mLead.itemModel1);
            itemModel.add(mLead.itemModel2);
            itemModel.add(mLead.itemModel3);
            itemModel.add(mLead.itemModel4);
            itemModel.add(mLead.itemModel5);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lead_detail_item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItemType.setText(itemType.get(position));
            holder.mItemModel.setText(itemModel.get(position));
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mItemType;
            public final TextView mItemModel;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mItemType = (TextView) view.findViewById(R.id.item_type);
                mItemModel = (TextView) view.findViewById(R.id.item_model);
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
