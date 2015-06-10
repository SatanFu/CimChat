package com.satan.cimchat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.satan.cimchat.model.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendDao {
    private DbHelper helper = null;

    public FriendDao(Context context) {
        helper = new DbHelper(context);
    }

    public List<Friend> getAllFriend() {
        List<Friend> friendList = new ArrayList<Friend>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from friend";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Friend friend = new Friend();
            friend.setUid(cursor.getInt(0));
            friend.setAccount(cursor.getString(1));
            friend.setUserName(cursor.getString(2));
            friendList.add(friend);
        }
        cursor.close();
        db.close();
        return friendList;
    }

    public void addMessage(Friend friend) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("account", friend.getAccount());
            values.put("username", friend.getUserName());
            db.insert("friend", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void delAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "delete from friend";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
