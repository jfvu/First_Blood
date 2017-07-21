package com.example.jf.cardapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jf.cardapp.R;

import java.util.ArrayList;


/**
 * Created by jf on 2017/2/22.
 */

public class TextSeachRecyclerViewAdapter extends RecyclerView.Adapter<TextSeachRecyclerViewAdapter.MyViewHolder> {

    /*private int item = RefreshActivity.selectedA.size()+RefreshActivity.selectedC.size()+RefreshActivity.selectedS.size()+RefreshActivity.selectedT.size()
            +RefreshActivity.selectedF.size()+RefreshActivity.selectedCo.size()+RefreshActivity.selectedTo.size();*/


    public TextSeachRecyclerViewAdapter(Context context, ArrayList selected) {
        this.context = context;
        this.selected = selected;
    }

    private Context context;
    private ArrayList<String> selected;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_textseach,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       /* if (RefreshActivity.selectedF.size()!=0){
            holder.textView.setText(RefreshActivity.selectedF.get(position));
        }
        if (RefreshActivity.selectedC.size()!=0){
            holder.textView.setText(RefreshActivity.selectedC.get(position-RefreshActivity.selectedF.size()));
        }
        if (RefreshActivity.selectedT.size()!=0){
            holder.textView.setText(RefreshActivity.selectedT.get(position-RefreshActivity.selectedF.size()-RefreshActivity.selectedC.size()));
        }
        if (RefreshActivity.selectedS.size()!=0){
            holder.textView.setText(RefreshActivity.selectedS.get(position-RefreshActivity.selectedF.size()-RefreshActivity.selectedC.size()-RefreshActivity.selectedT.size()));
        }
        if (RefreshActivity.selectedCo.size()!=0){
            holder.textView.setText(RefreshActivity.selectedCo.get(position-RefreshActivity.selectedF.size()-RefreshActivity.selectedC.size()-RefreshActivity.selectedT.size()-RefreshActivity.selectedS.size()));
        }
        if (RefreshActivity.selectedA.size()!=0){
            holder.textView.setText(RefreshActivity.selectedA.get(position-RefreshActivity.selectedF.size()-RefreshActivity.selectedC.size()-RefreshActivity.selectedT.size()-RefreshActivity.selectedS.size()-RefreshActivity.selectedCo.size()));
        }
        if (RefreshActivity.selectedTo.size()!=0){
            holder.textView.setText(RefreshActivity.selectedTo.get(position-RefreshActivity.selectedF.size()-RefreshActivity.selectedC.size()-RefreshActivity.selectedT.size()-RefreshActivity.selectedS.size()-RefreshActivity.selectedCo.size()-RefreshActivity.selectedA.size()));
        }*/
        holder.textView.setText(selected.get(position));
    }


    @Override
    public int getItemCount() {
        return selected.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder{
         TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_recyclerview);
        }
    }
}
