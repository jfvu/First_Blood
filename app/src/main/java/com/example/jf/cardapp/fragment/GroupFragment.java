package com.example.jf.cardapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.activity.DetailsActivity;
import com.example.jf.cardapp.adapter.GalleryAdapter;
import com.example.jf.cardapp.entity.GroupCard;
import com.example.jf.cardapp.entity.GroupCardList;
import com.example.jf.cardapp.thread.GroupCardThread;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jf on 2017/2/12.
 */

public class GroupFragment extends Fragment implements AdapterView.OnItemClickListener {
    private MaterialSpinner spinner;
    private GroupCardThread groupCardThread;
    private List<GroupCardList> groupCardLists=new ArrayList<>();
    private List<GroupCard>list=new ArrayList<>();
    private String[] ANDROID_VERSIONS;
    private RecyclerView recyclerView;
    private GalleryAdapter mAdapter;
    private List<Integer> mDatas;
    private ImageView imageView;
    private List<GroupCardList>list3=new ArrayList<>();
    GroupCardList g;
    private int count=0;
    private int count1=0;
    private ImageLoader imageLoader;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    String string= (String) message.obj;
                    //解析
                    try {
                        list3=Json2List(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group,null);
        spinner= (MaterialSpinner) view.findViewById(R.id.spinner);
        recyclerView= (RecyclerView) view.findViewById(R.id.listview);
        //得到控件
        imageView= (ImageView)view. findViewById(R.id.img);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器

        groupCardThread=new GroupCardThread(handler,getActivity());
        groupCardThread.run();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private List<GroupCardList> Json2List(String string) throws JSONException {
        final List<GroupCardList>list11=new ArrayList<>();
        JSONObject object=new JSONObject(string);
        String string1=object.getString("Data");
        JSONArray array=new JSONArray(string1);
        for(int i=0;i<array.length();i++){
            List<GroupCard>listc=new ArrayList<>();
            JSONObject object2=array.getJSONObject(i);
            int id=object2.getInt("ID");
            String title=object2.getString("Title");
            String cardList=object2.getString("CardList");
            JSONArray array2=new JSONArray(cardList);
            for(int j=0;j<array2.length();j++){
                JSONObject object3=array2.getJSONObject(j);
                int cardid=object3.getInt("ID");
                String pic=object3.getString("Pic");
                String name=object3.getString("Name");
                GroupCard card=new GroupCard(cardid,pic,name);
                listc.add(card);
            }

            g=new GroupCardList(id,title,listc);

            list11.add(g);

        }
        Log.e("list",list11.toString());
        ANDROID_VERSIONS=new String[list11.size()];
        for(int i=0;i<list11.size();i++){
            ANDROID_VERSIONS[i]=list11.get(i).getTitle();
        }
        spinner.setBackgroundColor(Color.BLACK);
        spinner.setTextColor(Color.RED);
        spinner.setDropdownMaxHeight(500);
        spinner.setItems(ANDROID_VERSIONS);
        mAdapter = new GalleryAdapter(getActivity(), list11.get(0).getList());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                count1=position;
                imageLoader= ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
                Animation animation = AnimationUtils.loadAnimation(
                        getActivity(), R.anim.translate);
                imageView.startAnimation(animation);
                imageLoader.displayImage("http://60.205.177.241/MagicCardData"+list11.get(0).getList().get(position).getPic(),imageView);

            }
        });
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

//                            Toast.makeText(getActivity(),item+list11.get(position),Toast.LENGTH_LONG).show();
                            count=position;
                                    mAdapter = new GalleryAdapter(getActivity(), list11.get(position).getList());
                                    mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener()
                                    {
                                        @Override
                                        public void onItemClick(View view, int position)
                                        {
                                            count1=position;
//                                    imageView.setImageResource(mDatas.get(position));
                                            imageLoader= ImageLoader.getInstance();
                                            imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
                                            Animation animation = AnimationUtils.loadAnimation(
                                                    getActivity(), R.anim.translate);
                                            imageView.startAnimation(animation);
                                    imageLoader.displayImage("http://60.205.177.241/MagicCardData"+list11.get(count).getList().get(position).getPic(),imageView);

//                                    Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT)
//                                            .show();
                                }
                            });
                            recyclerView.setAdapter(mAdapter);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),DetailsActivity.class);
                intent.putExtra("ID",list11.get(count).getList().get(count1).getId());
                boolean f=true;
                intent.putExtra("language",f);
                startActivity(intent);

            }
        });
        spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Toast.makeText(getActivity(),"Nothing selected",Toast.LENGTH_SHORT).show();
            }
        });
        return list11;
    }

}
