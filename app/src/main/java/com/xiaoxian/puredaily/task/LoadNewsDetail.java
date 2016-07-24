package com.xiaoxian.puredaily.task;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.xiaoxian.puredaily.model.NewsDetail;
import com.xiaoxian.puredaily.util.HttpUtil;
import com.xiaoxian.puredaily.util.Utility;

import org.json.JSONException;

import java.io.IOException;

public class LoadNewsDetail extends AsyncTask<Integer,Void,NewsDetail>{

    private WebView webView;

    public LoadNewsDetail(WebView webView){
        this.webView=webView;
    }

    @Override
    protected NewsDetail doInBackground(Integer... params) {
        NewsDetail newsDetail=null;
        try {
            newsDetail= Utility.parseJSONWithGSON(HttpUtil.getNewsDetail(params[0]));
        }catch (IOException|JSONException e){

        }finally {
            return newsDetail;
        }
    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        String coverImage;
        if (newsDetail.getImage()==null||newsDetail.getImage()==""){
            //加载assets目录下的资源文件方式
            coverImage="file:///android_asset/news_detail_cover_image.jpg";
        }else{
            coverImage=newsDetail.getImage();
        }
        StringBuilder builder=new StringBuilder();
        builder.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(newsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(newsDetail.getImage_source()).append("</span>")
                .append("<img src=\"").append(coverImage)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        /*
        replace函数：
            String s1="abcd";
            String s2=s1.replace("a","1");——将a替换成1
         */
        String newsContent="<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                +"<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                +newsDetail.getBody().replace("<div class=\"img-place-holder\">",builder.toString());
        /*
        loadDataWithBaseURL (String baseUrl, String data, String mimeType, String encoding, String historyUrl);
        作用：加载String中存放的html代码
            baseUrl:默认html代码；data:要加载html代码；mimeType:文本类型
            encoding:编码格式；historyUrl:历史记录html代码
         */
        webView.loadDataWithBaseURL("file:///android_asset/",newsContent,"text/html","utf-8",null);
    }
}
