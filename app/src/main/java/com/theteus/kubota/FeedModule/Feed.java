package com.theteus.kubota.FeedModule;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    RecyclerView postActivity;
    pageActivityAdapter adapter;
    public Feed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        postActivity = (RecyclerView) view.findViewById(R.id.feed_activitylog);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        postActivity.setLayoutManager(mLayoutManager);
        adapter = new pageActivityAdapter();
        retrieve();

        PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);

        mPieChart.addPieSlice(new PieModel("SKC Customer", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Customer", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Lead", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Acitivity", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();

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
        return view;
    }

    private void retrieve(){
        new RetrieveService(getActivity(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                try {
                    JSONArray result1 = result.getJSONObject("d").getJSONArray("results");
                    for(int i=0;i<result1.length();i++){
                        JSONObject jsObj = result1.getJSONObject(i);
                        JSONObject regardingObject = jsObj.getJSONObject("RegardingObjectId");
                        JSONObject createdByObject = jsObj.getJSONObject("CreatedOnBehalfBy");
                        adapter.addData(String.valueOf(regardingObject.get("Name")), "Created By " + String.valueOf(createdByObject.get("Name")));
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
        public  pageActivityAdapter(){
            post = new ArrayList<>();
            post2 = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feed_post_row, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.longText.setText(post.get(position));
            holder.longText2.setText(post2.get(position));
        }

        @Override
        public int getItemCount() {
            return post.size();
        }

        public void addData(String data, String data2){
            post.add(data);
            post2.add(data2);
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
