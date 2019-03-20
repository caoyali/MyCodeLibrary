package com.example.forev.mycodelibrary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MainDataBaseHelper extends SQLiteOpenHelper {
    //数据库的名称
    public static final String DBNAME = "mydb";

    //sql建表语句，建立一个叫main的表
    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            "main " +                       // Table's name
            "(" +                           // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            " WORD TEXT" +
            " FREQUENCY INTEGER " +
            " LOCALE TEXT )";

    public MainDataBaseHelper(@Nullable Context context) {
        /**
         *
         * @param context
         * @param name 数据库的名称
         * @param factory
         * @param version 数据库版本号
         */
        super(context, DBNAME, null, 1);
    }

    /**
     * 这个方法只有在尝试打开数据库数据或者SQLLite 报告的时候发现不存在匹配的结果，才会调用！
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
