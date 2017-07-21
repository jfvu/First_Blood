package com.example.jf.cardapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jf.cardapp.R;

import java.util.ArrayList;

/**
 * Created by jf on 2017/2/12.
 */

public class HomeFragmentTopadapter extends PagerAdapter {
    private ArrayList<Integer> pictureList;
    private Context context;
    private View view;

    public HomeFragmentTopadapter(ArrayList<Integer> list,Context context){

    this.pictureList = list;
    this.context = context;
}
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view =  View.inflate(context, R.layout.item_fragment_home_top_viewpager,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_fh_vp_item);
        Integer adInfo = pictureList.get(position%pictureList.size());
        imageView.setImageResource(adInfo);
        container.addView(view);
        return view;
    }
}
