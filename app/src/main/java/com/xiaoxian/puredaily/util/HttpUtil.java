package com.xiaoxian.puredaily.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String NEWS_DETAIL="http://news-at.zhihu.com/api/4/news/";
    public static String NEWS_LATEST="http://news-at.zhihu.com/api/4/news/latest";

    public static String sendHttpRequest(String address) throws IOException{
        HttpURLConnection conn=null;
        try {
            URL url=new URL(address);
            conn=(HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //设置请求头报文属性
            //User-Agent:即浏览器UA，说明与Mozilla渲染引擎兼容性
            conn.setRequestProperty("User-Agent","Mozilla/5.0");
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response=new StringBuilder();
                String line;
                while ((line=reader.readLine())!=null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }else {
                throw new IOException("Network Error - response code:"+conn.getResponseCode());
            }
        }finally {
            if (conn!=null){
                conn.disconnect();
            }
        }
    }

    public static String getLatestNews() throws IOException{
        return sendHttpRequest(NEWS_LATEST);
    }

    public static String getNewsDetail(int id) throws IOException{
        return sendHttpRequest(NEWS_DETAIL+id);
    }
}
