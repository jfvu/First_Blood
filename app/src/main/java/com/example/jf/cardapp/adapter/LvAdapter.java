package com.example.jf.cardapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.entity.Match;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


/**
 * Created by Administrator on 2017/2/21.
 */

public class LvAdapter extends BaseAdapter{
    private ImageLoader imageLoader;
    private List<Match> list;
    private Context context;
    public LvAdapter(List<Match> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        LayoutInflater inflater=LayoutInflater.from(context);
        if(view==null){
            holder=new Holder();
            view=inflater.inflate(R.layout.item_match,null);
            holder.picture= (ImageView) view.findViewById(R.id.img);
            holder.name= (TextView) view.findViewById(R.id.match_name);
            holder.place= (TextView) view.findViewById(R.id.place);
            holder.date= (TextView) view.findViewById(R.id.date);
            view.setTag(holder);
        }else{
            holder= (Holder) view.getTag();
        }
        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(list.get(i).getPicture(),holder.picture);
        holder.name.setText(list.get(i).getName());
        holder.place.setText(list.get(i).getPlace());
        holder.date.setText(list.get(i).getDate());
        return view;
    }
    class Holder{
        private ImageView picture;
        private TextView name;
        private TextView place;
        private TextView date;
    }
}
