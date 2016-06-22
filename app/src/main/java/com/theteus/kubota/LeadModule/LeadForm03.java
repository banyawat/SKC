package com.theteus.kubota.LeadModule;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.theteus.kubota.R;
import com.theteus.kubota.skcmodule.CustomAdapterSKC;
import com.theteus.kubota.skcmodule.SKCInstance;

import java.util.ArrayList;

public class LeadForm03 extends Fragment {
    Spinner interestProductSpinner,customerStatusSpinner;
    EditText interestSeriesEdit, agriAreaEdit;
    Button addButton;
    TextView limitInterestText;
    listAdapter adapter;

    public LeadForm03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lead_form03, container, false);
        findViewsbyID(view);
        initInterestProductSpinner(view);
        initCustomerStatusSpinner(view);
        initInterestList((RecyclerView) view.findViewById(R.id.lead_form3_interestlist), view);
        initAddButton(view);
        return view;
    }

    private void findViewsbyID(View view){
        interestProductSpinner = (Spinner) view.findViewById(R.id.lead_form3_interestproduct);
        customerStatusSpinner = (Spinner) view.findViewById(R.id.lead_form3_customerstatus);
        addButton = (Button) view.findViewById(R.id.lead_form3_add);
        limitInterestText = (TextView) view.findViewById(R.id.lead_form3_listlimit);
        interestSeriesEdit = (EditText) view.findViewById(R.id.lead_form3_interestseries);
        agriAreaEdit = (EditText) view.findViewById(R.id.lead_form3_agriarea);
    }

    private void initAddButton(final View view){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interestStr = interestProductSpinner.getSelectedItem().toString();
                String seriesStr = interestSeriesEdit.getText().toString();
                Log.i("TAG", "String: "+seriesStr.length());
                if(seriesStr.length()>2) {
                    adapter.addData(new leadInstance(interestStr, seriesStr));
                    adapter.notifyDataSetChanged();
                    interestSeriesEdit.setText("");
                    limitInterestText.setText(adapter.getItemCount() + "/5");
                    if (adapter.getItemCount() == 5) {
                        interestProductSpinner.setEnabled(false);
                        interestSeriesEdit.setEnabled(false);
                        addButton.setBackgroundResource(R.drawable.button_shape_inactive);
                        addButton.setEnabled(false);
                    }
                }
                else
                    interestSeriesEdit.setError("กรุณาใส่ข้อมูล");
            }
        });
    }

    private void initInterestProductSpinner(View view){
        ArrayAdapter<CharSequence> interestProductAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.interest_product, android.R.layout.simple_spinner_item);
        interestProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestProductSpinner.setAdapter(interestProductAdapter);
    }

    private void initCustomerStatusSpinner(View view){
        ArrayAdapter<CharSequence> customerStatusAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.customer_status, android.R.layout.simple_spinner_item);
        customerStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerStatusSpinner.setAdapter(customerStatusAdapter);
    }

    private void initInterestList(RecyclerView recyclerView,View view){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new listAdapter();
        recyclerView.setAdapter(adapter);
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onPendingDismiss(RecyclerViewAdapter recyclerView, int position) {

                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, final int position) {
                                adapter.remove(position);
                                limitInterestText.setText(adapter.getItemCount()+"/5");
                                interestProductSpinner.setEnabled(true);
                                addButton.setEnabled(true);
                                addButton.setBackgroundResource(R.drawable.button_shape_active);
                                interestSeriesEdit.setEnabled(true);
                            }
                        });
        touchListener.setDismissDelay(1500);
        recyclerView.setOnTouchListener(touchListener);
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(view.getContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.lead_txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.lead_txt_undo) {
                            touchListener.undoPendingDismiss();
                        }
                    }
                }));
        limitInterestText.setText(adapter.getItemCount()+"/5");
    }

    private class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder> {
        ArrayList<leadInstance> list;

        public listAdapter() {
            list = new ArrayList<leadInstance>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lead_form3_list_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.interestText.setText(list.get(position).getInterest());
            holder.seriesText.setText(list.get(position).getSeries());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void remove(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }

        public void addData(leadInstance item){
            list.add(item);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView interestText, seriesText;
            MyViewHolder(View view) {
                super(view);
                interestText = (TextView) view.findViewById(R.id.lead_list_interest);
                seriesText = (TextView) view.findViewById(R.id.lead_list_series);
            }
        }
    }

    private class leadInstance{
        String Series,Interest;
        leadInstance(String Interest, String Series){
            this.Interest = Interest;
            this.Series = Series;
        }

        public String getInterest(){
            return this.Interest;
        }

        public String getSeries(){
            return this.Series;
        }
    }
}
