package com.xiaoxian.puredaily.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiaoxian.puredaily.model.News;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDB {
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private static FavouriteDB favouriteDB;
    public String[] sum={DBOpenHelper.ID,DBOpenHelper.NEWS_ID,DBOpenHelper.NEWS_TITLE,DBOpenHelper.NEWS_IMAGE};

    public FavouriteDB(Context context){
        dbOpenHelper=new DBOpenHelper(context);
        db=dbOpenHelper.getWritableDatabase();
    }

    public synchronized static FavouriteDB getInstance(Context context){
        if(favouriteDB ==null){
            favouriteDB =new FavouriteDB(context);
        }
        return favouriteDB;
    }

    public void saveFavourite(News news){
        if (news!=null){
            ContentValues values=new ContentValues();
            values.put(DBOpenHelper.NEWS_ID,news.getId());
            values.put(DBOpenHelper.NEWS_TITLE,news.getTitle());
            values.put(DBOpenHelper.NEWS_IMAGE,news.getImage());
            db.insert(DBOpenHelper.TABLE_NAME,null,values);
        }
    }

    public List<News> loadFavourite(){
        List<News> list=new ArrayList<News>();
        Cursor cursor=db.query(DBOpenHelper.TABLE_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                News news=new News();
                news.setId(cursor.getInt(1));
                news.setTitle(cursor.getString(2));
                news.setImage(cursor.getString(3));
                list.add(news);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean isFavourite(News news){
        Cursor cursor=db.query(DBOpenHelper.TABLE_NAME,null,DBOpenHelper.NEWS_ID+"=?",new String[]{news.getId()+""},null,null,null);
        if (cursor.moveToNext()){
            cursor.close();
            return true;
        }else{
            return false;
        }
    }

    public void deleteFavourite(News news){
        if (news!=null){
            db.delete(DBOpenHelper.TABLE_NAME,DBOpenHelper.NEWS_ID+"=?",new String[]{news.getId()+""});
        }
    }
}
