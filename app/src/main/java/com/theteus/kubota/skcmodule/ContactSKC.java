package com.theteus.kubota.skcmodule;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.theteus.kubota.R;

import java.util.ArrayList;

public class ContactSKC extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ListView skcListview;
    CustomAdapterSKC adapter;
    ArrayList<SKCInstance> dataList;

    ViewPager mViewPager;

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
        final View view = inflater.inflate(R.layout.fragment_contact_skc, container, false);

        //Dummy data
        dataList = new ArrayList<SKCInstance>();
        dataList.add(new SKCInstance("Banyawat Kaewsamer", "0913067637", "bunyawat.38@gmail.com"));
        dataList.add(new SKCInstance("John Cartney", "+419523115", "elitan_842@gmail.com"));
        dataList.add(new SKCInstance("Jack Ma", "+61155149712", "maj_j12@hotmail.com"));

        adapter = new CustomAdapterSKC(getActivity(), dataList);
        skcListview = (ListView) view.findViewById(R.id.skc_listview);
        skcListview.setAdapter(adapter);

        /*FloatingActionButton removeFab = (FloatingActionButton) view.findViewById(R.id.skc_fab_remove);
        removeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getCheckedCount()!=0) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(view.getContext());
                    builder.setMessage("ลบข้อมูล?");
                    builder.setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteCheckedArray();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });*/

        FloatingActionButton addFab = (FloatingActionButton) view.findViewById(R.id.skc_fab_add);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });
        return view;
    }

    public void addView(ViewPager mViewPager){
        this.mViewPager = mViewPager;
    }
}
