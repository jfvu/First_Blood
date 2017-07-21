package com.example.jf.cardapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.adapter.LvAdapter;
import com.example.jf.cardapp.entity.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public class MatchActivity extends AppCompatActivity {
    private String newsString=null;
    private List<Match> list;
    private ListView listView;
    private LvAdapter adapter;
    private TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        list=new ArrayList<>();
        Intent intent=getIntent();
        newsString=intent.getStringExtra("match");
        initView();
        /*解析新闻*/
        try {
            list=jsonToList(newsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter=new LvAdapter(list,MatchActivity.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MatchActivity.this,WebActivity.class);
                intent.putExtra("ID",list.get(i).getId());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        listView= (ListView) findViewById(R.id.newsListVew);
        title= (TextView) findViewById(R.id.title);
        title.setText("赛事");
    }

    private List<Match> jsonToList(String newsString) throws JSONException {
        List<Match>mlist=new ArrayList<>();
        Match m;
        JSONArray array=new JSONArray(newsString);
        for(int i=0;i<array.length();i++){
            JSONObject object=array.getJSONObject(i);
            int id=object.getInt("ID");
            String name=object.getString("Title");
            String content=object.getString("Summary");
            String pic="http://60.205.177.241"+object.getString("Pic");
            String date=object.getString("Date");
            String place=object.getString("Url");
            m=new Match(id,pic,name,place,date,content);
            mlist.add(m);
        }
        return mlist;
    }
}
