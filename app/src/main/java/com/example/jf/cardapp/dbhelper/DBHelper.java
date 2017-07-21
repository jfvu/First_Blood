package com.example.jf.cardapp.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee on 2017/2/13.
 */

public class DBHelper  extends SQLiteOpenHelper{
    private String sql="create table cards(id int,name varchar(100))";
    private Context mcontext;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!tabIsExist("test", db)) {

            db.execSQL(sql);
            db.execSQL("insert into cards values(0,'"+"aacc"+"')");
            db.execSQL("insert into cards values(0,'"+"abc"+"')");
            System.out.println("创建表");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    /**
     * 判断是否存在某一张表
     * @param tabName
     * @param db
     * @return
     */
    public boolean tabIsExist(String tabName, SQLiteDatabase db) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
        }
        return result;
    }
}
