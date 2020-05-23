package com.szmy.noty.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotyDB {

    private static NotyDB instance;

    private SQLiteDatabase getDatabase(){
        return new NotyOpenHelper().getWritableDatabase();
    }

    public static NotyDB instance() {
        if (instance==null) instance = new NotyDB();
        return instance;
    }

    public void insert(String data){
        SQLiteDatabase db = getDatabase();
        ContentValues values = new ContentValues();
        values.put("title","title");
        values.put("content",data);
        values.put("time",new Date().toString());
        db.insert("note",null,values);
        db.close();
        Log.d("insert","insert");
    }

    public void update(int itemId, String content){
        SQLiteDatabase db = getDatabase();
        ContentValues values = new ContentValues();
        values.put("content",content);
        db.update("note",values,"_id=?",new String[]{String.valueOf(itemId)});
        db.close();
    }

    public void delete(int itemId){

        SQLiteDatabase db = getDatabase();
        db.delete("note","_id=?",new String[]{String.valueOf(itemId)});

    }



    public List<NotyBean> search(){
        Log.d("search","search");
        SQLiteDatabase db = getDatabase();
        List<NotyBean> list = new ArrayList<>();
        //返回游标对象的select 查询
        Cursor cursor = db.rawQuery("select * from note",null);
        if (cursor != null){
            String [] cols = cursor.getColumnNames();
            while (cursor.moveToNext()){
                NotyBean notyBean = new NotyBean();
                for (String colsnames : cols){
                    Log.d("db",colsnames+":"+cursor.getString(cursor.getColumnIndex(colsnames)));
                    switch (colsnames) {
                        case "_id":
                            notyBean.setId(cursor.getInt(cursor.getColumnIndex(colsnames)));
                            break;
                        case "title":
                            notyBean.setTitle(cursor.getString(cursor.getColumnIndex(colsnames)));
                            break;
                        case "time":
                            Date date = new Date();
                            DateFormat ins = SimpleDateFormat.getDateInstance();
                            try {
                                date = ins.parse(cursor.getString(cursor.getColumnIndex(colsnames)));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } finally {
                                notyBean.setTime(date);
                            }

                            break;
                        case "content":
                            notyBean.setContent(cursor.getString(cursor.getColumnIndex(colsnames)));
                            break;
                    }

                }
                list.add(notyBean);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

}
