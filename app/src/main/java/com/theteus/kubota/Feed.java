package com.theteus.kubota;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.RetrieveService;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;
import org.json.JSONObject;


public class Feed extends Fragment {
    public Feed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        retrieve();
        // Inflate the layout for this fragment
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
                Log.i("ON FEED LOAD", result.toString());
            }
        })
                .setEntity("ServiceActivity").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
