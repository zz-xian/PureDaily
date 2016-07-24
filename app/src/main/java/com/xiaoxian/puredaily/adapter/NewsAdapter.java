package com.xiaoxian.puredaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoxian.puredaily.model.News;
import com.xiaoxian.puredaily.R;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News>{
    private LayoutInflater inflater;
    private int resource;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    private DisplayImageOptions options=new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.no_image)
            .showImageOnFail(R.drawable.no_image)
            .showImageForEmptyUri(R.drawable.no_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    public NewsAdapter(Context context, int resource){
        super(context,resource);
        inflater=LayoutInflater.from(context);
        this.resource=resource;
    }

    public NewsAdapter(Context context,int resource,List<News> object){
        super(context,resource,object);
        inflater=LayoutInflater.from(context);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=getItem(position);
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=inflater.inflate(resource,null);
            viewHolder=new ViewHolder();
            viewHolder.newsImage=(ImageView)convertView.findViewById(R.id.news_image);
            viewHolder.newsTitle=(TextView)convertView.findViewById(R.id.news_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.newsTitle.setText(news.getTitle());
        imageLoader.displayImage(news.getImage(),viewHolder.newsImage,options);
        return convertView;
    }

    class ViewHolder{
        ImageView newsImage;
        TextView newsTitle;
    }

    public void refreshNewsList(List<News> newsList){
        clear();
        addAll(newsList);
        //刷新ListView
        notifyDataSetChanged();
    }
}
