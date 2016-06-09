package com.theteus.kubota.skcmodule;

/**
 * Created by user on 6/9/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.theteus.kubota.R;

import java.util.ArrayList;

public class CustomAdapterSKC extends BaseAdapter {
    Context mContext;
    ArrayList<SKCInstance> list;

    public CustomAdapterSKC(Context context, ArrayList<SKCInstance> list) {
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

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.skc_listview_row, parent, false);

        TextView nameText = (TextView)view.findViewById(R.id.skc_text_name);
        nameText.setText(list.get(position).getName());

        TextView telText = (TextView)view.findViewById(R.id.skc_text_tel);
        telText.setText(list.get(position).getTel());

        TextView emailText = (TextView)view.findViewById(R.id.skc_text_email);
        emailText.setText(list.get(position).getEmail());

        final CheckBox selector = (CheckBox)view.findViewById(R.id.skc_checkbox);
        final View finalView = view;
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(finalView.getContext(), "TEST: "+list.get(position).getName()+" Status"+selector.isChecked(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
