package com.xiaoxian.puredaily.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.xiaoxian.puredaily.db.FavouriteDB;
import com.xiaoxian.puredaily.model.News;
import com.xiaoxian.puredaily.task.LoadNewsDetail;
import com.xiaoxian.puredaily.util.NetWork;
import com.xiaoxian.puredaily.R;

public class NewsDetailActivity extends Activity {
    private News news;
    private WebView webView;
    private boolean isFavourite =false;

    public static void actionStart(Context context,News news){
        if (NetWork.checkConnection(context)){
            Intent i=new Intent(context,NewsDetailActivity.class);
            i.putExtra("news",news);
            context.startActivity(i);
        }else{
            NetWork.noNetwork(context);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        //给左上角图标左边加上一个返回图标
        getActionBar().setDisplayHomeAsUpEnabled(true);
        webView=(WebView)findViewById(R.id.webview);
        setWebView(webView);
        news=(News)getIntent().getSerializableExtra("news");
        new LoadNewsDetail(webView).execute(news.getId());
        isFavourite = FavouriteDB.getInstance(this).isFavourite(news);
    }

    public void setWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        //隐藏垂直、水平滚动条
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        if (isFavourite){
            menu.findItem(R.id.action_favourite).setIcon(R.mipmap.favourite_active);
        }
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
            if (!isFavourite){
                FavouriteDB.getInstance(this).saveFavourite(news);
                item.setIcon(R.mipmap.favourite_active);
                Toast.makeText(NewsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                isFavourite =true;
            }else{
                FavouriteDB.getInstance(this).deleteFavourite(news);
                item.setIcon(R.mipmap.favourite_normal);
                Toast.makeText(NewsDetailActivity.this, "收藏取消", Toast.LENGTH_SHORT).show();
                isFavourite =false;
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
