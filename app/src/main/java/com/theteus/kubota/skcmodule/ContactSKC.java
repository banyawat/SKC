package com.theteus.kubota.skcmodule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.theteus.kubota.R;

import java.util.ArrayList;

public class ContactSKC extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView skcListview;
    CustomAdapterSKC adapter;
    ArrayList<SKCInstance> dataList;

    public ContactSKC() {
        // Required empty public constructor
    }

    public static ContactSKC newInstance(String param1, String param2) {
        ContactSKC fragment = new ContactSKC();
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
        View view = inflater.inflate(R.layout.fragment_contact_skc, container, false);

        //Dummy data
        dataList = new ArrayList<SKCInstance>();
        dataList.add(new SKCInstance("Banyawat Kaewsamer", "0913067637", "bunyawat.38@gmail.com"));
        dataList.add(new SKCInstance("John Cartney", "+419523115", "elitan_842@gmail.com"));
        dataList.add(new SKCInstance("Jack Ma", "+61155149712", "maj_j12@hotmail.com"));

        adapter = new CustomAdapterSKC(getActivity(), dataList);
        skcListview = (ListView) view.findViewById(R.id.skc_listview);
        skcListview.setAdapter(adapter);

        skcListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "TSET", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton removeFab = (FloatingActionButton) view.findViewById(R.id.skc_fab_remove);
        removeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCheckedArray();
            }
        });
        return view;
    }
}
