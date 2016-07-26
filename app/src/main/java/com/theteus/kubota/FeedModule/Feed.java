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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Feed extends Fragment {
    public Feed() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView postActivity = (RecyclerView) view.findViewById(R.id.feed_activitylog);
        postActivity.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pageActivityAdapter adapter = new pageActivityAdapter();
        retrieve(postActivity, adapter, view);
        return view;
    }

    private void retrieve(final RecyclerView postActivity, final pageActivityAdapter adapter, final View view){
        final Map<String, Integer> dateMap = new HashMap<>();
        final Map<String, Integer> typeMap = new HashMap<>();
        new RetrieveService(getActivity(), new AsyncResponse() {
            @Override
            public void onFinishTask(JSONObject result) {
                try {
                    JSONArray result1 = result.getJSONObject("d").getJSONArray("results");
                    for(int i=0;i<result1.length();i++){
                        JSONObject jsObj = result1.getJSONObject(i);
                        Timestamp stamp = new Timestamp(Long.valueOf(jsObj.get("CreatedOn").toString().replaceAll("[/Date()]","")));
                        Date date = new Date(stamp.getTime());
                        JSONObject regardingObject = jsObj.getJSONObject("RegardingObjectId");
                        JSONObject createdByObject = jsObj.getJSONObject("CreatedOnBehalfBy");

                        String dateObj = date.toString();
                        if(dateMap.containsKey(dateObj)) dateMap.put(dateObj, dateMap.get(dateObj) + 1);
                        else dateMap.put(dateObj, 1);

                        String typeName = regardingObject.get("LogicalName").toString();
                        if(typeMap.containsKey(typeName)) typeMap.put(typeName, typeMap.get(typeName)+1);
                        else typeMap.put(typeName, 1);

                        adapter.addData(String.valueOf(regardingObject.get("Name"))
                                , "Created By " + String.valueOf(createdByObject.get("Name"))
                                , regardingObject.get("Id").toString()
                                , regardingObject.get("LogicalName").toString());
                    }
                } catch (JSONException e) {e.printStackTrace();}
                initPieChart(view, typeMap);
                initValueLine(view, dateMap);
                postActivity.setAdapter(adapter);
            }
        })
                .setEntity("Post").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initPieChart(View view, Map<String, Integer> typeMap){
        int colorBias = 0;
        PieChart mPieChart = (PieChart) view.findViewById(R.id.piechart);
        for (Object o : typeMap.entrySet()) {
            HashMap.Entry pair = (HashMap.Entry) o;
            String output = pair.getKey().toString().substring(0, 1).toUpperCase() + pair.getKey().toString().substring(1);
            mPieChart.addPieSlice(new PieModel(output
                    , Integer.valueOf(pair.getValue().toString())
                    , Color.parseColor("#56B7F1") + colorBias));
            colorBias += 600;
        }
        mPieChart.startAnimation();
    }

    private void initValueLine(View view, Map<String, Integer> dateMap){
        ValueLineChart mCubicValueLineChart = (ValueLineChart) view.findViewById(R.id.cubiclinechart);
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFFFFC107);
        Map<String, Integer> map = new TreeMap<>(dateMap);
        Iterator it = map.entrySet().iterator();
        series.addPoint(new ValueLinePoint(" ", 0));
        while(it.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)it.next();
            series.addPoint(new ValueLinePoint(pair.getKey().toString(), Float.valueOf(pair.getValue().toString())));
        }
        series.addPoint(new ValueLinePoint(" ", 0));
        mCubicValueLineChart.addSeries(series);
        mCubicValueLineChart.startAnimation();
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
