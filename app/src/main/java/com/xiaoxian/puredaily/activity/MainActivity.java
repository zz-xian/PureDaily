package com.xiaoxian.puredaily.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaoxian.puredaily.R;
import com.xiaoxian.puredaily.adapter.NewsAdapter;
import com.xiaoxian.puredaily.task.LoadNews;
import com.xiaoxian.puredaily.util.NetWork;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener{
    private SwipeRefreshLayout refreshLayout;
    private ListView lv;
    private NewsAdapter adapter;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnected= NetWork.checkConnection(this);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright);
        lv=(ListView)findViewById(R.id.lv);
        setTitle(getTime());
        adapter=new NewsAdapter(this,R.layout.listview_item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        if(isConnected){
            new LoadNews(adapter).execute();
        }else {
            NetWork.noNetwork(this);
        }
    }

    private String getTime() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(getString(R.string.date_format));
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent i=new Intent(this,AboutActivity.class);
            startActivity(i);
            return true;
        }
        if (id==R.id.action_favourite){
            Intent i=new Intent(this,FavouriteActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsDetailActivity.actionStart(this,adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        if(isConnected){
            new LoadNews(adapter, new LoadNews.onFinishListener() {
                @Override
                public void finish() {
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        }else {
            NetWork.noNetwork(MainActivity.this);
            refreshLayout.setRefreshing(false);
        }
    }
}
