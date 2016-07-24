package com.xiaoxian.puredaily.task;

import android.os.AsyncTask;

import com.xiaoxian.puredaily.adapter.NewsAdapter;
import com.xiaoxian.puredaily.model.News;
import com.xiaoxian.puredaily.util.HttpUtil;
import com.xiaoxian.puredaily.util.Utility;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class LoadNews extends AsyncTask<Void,Void,List<News>>{
    private NewsAdapter adapter;
    private onFinishListener listener;

    public LoadNews(NewsAdapter adapter){
        super();
        this.adapter=adapter;
    }

    public LoadNews(NewsAdapter adapter,onFinishListener listener){
        super();
        this.adapter=adapter;
        this.listener=listener;
    }

    @Override
    protected List<News> doInBackground(Void... params) {
        List<News> newsList=null;
        try{
            newsList= Utility.parseJSONWithJSONObject(HttpUtil.getLatestNews());
        }catch (IOException|JSONException e){

        }finally {
            return newsList;
        }
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        adapter.refreshNewsList(newsList);
        if (listener!=null){
            listener.finish();
        }
    }

    public interface onFinishListener{
        public void finish();
    }
}
