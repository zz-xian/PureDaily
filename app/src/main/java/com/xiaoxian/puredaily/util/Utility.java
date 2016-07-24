package com.xiaoxian.puredaily.util;

import com.google.gson.Gson;
import com.xiaoxian.puredaily.model.News;
import com.xiaoxian.puredaily.model.NewsDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    /*
    使用JSONObject解析列表内容
     */
    public static List<News> parseJSONWithJSONObject(String json) throws JSONException{
        JSONObject newsOrigin=new JSONObject(json);
        JSONArray newsArray=newsOrigin.getJSONArray("stories");
        List<News> newsList=new ArrayList<News>();
        for (int i=0;i<newsArray.length();i++){
            JSONObject newObject=newsArray.getJSONObject(i);
            int id=newObject.optInt("id");
            String title=newObject.optString("title");
            String image="";
            if(newObject.has("images")){
                image=(String)newObject.getJSONArray("images").get(0);
            }
            News news=new News(id,title,image);
            newsList.add(news);
        }
        return newsList;
    }
    /*
    使用GSON解析消息具体内容
     */
    public static NewsDetail parseJSONWithGSON(String json) throws JSONException{
        Gson gson=new Gson();
        return gson.fromJson(json,NewsDetail.class);
    }
}
