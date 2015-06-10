package com.satan.cimchat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.satan.cimchat.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private DbHelper helper = null;

    public MessageDao(Context context) {
        helper = new DbHelper(context);
    }

    public List<Message> getAllMessage() {
        List<Message> msgList = new ArrayList<Message>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from message";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Message msg = new Message();
            msg.setMid(cursor.getInt(0));
            msg.setType(cursor.getString(1));
            msg.setContent(cursor.getString(2));
            msg.setSender(cursor.getString(3));
            msg.setReceiver(cursor.getString(4));
            msg.setStatus(cursor.getInt(5));
            msg.setTimestamp(cursor.getLong(6));
            msg.setFile(cursor.getString(7));
            msg.setFileType(cursor.getString(8));
            msgList.add(msg);
        }
        cursor.close();
        db.close();
        return msgList;
    }

    public void addMessage(Message msg) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("type", msg.getType());
            values.put("title", msg.getTitle());
            values.put("content", msg.getContent());
            values.put("sender", msg.getSender());
            values.put("receiver", msg.getReceiver());
            values.put("status", msg.getStatus());
            values.put("time", msg.getTimestamp());
            values.put("file", msg.getFile());
            values.put("file_type", msg.getFileType());
            db.insert("message", null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<Message> getMessageBySender(String account) {
        List<Message> msgList = new ArrayList<Message>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from message where sender = " + account;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Message msg = new Message();
            msg.setMid(cursor.getInt(0));
            msg.setType(cursor.getString(1));
            msg.setContent(cursor.getString(2));
            msg.setSender(cursor.getString(3));
            msg.setReceiver(cursor.getString(4));
            msg.setStatus(cursor.getInt(5));
            msg.setTimestamp(cursor.getLong(6));
            msg.setFile(cursor.getString(7));
            msg.setFileType(cursor.getString(8));
            msgList.add(msg);
        }
        cursor.close();
        db.close();
        return msgList;
    }

    public void delAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            String sql = "delete from message";
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
