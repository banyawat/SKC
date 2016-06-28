package com.theteus.kubota.ContactModule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.TestMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whorangester on 6/28/16.
 */
public class ContactDetailQuestionnaire extends Fragment {
    private ContactInstance mContact;
    QuestionnaireAdapter qAdapter;
    RecyclerView recyclerView;

    public ContactDetailQuestionnaire() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ContactDetailMain.ARG_PARAM1)) {
            mContact = DummyContactInstance.CONTACT_MAP.get(getArguments().getString(ContactDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail_questionnaire, container, false);

        if(mContact != null) {
            ((TextView) view.findViewById(R.id.contact_detail_land_owner)).setText(Reference.MASTER_LANDOWNER.get(mContact.landOwner));
            ((TextView) view.findViewById(R.id.contact_detail_harvest)).setText(Reference.MASTER_HARVESTMETHOD.get(mContact.harvestMethod));
            ((TextView) view.findViewById(R.id.contact_detail_farming)).setText(Reference.MASTER_FARMINGMETHOD.get(mContact.farmingMethod));
            ((TextView) view.findViewById(R.id.contact_detail_income)).setText(mContact.yearlyIncome + " บาท");
            ((TextView) view.findViewById(R.id.contact_detail_listlimit)).setText(itemCounter() + " / 5");

            qAdapter = new QuestionnaireAdapter(mContact);
            View itemListView = view.findViewById(R.id.contact_detail_questionnaire);
            assert itemListView != null;
            recyclerView = (RecyclerView) itemListView;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(qAdapter);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
        }

        return view;
    }

    public int itemCounter() {
        int count = 0;
        if(Reference.MASTER_OCCUPATION.containsKey(mContact.occupation1)) count++;
        if(Reference.MASTER_OCCUPATION.containsKey(mContact.occupation2)) count++;
        if(Reference.MASTER_OCCUPATION.containsKey(mContact.occupation3)) count++;
        if(Reference.MASTER_OCCUPATION.containsKey(mContact.occupation4)) count++;
        if(Reference.MASTER_OCCUPATION.containsKey(mContact.occupation5)) count++;
        return count;
    }

    public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.ViewHolder> {
        private final ContactInstance mContact;
        private final List<Integer> occupation;
        private final List<Integer> plant;
        private final List<Double> area;
        private final List<Integer> product;

        public QuestionnaireAdapter(ContactInstance contact) {
            this.mContact = contact;
            occupation = new ArrayList<>();
            plant = new ArrayList<>();
            area = new ArrayList<>();
            product = new ArrayList<>();
            occupation.add(mContact.occupation1);
            occupation.add(mContact.occupation2);
            occupation.add(mContact.occupation3);
            occupation.add(mContact.occupation4);
            occupation.add(mContact.occupation5);
            plant.add(mContact.plant1);
            plant.add(mContact.plant2);
            plant.add(mContact.plant3);
            plant.add(mContact.plant4);
            plant.add(mContact.plant5);
            area.add(mContact.area1);
            area.add(mContact.area2);
            area.add(mContact.area3);
            area.add(mContact.area4);
            area.add(mContact.area5);
            product.add(mContact.product1);
            product.add(mContact.product2);
            product.add(mContact.product3);
            product.add(mContact.product4);
            product.add(mContact.product5);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_detail_questionnaire_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if(Reference.MASTER_OCCUPATION.containsKey(occupation.get(position))) {
                holder.mOccupation.setText(Reference.MASTER_OCCUPATION.get(occupation.get(position)));
                holder.mPlant.setText(Reference.MASTER_PLANT.get(plant.get(position)));
                holder.mArea.setText(area.get(position) + " ไร่");
                holder.mProduct.setText(Reference.MASTER_INTERESTPRODUCT.get(product.get(position)));
            }
            else {
                holder.mOccupation.setText("- - -");
                holder.mPlant.setText("- - -");
                holder.mArea.setText("- - -");
                holder.mProduct.setText("- - -");
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mOccupation;
            public final TextView mPlant;
            public final TextView mArea;
            public final TextView mProduct;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mOccupation = (TextView) view.findViewById(R.id.occupation);
                mPlant = (TextView) view.findViewById(R.id.plant);
                mArea = (TextView) view.findViewById(R.id.area);
                mProduct = (TextView) view.findViewById(R.id.product);
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
