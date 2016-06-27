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
import com.theteus.kubota.Reference;

import java.sql.Ref;
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
            ((TextView) view.findViewById(R.id.lead_detail_follow)).setText(Reference.MASTER_FOLLOWSTATUS.get(mLead.followStatus));
            ((TextView) view.findViewById(R.id.lead_detail_area)).setText(mLead.area + " ไร่");
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
        if(Reference.MASTER_INTERESTPRODUCT.containsKey(mLead.product1)) count++;
        if(Reference.MASTER_INTERESTPRODUCT.containsKey(mLead.product2)) count++;
        if(Reference.MASTER_INTERESTPRODUCT.containsKey(mLead.product3)) count++;
        if(Reference.MASTER_INTERESTPRODUCT.containsKey(mLead.product4)) count++;
        if(Reference.MASTER_INTERESTPRODUCT.containsKey(mLead.product5)) count++;
        return count;
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
        private final LeadInstance mLead;
        private final List<Integer> product;
        private final List<String> model;

        public ItemAdapter(LeadInstance lead) {
            this.mLead = lead;
            product = new ArrayList<>();
            model = new ArrayList<>();
            product.add(mLead.product1);
            product.add(mLead.product2);
            product.add(mLead.product3);
            product.add(mLead.product4);
            product.add(mLead.product5);
            model.add(mLead.model1);
            model.add(mLead.model2);
            model.add(mLead.model3);
            model.add(mLead.model4);
            model.add(mLead.model5);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lead_detail_item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if(Reference.MASTER_INTERESTPRODUCT.containsKey(product.get(position))) {
                holder.mProduct.setText(Reference.MASTER_INTERESTPRODUCT.get(product.get(position)));
                holder.mModel.setText(model.get(position));
            }
            else {
                holder.mProduct.setText("- - -");
                holder.mModel.setText("- - -");
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mProduct;
            public final TextView mModel;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mProduct = (TextView) view.findViewById(R.id.item_type);
                mModel = (TextView) view.findViewById(R.id.item_model);
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
