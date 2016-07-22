package com.theteus.kubota.FeedModule;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.theteus.kubota.Home;
import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.RetrieveService;
import com.theteus.kubota.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Feed extends Fragment {
    public Feed() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView postActivity = (RecyclerView) view.findViewById(R.id.feed_activitylog);
        postActivity.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pageActivityAdapter adapter = new pageActivityAdapter();
        retrieve(postActivity, adapter);
        initPieChart(view);
        initValueLine(view);
        return view;
    }

    private void initPieChart(View view){
        PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);
        mPieChart.addPieSlice(new PieModel("SKC Customer", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Customer", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Lead", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Acitivity", 9, Color.parseColor("#FED70E")));
        mPieChart.startAnimation();
    }
    private void initValueLine(View view){
        ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        series.addPoint(new ValueLinePoint("Jan", 2.4f));
        series.addPoint(new ValueLinePoint("Feb", 3.4f));
        series.addPoint(new ValueLinePoint("Mar", .4f));
        series.addPoint(new ValueLinePoint("Apr", 1.2f));
        series.addPoint(new ValueLinePoint("Mai", 2.6f));
        series.addPoint(new ValueLinePoint("Jun", 1.0f));
        series.addPoint(new ValueLinePoint("Jul", 3.5f));
        series.addPoint(new ValueLinePoint("Aug", 2.4f));
        series.addPoint(new ValueLinePoint("Sep", 2.4f));
        series.addPoint(new ValueLinePoint("Oct", 3.4f));
        series.addPoint(new ValueLinePoint("Nov", .4f));
        series.addPoint(new ValueLinePoint("Dec", 1.3f));
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
    }
    private void retrieve(final RecyclerView postActivity, final pageActivityAdapter adapter){
        new RetrieveService(getActivity(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                try {
                    JSONArray result1 = result.getJSONObject("d").getJSONArray("results");
                    for(int i=0;i<result1.length();i++){
                        JSONObject jsObj = result1.getJSONObject(i);
                        JSONObject regardingObject = jsObj.getJSONObject("RegardingObjectId");
                        JSONObject createdByObject = jsObj.getJSONObject("CreatedOnBehalfBy");
                        Log.i("RETRIEVE", "."+regardingObject.get("LogicalName").toString());
                        adapter.addData(String.valueOf(regardingObject.get("Name"))
                                , "Created By " + String.valueOf(createdByObject.get("Name"))
                                , regardingObject.get("Id").toString()
                                , regardingObject.get("LogicalName").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                postActivity.setAdapter(adapter);
            }
        })
                .setEntity("Post").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private class pageActivityAdapter extends RecyclerView.Adapter<pageActivityAdapter.MyViewHolder>{
        ArrayList<String> post;
        ArrayList<String> post2;
        ArrayList<String> idList;
        ArrayList<String> logicalNameList;
        public  pageActivityAdapter(){
            post = new ArrayList<>();
            post2 = new ArrayList<>();
            idList = new ArrayList<>();
            logicalNameList = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_post_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.longText.setText(post.get(position));
            holder.longText2.setText(post2.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(logicalNameList.get(position).equals("account")) {
                        Home home = (Home) getActivity();
                        home.goTo(5, idList.get(position));
                    }
                    else
                        Toast.makeText(getContext(), "Service not yet available!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return post.size();
        }

        public void addData(String data, String data2,String id, String logicalName){
            this.post.add(data);
            this.post2.add(data2);
            this.idList.add(id);
            this.logicalNameList.add(logicalName);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView longText,longText2;
            public MyViewHolder(View itemView) {
                super(itemView);
                longText = (TextView) itemView.findViewById(R.id.feed_list_text);
                longText2 = (TextView) itemView.findViewById(R.id.feed_list_text2);
            }
        }
    }
}
