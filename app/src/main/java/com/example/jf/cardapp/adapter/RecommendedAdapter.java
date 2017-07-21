package com.example.jf.cardapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.entity.Magic;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.MyViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private List<Magic> list;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;
    public RecommendedAdapter(Context context, List<Magic>list){
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    public void setHeaderView(View headerView){
        mHeaderView=headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
        View view=inflater.inflate(R.layout.item_recommend,null,false);
        MyViewHolder holder=new MyViewHolder(view,mItemClickListener,mItemLongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        imageLoader= imageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage("http://60.205.177.241/MagicCardData"+list.get(position).getPic(), holder.imageView);
    }



    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView name;
        ImageView imageView;
        MyItemClickListener mListener;
        MyItemLongClickListener mLongClickListener;
        public MyViewHolder(View itemView,MyItemClickListener listener,MyItemLongClickListener longClickListener) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.name);
            imageView= (ImageView) itemView.findViewById(R.id.img);
            this.mListener = listener;
            this.mLongClickListener = longClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            if(itemView == itemView) return;
        }

        @Override
        public void onClick(View view) {
            if(mListener != null){
                mListener.onItemClick(itemView,getPosition());

            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(view, getPosition());
            }
            return false;
        }
    }
    public interface MyItemClickListener{
        public void onItemClick(View view, int postion);
    }
    public interface MyItemLongClickListener{
        public void onItemLongClick(View view, int postion);
    }
}
