package com.satan.cimchat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static String name = "cim_db.db";
    private static int version = 1;

    public DbHelper(Context context) {
        super(context, name, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String friendSql = "create table friend(uid integer primary key autoincrement,account varchar(255), username varchar(255)";
        db.execSQL(friendSql);

        String msgSql = "create table message(mid integer primary key autoincrement,type varchar(255), title varchar(255), content varchar, sender varchar(255), receiver varchar(255), status int, time long, file varchar(255), file_type varchar(255)";
        db.execSQL(msgSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
