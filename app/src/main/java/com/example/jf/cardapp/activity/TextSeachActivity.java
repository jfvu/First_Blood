package com.example.jf.cardapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.adapter.TextSeachRecyclerViewAdapter;
import com.example.jf.cardapp.adapter.TextSeachViewAdapter;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.entity.RefreshData;
import com.example.jf.cardapp.entity.RefreshReturnData;
import com.example.jf.cardapp.entity.ReturnCardName;
import com.example.jf.cardapp.utils.HttpUtils;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jf on 2017/2/21.
 */

public class TextSeachActivity extends BaseActivity {

    @BindView(R.id.img_textseach_top_back)
    ImageView imgTextseachTopBack;
    @BindView(R.id.img_textseach_scan)
    ImageView imgTextseachScan;
    @BindView(R.id.img_textseach_top_refresh)
    ImageView imgTextseachTopRefresh;
    @BindView(R.id.et_textseach_seach)
    EditText etTextseachSeach;
    /*@BindView(R.id.but_textseach_seach)
    Button butTextseachSeach;*/
    @BindView(R.id.rv_selected)
    RecyclerView rvSelected;
    @BindView(R.id.rv_selectedok)
    RecyclerView rvSelectedok;
    @BindView(R.id.search_01)
    Button search01;


    private SharedPreferences preferences;

