package com.theteus.kubota.LeadModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.theteus.kubota.R;

import java.util.ArrayList;

public class Lead extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Mock-Up Dataset
    // TODO: Remove when actual retrieval function is coded
    private static final String[] leadID = new String[] {"L0001", "L0002", "L0003", "L0004"};
    private static final String[] leadName = new String[] {"Jack Ripper", "Hirohito", "Muhammad Issac", "Authur Pendragon"};
    private static final String[] leadPhone = new String[] {"000-000-xxxx", "000-000-xxxx", "000-000-xxxx", "000-000-xxxx"};
    private static final String[] leadEmail = new String[] {"this@email.com", "that@email.com", "those@email.com", "these@email.com"};
    private static final String[] leadRegisterDate = new String[] {"2016-02-01", "2016-02-04", "2016-02-28", "2016-03-15"};
    private static final String[] accountID = new String[] {"A0003", "A0001", "A0002", "A0001"};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Lead() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Lead.
     */
    // TODO: Rename and change types and number of parameters
    public static Lead newInstance(String param1, String param2) {
        Lead fragment = new Lead();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lead, container, false);

        //ArrayAdapter<String> leadAdapter = new ArrayAdapter<>(getActivity(), R.layout.fragment_lead, R.id.lead_listview, leadID);
        //ListView leadListView = (ListView) view.findViewById(R.id.lead_listview);
        //leadListView.setAdapter(leadAdapter);

        ArrayList<LeadInstance> leadList = new ArrayList<LeadInstance>();
        for(int i = 0; i < leadID.length; i++) leadList.add(new LeadInstance(leadID[i], leadName[i], leadPhone[i], leadEmail[i], leadRegisterDate[i], accountID[i]));
        final LeadAdapter adapter = new LeadAdapter(getActivity(), leadList);
        ListView leadListView = (ListView) view.findViewById(R.id.lead_listview);
        //leadListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        leadListView.setAdapter(adapter);
        /*leadListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                //... //Anything
            }
        });*/

        return view;
    }
}
