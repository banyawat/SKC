package com.theteus.kubota.LeadModule;

/**
 * Created by whorangester on 6/9/16.
 */
import com.theteus.kubota.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.graphics.Color;

import java.util.*;

public class LeadAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<LeadInstance> list;

    public LeadAdapter(Context context, ArrayList<LeadInstance> list) {
        this.mContext= context;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Color lightGray = new Color();
        if(view == null)
            view = mInflater.inflate(R.layout.lead_listview_row, parent, false);
        TextView nameText = (TextView)view.findViewById(R.id.lead_name);
        nameText.setText(list.get(position).getName());

        TextView phoneText = (TextView)view.findViewById(R.id.lead_phone);
        phoneText.setText(list.get(position).getPhone());

        TextView emailText = (TextView)view.findViewById(R.id.lead_email);
        emailText.setText(list.get(position).getEmail());

        TextView dateText = (TextView)view.findViewById(R.id.lead_date);
        dateText.setText(list.get(position).getRegisterDate());

        TextView accountText = (TextView)view.findViewById(R.id.lead_account);
        accountText.setText(list.get(position).getAccountId());

        return view;
    }

}
