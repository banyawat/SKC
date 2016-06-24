package com.theteus.kubota.SKCModule;


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

public class SKCForm03 extends Fragment {
    Spinner jobSpinner, goodsSpinner, agriTypeSpinner, interestSpinner,harvestMethodSpinner, riceMethodSpinner;
    EditText goodsAreaEdit, incomeEdit;
    TextView limitText;
    Button menu1Button, menu2Button, addButton;
    ArrayList<PageInformation> pageList;

    RecyclerView recyclerView;

    int pageID=0;

    listAdapter adapter;

    public SKCForm03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skcform03, container, false);

        pageList = new ArrayList<>();
        pageList.add(new PageInformation());
        pageList.add(new PageInformation());
        findViewsByID(view);
        initSpinner(view);
        initInterestList(view);
        initAddButton();
        initMenu();

        return view;
    }

    private void findViewsByID(View view){
        jobSpinner = (Spinner) view.findViewById(R.id.skc_form3_job);
        goodsSpinner = (Spinner) view.findViewById(R.id.skc_form3_goods);
        goodsAreaEdit = (EditText) view.findViewById(R.id.skc_form3_agriarea);
        interestSpinner = (Spinner) view.findViewById(R.id.skc_form3_interestproduct);
        agriTypeSpinner = (Spinner)view.findViewById(R.id.skc_form3_agritype);
        harvestMethodSpinner = (Spinner)view.findViewById(R.id.skc_form3_harvestmethod);
        riceMethodSpinner = (Spinner)view.findViewById(R.id.skc_form3_ricemethod);
        incomeEdit = (EditText) view.findViewById(R.id.skc_form3_income);
        menu1Button = (Button) view.findViewById(R.id.skc_form3_menu1);
        menu2Button = (Button) view.findViewById(R.id.skc_form3_menu2);
        addButton = (Button) view.findViewById(R.id.skc_form3_addbutton);
        limitText = (TextView) view.findViewById(R.id.skc_form3_limit);
    }

    private void initMenu(){
        menu1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(pageID){
                    case 0:
                        return;
                    case 1:
                        saveStatus();
                        break;
                    default:
                        break;
                }
                pageID=0;
                restoreStatus();
            }
        });

        menu2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(pageID){
                    case 0:
                        saveStatus();
                        break;
                    case 1:
                        return;
                    default:
                        break;
                }
                pageID=1;
                restoreStatus();
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
        interestSpinner.setAdapter(interestAdapter);
    }

    private void initInterestList(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.skc_form3_listview);
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
                                limitText.setText(adapter.getItemCount()+"/5");
                                jobSpinner.setEnabled(true);
                                goodsSpinner.setEnabled(true);
                                goodsAreaEdit.setEnabled(true);
                                interestSpinner.setEnabled(true);
                                addButton.setBackgroundResource(R.drawable.button_shape_inactive);
                                addButton.setEnabled(true);
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

    private void initAddButton(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobStr = jobSpinner.getSelectedItem().toString();
                String goodsStr = goodsSpinner.getSelectedItem().toString();
                String agriArea = goodsAreaEdit.getText().toString();
                String interestStr = interestSpinner.getSelectedItem().toString();
                if(agriArea.length()>0&&interestStr.length()>0) {
                    adapter.addData(new surveyInstance(jobStr, goodsStr, agriArea, interestStr));
                    adapter.notifyDataSetChanged();
                    goodsAreaEdit.setText("");
                    goodsAreaEdit.setError(null);
                    limitText.setText(adapter.getItemCount() + "/5");
                    if (adapter.getItemCount() == 5) {
                        jobSpinner.setEnabled(false);
                        goodsSpinner.setEnabled(false);
                        goodsAreaEdit.setEnabled(false);
                        interestSpinner.setEnabled(false);
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

    private void clearInfo(){
        jobSpinner.setSelection(0);
        goodsSpinner.setSelection(0);
        agriTypeSpinner.setSelection(0);
        harvestMethodSpinner.setSelection(0);
        riceMethodSpinner.setSelection(0);
        goodsAreaEdit.setText("");
        interestSpinner.setSelection(0);
        incomeEdit.setText("");
        goodsAreaEdit.setError(null);
        incomeEdit.setError(null);
    }

    private void saveStatus(){
        pageList.get(pageID).setPage(jobSpinner, goodsSpinner, agriTypeSpinner, harvestMethodSpinner,
                 riceMethodSpinner,  goodsAreaEdit,  interestSpinner,  incomeEdit, adapter);
        clearInfo();
    }

    private void restoreStatus(){
        pageList.get(pageID).getPage(jobSpinner, goodsSpinner, agriTypeSpinner, harvestMethodSpinner,
                riceMethodSpinner,  goodsAreaEdit,  interestSpinner,  incomeEdit, adapter);
    }

    private class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolder> {
        ArrayList<surveyInstance> list;

        public listAdapter() {
            list = new ArrayList<>();
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

    private class PageInformation {
        ArrayList<surveyInstance> savedList;
        int jobChoice, goodsChoice, agriTypeChoice,
                harvestChoice, riceChoice, interestProductChoice;
        String agriAreaStr, incomeStr;
        PageInformation(){
            savedList = new ArrayList<>();
        }

        public void setPage(Spinner jobSpinner,Spinner goodsSpinner,Spinner agriTypeSpinner,Spinner harvestMethodSpinner,
                               Spinner riceMethodSpinner, EditText goodsAreaEdit, Spinner interestSpinner, EditText incomeEdit,
                            listAdapter adapter){
            jobChoice = jobSpinner.getSelectedItemPosition();
            goodsChoice = goodsSpinner.getSelectedItemPosition();
            agriTypeChoice = agriTypeSpinner.getSelectedItemPosition();
            harvestChoice = harvestMethodSpinner.getSelectedItemPosition();
            riceChoice = riceMethodSpinner.getSelectedItemPosition();
            agriAreaStr = goodsAreaEdit.getText().toString();
            interestProductChoice = interestSpinner.getSelectedItemPosition();
            incomeStr = incomeEdit.getText().toString();
            savedList = adapter.list;
        }

        public void getPage(Spinner jobSpinner,Spinner goodsSpinner,Spinner agriTypeSpinner,Spinner harvestMethodSpinner,
                            Spinner riceMethodSpinner, EditText goodsAreaEdit, Spinner interestSpinner, EditText incomeEdit, listAdapter adapter){
            jobSpinner.setSelection(jobChoice);
            goodsSpinner.setSelection(goodsChoice);
            agriTypeSpinner.setSelection(agriTypeChoice);
            harvestMethodSpinner.setSelection(harvestChoice);
            riceMethodSpinner.setSelection(riceChoice);
            goodsAreaEdit.setText(agriAreaStr);
            interestSpinner.setSelection(interestProductChoice);
            incomeEdit.setText(incomeStr);
            adapter.list = savedList;
            adapter.notifyDataSetChanged();
        }
    }

}