    private ArrayList<String> selected = new ArrayList<>();
    private List<String> initial = new ArrayList<>();
    private List<Integer> color = new ArrayList<>();
    private List<String> category = new ArrayList<>();
    private List<String> rarity = new ArrayList<>();
    private List<Integer> power = new ArrayList<>();
    private List<Integer> defense = new ArrayList<>();
    private List<String> magic = new ArrayList<>();
    private LinearLayoutManager layoutManager  = new LinearLayoutManager(TextSeachActivity.this);
    private Gson gson;
    private List<RefreshReturnData.DataBean.CardListBean> cardList;
    private Pattern p;
    private Matcher m;
    private String str;
    private TextSeachViewAdapter adapter = null;
    private boolean aBoolean = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_textseach;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


    }

    private void getSelected() {
        preferences = getSharedPreferences("refresh", MODE_PRIVATE);
        addSelected(26, "selectedF");
        addSelected(9, "selectedC");
        addSelected(7, "selectedT");
        addSelected(6, "selectedTy");
        if (preferences.getString("selectedS", null) != null) {
            selected.add(preferences.getString("selectedS", null));
        }
        addSelected(17, "selectedCo");
        addSelected(16, "selectedA");
        addSelected(16, "selectedTo");


        /*Log.e("seshi", selected.toString());
        Log.e("seshi", selected.size() + "");*/
    }

    private void addSelected(int j, String x) {
        for (int i = 0; i < j; i++) {
            String s = preferences.getString(x + i, null);
            if (!TextUtils.isEmpty(s)) {
                selected.add(preferences.getString(x + i, ""));

            }

        }
    }


    @OnClick({R.id.img_textseach_top_back, R.id.img_textseach_scan, R.id.img_textseach_top_refresh, R.id.search_01})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_textseach_top_back:

                finish();
                break;
            case R.id.img_textseach_scan:

                break;
            case R.id.img_textseach_top_refresh:
                preferences.edit().clear().commit();
                selected.clear();
                Intent intent = new Intent(TextSeachActivity.this, RefreshActivity.class);
                startActivity(intent);
                break;
            case R.id.search_01:
                final String name = etTextseachSeach.getText().toString();

                if (!name.equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                                p = Pattern.compile("^[\u4e00-\u9fff]+$");
                                m = p.matcher(name);


                            if (m.matches()) {
                               /* try {
                                    name[0] = URLEncoder.encode(etTextseachSeach.getText().toString(), "UTF8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }*/
                                aBoolean = true;
                                str = "http://60.205.177.241:8001/api/Card/CardSearchByName?name=" + name + "&language=cn";
                            }else{

                                str ="http://60.205.177.241:8001/api/Card/CardSearchByName?name=" + name + "&language=en";
                            }
Log.e("ok1",str);
                            OkHttpUtils.get(str).execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Log.e("card", s);
                                    RefreshReturnData cardName = new RefreshReturnData();
                                    Gson gson = new Gson();
                                    cardName = gson.fromJson(s, RefreshReturnData.class);
                                    if (cardName.getErrorCode() == 0 ) {
                                        if(cardName.getData().getTotalCount() == 1){
                                            Intent intent = new Intent(TextSeachActivity.this, DetailsActivity.class);
                                            intent.putExtra("language",aBoolean);
                                            intent.putExtra("ID", cardName.getData().getCardList().get(0).getID());
                                            startActivity(intent);
                                        }
                                        else if(cardName.getData().getTotalCount() == 0){
                                            Toast.makeText(TextSeachActivity.this, "无匹配对象", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            cardList = cardName.getData().getCardList();
                                            Log.e("list",cardList.size()+"");
                                            TextSeachViewAdapter adapter1 = new TextSeachViewAdapter(TextSeachActivity.this, cardList);
                                            rvSelectedok.setAdapter(adapter1);
                                            adapter1.setOnItemClickListener(new TextSeachViewAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(View view) {
                                                    int childAdapterPosition = rvSelectedok.getChildAdapterPosition(view);
                                                    Toast.makeText(TextSeachActivity.this, "item click index = " + childAdapterPosition, Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(TextSeachActivity.this, DetailsActivity.class);
                                                    intent.putExtra("language",aBoolean);
                                                    intent.putExtra("ID", cardList.get(childAdapterPosition).getID());
                                                    startActivity(intent);
                                                    selected.clear();
                                                }
                                            });

                                        }

                                    } else {
                                        Toast.makeText(TextSeachActivity.this,cardName.getMessage() , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }).start();
                }
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        initial.clear();
        color.clear();
        category.clear();
        rarity.clear();
        power.clear();
        defense.clear();
        magic.clear();

        getSelected();
        if (selected.size() != 0) {
            rvSelected.setVisibility(View.VISIBLE);

        } else {
            rvSelected.setVisibility(View.GONE);

        }

        rvSelected.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        rvSelected.setAdapter(new TextSeachRecyclerViewAdapter(TextSeachActivity.this, selected));


//bean添加首字母
        for (int i = 0; i < 27; i++) {
            String s = preferences.getString("selectedF" + i, null);
            if (!TextUtils.isEmpty(s)) {
                initial.add(preferences.getString("selectedF" + i, ""));
            }
        }

//bean添加颜色
        for (int i = 0; i < 7; i++) {
            String s = preferences.getString("selectedC" + i, null);
            if (!TextUtils.isEmpty(s)) {

                if (preferences.getString("selectedC" + i, "").equals("红")) {
                    color.add(1);
                } else if (preferences.getString("selectedC" + i, "").equals("绿")) {
                    color.add(2);
                } else if (preferences.getString("selectedC" + i, "").equals("蓝")) {
                    color.add(3);
                } else if (preferences.getString("selectedC" + i, "").equals("白")) {
                    color.add(4);
                } else if (preferences.getString("selectedC" + i, "").equals("黑")) {
                    color.add(5);
                } else if (preferences.getString("selectedC" + i, "").equals("无色")) {
                    color.add(0);
                } else if (preferences.getString("selectedC" + i, "").equals("多色")) {
                    color.add(99);
                } else if (preferences.getString("selectedC" + i, "").equals("其他")) {
                    color.add(100);
                }

            }

        }


        for (int i = 0; i < 9; i++) {
            String s = preferences.getString("selectedT" + i, null);
            if (!TextUtils.isEmpty(s)) {

                if (preferences.getString("selectedT" + i, "").equals("神器")) {
                    category.add("Artifact");
                } else if (preferences.getString("selectedT" + i, "").equals("生物")) {
                    category.add("Creature");
                } else if (preferences.getString("selectedT" + i, "").equals("魔法")) {
                    category.add("Sorcery");
                } else if (preferences.getString("selectedT" + i, "").equals("即时")) {
                    category.add("Instant");
                } else if (preferences.getString("selectedT" + i, "").equals("土地")) {
                    category.add("Land");
                }
                /*else if (preferences.getString("selectedT"+i,"").equals("巫术")){
                    category.add(0);
                }*/

            }

        }


        for (int i = 0; i < 9; i++) {
            String s = preferences.getString("selectedTy" + i, null);
            if (!TextUtils.isEmpty(s)) {

                if (preferences.getString("selectedTy" + i, "").equals("神话")) {
                    rarity.add("Mythic Rare");
                } else if (preferences.getString("selectedTy" + i, "").equals("稀有")) {
                    rarity.add("Rare");
                } else if (preferences.getString("selectedTy" + i, "").equals("罕见")) {
                    rarity.add("Uncommon");
                } else if (preferences.getString("selectedTy" + i, "").equals("通用")) {
                    rarity.add("Common");
                } else if (preferences.getString("selectedTy" + i, "").equals("特殊")) {
                    rarity.add("Special");
                }


            }

        }
        //magic.add("3ww");
       /* for (int i = 0; i < 17; i++) {
            String s = preferences.getString("selectedCo" + i, null);
            if (!TextUtils.isEmpty(s)){
                magic.add();
        }*/
        //power.add(1);
        for (int i = 0; i < 16; i++) {
            String s = preferences.getString("selectedA" + i, null);
            //power.add(i);
                /*if (s!=null){
                    Log.e("攻击力",s);
                    power.add(i);
                }*/

            if (!TextUtils.isEmpty(s)) {
                Log.e("攻击力1", s + i);
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(s);
                power.add(Integer.valueOf(m.replaceAll("").trim()));
            }
        }

        //defense.add(2);
        for (int i = 0; i < 16; i++) {
            String s = preferences.getString("selectedTo" + i, null);


            if (!TextUtils.isEmpty(s)) {
                if (!TextUtils.isEmpty(s)) {
                    String regEx = "[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(s);
                    defense.add(Integer.valueOf(m.replaceAll("").trim()));
                }
            }
        }

        RefreshData data = new RefreshData(1, 20, "en", preferences.getString("selectedS", null), initial, color, category, rarity, power, defense, magic);
        goHttp(data);

    }

    @Override
    protected void onStop() {
        super.onStop();
        preferences.edit().clear().commit();
    }

    private void goHttp(final RefreshData data) {

        new Thread(new Runnable() {
            @Override
            public void run() {


                gson = new Gson();
                Log.e("json", gson.toJson(data));
                OkHttpUtils.post("http://60.205.177.241:8001/api/Card/CardSearchByParam").tag(this).upJson(gson.toJson(data)).execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("ok", s);


                        RefreshReturnData returnData = new RefreshReturnData();
                        final Gson gson1 = new Gson();
                        returnData = gson1.fromJson(s, RefreshReturnData.class);

                        if (returnData.getData().getTotalCount() != 0) {
                            rvSelectedok.setVisibility(View.VISIBLE);
                            cardList = returnData.getData().getCardList();
                            Toast.makeText(TextSeachActivity.this, returnData.getData().getTotalCount() + "", Toast.LENGTH_LONG).show();
                            //存用户名密码
                            adapter = new TextSeachViewAdapter(TextSeachActivity.this, cardList);

                            rvSelectedok.setLayoutManager(layoutManager);

                            final RefreshReturnData finalReturnData = returnData;
                            adapter.setOnItemClickListener(new TextSeachViewAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view) {
                                    int childAdapterPosition = rvSelectedok.getChildAdapterPosition(view);
                                    Toast.makeText(TextSeachActivity.this, "item click index = " + childAdapterPosition, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(TextSeachActivity.this, DetailsActivity.class);
                                    intent.putExtra("ID", finalReturnData.getData().getCardList().get(childAdapterPosition).getID());
                                    startActivity(intent);
                                    selected.clear();
                                }
                            });
                            rvSelectedok.setAdapter(adapter);
                            rvSelectedok.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    Toast.makeText(TextSeachActivity.this,"ok2",Toast.LENGTH_SHORT).show();
                                    if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.getItemCount() - 1 == layoutManager.findLastVisibleItemPosition()) {
                                        data.setPageIndex(data.getPageIndex() + 1);

                                        Log.e("page", data.getPageIndex() + "");
                                        //Log.e("page",data.)
                                        OkHttpUtils.post("http://60.205.177.241:8001/api/Card/CardSearchByParam").tag(this).upJson(gson.toJson(data)).execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                Log.e("next", s);
                                                RefreshReturnData returnData = gson1.fromJson(s, RefreshReturnData.class);
                                                if (returnData.getData().getTotalCount() != 0) {
                                                    for (int i = 0; i < returnData.getData().getCardList().size(); i++) {
                                                        cardList.add(returnData.getData().getCardList().get(i));
                                                    }
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                    super.onScrollStateChanged(recyclerView, newState);
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }
                            });

                            Log.e("cardreturn", returnData.getData().getCardList().get(2).getName());
                            /*rvSelected.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                            rvSelected.setAdapter(new TextSeachRecyclerViewAdapter(TextSeachActivity.this, selected));*/

                        } else {
                            rvSelectedok.setVisibility(View.GONE);
                            Toast.makeText(TextSeachActivity.this, "无匹配对象", Toast.LENGTH_LONG).show();
                        }

                    }


                });


            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
