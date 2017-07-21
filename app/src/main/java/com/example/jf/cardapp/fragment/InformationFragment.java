package com.example.jf.cardapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.adapter.LvAdapter;
import com.example.jf.cardapp.entity.Match;
import com.example.jf.cardapp.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jf on 2017/2/12.
 * Update by leen
 */

public class InformationFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<Match> list;
    private MyListView lv_news,lv_match;
    private LvAdapter adapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList=new ArrayList<>();//卡牌标题集合
    private View view1,view2;
    private List<View>mViewList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information,null);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_view);
        mTabLayout= (TabLayout) view.findViewById(R.id.tabs);
        mInflater=LayoutInflater.from(getActivity());
        view1=mInflater.inflate(R.layout.news,null);
        view2=mInflater.inflate(R.layout.match,null);
        mViewList.add(view1);
        mViewList.add(view2);
        //添加页卡
        mTitleList.add("新闻");
        mTitleList.add("赛事");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        lv_news= (MyListView) view1.findViewById(R.id.listview);
        lv_match= (MyListView) view2.findViewById(R.id.listview);
        list = new ArrayList<Match>();
        for(int i=0;i<10;i++){

            Match m=new Match(i,"http://60.205.177.241/MagicCardData/Image/cn/kld/1.jpg","第"+i+"届德玛西亚杯总决赛","上海站","2017年2月22日","真的还可以");
            list.add(m);
        }
        adapter = new LvAdapter(list, getActivity());
        lv_news.setAdapter(adapter);
        lv_match.setAdapter(adapter);
        lv_news.setonRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Match m=new Match(1,"http://60.205.177.241/MagicCardData/Image/cn/kld/2.jpg","最新德玛西亚杯总决赛","上海站","2017年2月22日","真的还可以");
                        list.add(0,m);
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        lv_news.onRefreshComplete();
                    }
                }.execute(null, null, null);
            }

            @Override
            public void onLoadingMore() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Match m=new Match(1,"http://60.205.177.241/MagicCardData/Image/cn/kld/3.jpg","上届德玛西亚杯总决赛","上海站","2017年2月22日","真的还可以");
                        list.add(m);
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        lv_news.onRefreshComplete();
                        lv_news.hideFooterView();
                    }
                }.execute(null, null, null);
            }
        });
        lv_match.setonRefreshListener(new MyListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Match m=new Match(1,"http://60.205.177.241/MagicCardData/Image/cn/kld/4.jpg","最新德玛西亚杯总决赛","上海站","2017年2月22日","真的还可以");
                        list.add(0,m);
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        lv_match.onRefreshComplete();
                    }
                }.execute(null, null, null);
            }

            @Override
            public void onLoadingMore() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Match m=new Match(1,"http://60.205.177.241/MagicCardData/Image/cn/kld/5.jpg","上届德玛西亚杯总决赛","上海站","2017年2月22日","真的还可以");
                        list.add(m);
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        lv_match.onRefreshComplete();
                        lv_match.hideFooterView();
                    }
                }.execute(null, null, null);
            }
        });
        return view;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }



}
