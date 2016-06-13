package com.theteus.kubota.skcmodule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.theteus.kubota.R;
import java.util.ArrayList;

public class CustomAdapterSKC extends RecyclerView.Adapter<CustomAdapterSKC.MyViewHolder> {
    ArrayList<SKCInstance> list;

    public CustomAdapterSKC(ArrayList<SKCInstance> list) {
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skc_listview_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textName.setText(list.get(position).getName());
        holder.textTel.setText(list.get(position).getTel());
        holder.textEmail.setText(list.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textTel, textEmail;
        MyViewHolder(View view) {
            super(view);
            textName = (TextView) view.findViewById(R.id.skc_list_name);
            textTel = (TextView) view.findViewById(R.id.skc_list_tel);
            textEmail = (TextView) view.findViewById(R.id.skc_list_email);
        }
    }
}
