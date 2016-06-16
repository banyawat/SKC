package com.theteus.kubota.skcmodule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.theteus.kubota.R;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class ContactSKC extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ArrayList<SKCInstance> dataList;
    private static final int TIME_TO_AUTOMATICALLY_DISMISS_ITEM = 1500;

    CustomAdapterSKC adapter;

    ViewPager mViewPager;
    View view;

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
        view = inflater.inflate(R.layout.fragment_contact_skc, container, false);
        //Dummy data
        dataList = new ArrayList<SKCInstance>();
        dataList.add(new SKCInstance("Banyawat Kaewsamer", "0913067637", "bunyawat.38@gmail.com"));
        dataList.add(new SKCInstance("John Cartney", "+419523115", "elitan_842@gmail.com"));
        dataList.add(new SKCInstance("Jack Ma", "+61155149712", "maj_j12@hotmail.com"));

        FloatingActionButton addFab = (FloatingActionButton) view.findViewById(R.id.skc_fab_add);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });

        init((RecyclerView) view.findViewById(R.id.skc_listview));

        return view;
    }

    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CustomAdapterSKC(dataList);
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
                            }
                        });
        touchListener.setDismissDelay(TIME_TO_AUTOMATICALLY_DISMISS_ITEM);
        recyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(ContactSKC.this.getContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        }
                    }
                }));
    }

    public void setView(ViewPager mViewPager){
        this.mViewPager = mViewPager;
    }

    public void addItem(SKCInstance item){
        adapter.addData(item);
        adapter.notifyDataSetChanged();
    }
}
