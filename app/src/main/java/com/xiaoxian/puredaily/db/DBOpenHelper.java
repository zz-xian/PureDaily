package com.xiaoxian.puredaily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
    public static final String DB_NAME="daily_news.db";
    public static final String TABLE_NAME="favourite";
    public static final int VERSION=1;
    public static final String ID="id";
    public static final String NEWS_ID="news_id";
    public static final String NEWS_TITLE="news_title";
    public static final String NEWS_IMAGE="news_image";

    public static final String CREATE_DATABASE="create table "+TABLE_NAME
            +"("+ID+" integer primary key autoincrement, "
            +NEWS_ID+" integer unique, "
            +NEWS_TITLE+" text, "
            +NEWS_IMAGE+" text)";

    public DBOpenHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists"+TABLE_NAME);
        onCreate(db);
    }
}
