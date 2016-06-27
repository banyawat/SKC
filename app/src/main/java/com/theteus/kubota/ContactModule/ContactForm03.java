package com.theteus.kubota.ContactModule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.theteus.kubota.R;

import java.util.ArrayList;

public class ContactForm03 extends Fragment {
    Spinner jobSpinner, goodsSpinner, interestProductSpinner, agriTypeSpinner, harvestMethodSpinner, riceMethodSpinner;
    EditText goodsAreaEdit, incomeEdit;
    TextView limitText;
    Button addButton;

    listAdapter adapter;

    public ContactForm03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_form03, container, false);

        findViewsByID(view);
        initSpinner(view);
        initInterestList((RecyclerView) view.findViewById(R.id.contact_form3_listview), view);
        initAddButton();

        return view;
    }

    private void initAddButton(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobStr = jobSpinner.getSelectedItem().toString();
                String goodsStr = goodsSpinner.getSelectedItem().toString();
                String agriArea = goodsAreaEdit.getText().toString();
                String interestStr = interestProductSpinner.getSelectedItem().toString();
                if(agriArea.length()>0&&interestStr.length()>0) {
                    adapter.addData(new surveyInstance(jobStr, goodsStr, agriArea, interestStr));
                    adapter.notifyDataSetChanged();
                    goodsAreaEdit.setText("");
                    goodsAreaEdit.setError(null);
                    limitText.setText(adapter.getItemCount() + "/5");
                    if (adapter.getItemCount() >= 5) {
                        jobSpinner.setEnabled(false);
                        goodsSpinner.setEnabled(false);
                        goodsAreaEdit.setEnabled(false);
                        interestProductSpinner.setEnabled(false);
                        addButton.setBackgroundResource(R.drawable.button_shape_inactive);
                        addButton.setEnabled(false);
                    }
                }
                else {
                    if(agriArea.length()==0)
                        goodsAreaEdit.setError("กรุณาใส่ข้อมูล");
                    else
                        goodsAreaEdit.setError(null);
                }
            }
        });
    }
    private void initSpinner(View view){
        ArrayAdapter<CharSequence> jobAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.job_choice, android.R.layout.simple_spinner_item);
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobSpinner.setAdapter(jobAdapter);

        ArrayAdapter<CharSequence> goodsAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.agri_choice, android.R.layout.simple_spinner_item);
        goodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goodsSpinner.setAdapter(goodsAdapter);

        ArrayAdapter<CharSequence> agriAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.agritype_choice, android.R.layout.simple_spinner_item);
        agriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agriTypeSpinner.setAdapter(agriAdapter);

        ArrayAdapter<CharSequence> harvestAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.harvest_choice, android.R.layout.simple_spinner_item);
        harvestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        harvestMethodSpinner.setAdapter(harvestAdapter);

        ArrayAdapter<CharSequence> riceAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.rice_choice, android.R.layout.simple_spinner_item);
        riceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        riceMethodSpinner.setAdapter(riceAdapter);

        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.interest_product, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestProductSpinner.setAdapter(interestAdapter);

    }
    private void findViewsByID(View view){
        jobSpinner = (Spinner) view.findViewById(R.id.contact_form3_job);
        goodsSpinner = (Spinner) view.findViewById(R.id.contact_form3_goods);
        goodsAreaEdit = (EditText) view.findViewById(R.id.contact_form3_agriarea);
        interestProductSpinner = (Spinner) view.findViewById(R.id.contact_form3_interestproduct);
        agriTypeSpinner = (Spinner)view.findViewById(R.id.contact_form3_agritype);
        harvestMethodSpinner = (Spinner)view.findViewById(R.id.contact_form3_harvestmethod);
        riceMethodSpinner = (Spinner)view.findViewById(R.id.contact_form3_ricemethod);
        incomeEdit = (EditText) view.findViewById(R.id.contact_form3_income);
        addButton = (Button) view.findViewById(R.id.contact_form3_addbutton);
        limitText = (TextView) view.findViewById(R.id.contact_form3_limit);
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
                                if(adapter.getItemCount()<5) {
                                    limitText.setText(adapter.getItemCount() + "/5");
                                    jobSpinner.setEnabled(true);
                                    goodsSpinner.setEnabled(true);
                                    goodsAreaEdit.setEnabled(true);
                                    interestProductSpinner.setEnabled(true);
                                    addButton.setBackgroundResource(R.drawable.button_shape_active);
                                    addButton.setEnabled(true);
                                }
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
        limitText.setText(adapter.getItemCount()+"/5");
    }
    private class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder> {
        ArrayList<surveyInstance> list;

        public listAdapter() { list = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.skc_form3_list_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.JobsText.setText(list.get(position).getJobs());
            holder.GoodsText.setText(list.get(position).getGoods());
            holder.agriAreaText.setText(list.get(position).getAgriArea());
            holder.interestProductText.setText(list.get(position).getInterestProduct());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void remove(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }

        public void addData(surveyInstance item) {
            list.add(item);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView JobsText, GoodsText, agriAreaText, interestProductText;

            MyViewHolder(View view) {
                super(view);
                JobsText = (TextView) view.findViewById(R.id.skc_list_job);
                GoodsText = (TextView) view.findViewById(R.id.skc_list_goods);
                agriAreaText = (TextView) view.findViewById(R.id.skc_list_agriarea);
                interestProductText = (TextView) view.findViewById(R.id.skc_list_interest);
            }
        }
    }
    private class surveyInstance {
        private String Jobs, Goods, agriArea, interestProduct;
        public surveyInstance(String Jobs, String Goods, String agriArea, String interestProduct){
            this.Jobs = Jobs;
            this.Goods = Goods;
            this.agriArea = agriArea;
            this.interestProduct = interestProduct;
        }

        public String getJobs() {
            return Jobs;
        }

        public String getGoods() {
            return Goods;
        }

        public String getAgriArea() {
            return agriArea;
        }

        public String getInterestProduct() {
            return interestProduct;
        }
    }

}
