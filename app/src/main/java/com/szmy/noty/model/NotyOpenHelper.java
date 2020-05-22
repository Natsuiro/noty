package com.szmy.noty.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.szmy.noty.app.NotyApplication;

import java.util.List;

public class NotyOpenHelper extends SQLiteOpenHelper {

    public NotyOpenHelper() {
        super(NotyApplication.instance().context(), SQLValue.DATABASE_NAME, null, SQLValue.DATABASE_VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String sql =("create table if not exists "+SQLValue.DATABASE_TABLE+"" +
                "("+SQLValue._ID+" integer primary key autoincrement," +
                ""+SQLValue.TABLE_TITLE+" text not null,"+SQLValue.TABLE_CONTENT+" text not null," +
                ""+SQLValue.TABLE_TIME+" text not null)");

        //插入数据
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
