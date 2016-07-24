package com.xiaoxian.puredaily.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaoxian.puredaily.db.FavouriteDB;
import com.xiaoxian.puredaily.model.News;
import com.xiaoxian.puredaily.R;
import com.xiaoxian.puredaily.adapter.NewsAdapter;

import java.util.List;

public class FavouriteActivity extends Activity implements AdapterView.OnItemClickListener{
    private NewsAdapter adapter;
    private List<News> favouriteList;
    private ListView lvFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);
        lvFavourite =(ListView)findViewById(R.id.lv_favourite);
        favouriteList = FavouriteDB.getInstance(this).loadFavourite();
        adapter=new NewsAdapter(this,R.layout.listview_item, favouriteList);
        lvFavourite.setAdapter(adapter);
        lvFavourite.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsDetailActivity.actionStart(this,adapter.getItem(position));
    }
}
