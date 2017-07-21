package com.example.jf.cardapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.entity.RefreshReturnData;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.List;

/**
 * Created by jf on 2017/2/28.
 */

public class TextSeachViewAdapter extends RecyclerView.Adapter<TextSeachViewAdapter.MyViewHolder>{
    private Context context;
    private List<RefreshReturnData.DataBean.CardListBean> list;
    private OnItemClickListener mOnItemClickListener;


    public TextSeachViewAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_selectedok,parent,false);

        TextSeachViewAdapter.MyViewHolder holder = new TextSeachViewAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.set.setText(list.get(position).getSetsName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView set;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_cardname);
            set = (TextView) itemView.findViewById(R.id.tv_cardset);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v);
            }
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

}
