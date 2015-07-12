package com.satan.cimchat.db;

import java.util.Collections;
import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.satan.cimchat.model.RecentItem;


public class RecentDB {
	public static final String MSG_DBNAME = "message.db";
	private static final String RECENT_TABLE_NAME = "recent";
	private SQLiteDatabase db;

	public RecentDB(Context context) {
		db = context.openOrCreateDatabase(MSG_DBNAME, Context.MODE_PRIVATE,
				null);
		db.execSQL("CREATE table IF NOT EXISTS "
				+ RECENT_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId TEXT, name TEXT, img TEXT,time TEXT,num TEXT,message TEXT)");
	}

	public void saveRecent(RecentItem item) {
		if (isExist(item.getUserId())) {
			ContentValues cv = new ContentValues();
			cv.put("name", item.getName());
			cv.put("img", item.getHeadImg());
			cv.put("time", item.getTime());
			cv.put("num", item.getNewNum());
			cv.put("message", item.getMessage());

			db.update(RECENT_TABLE_NAME, cv, "userId=?",
					new String[] { item.getUserId() });
		} else {
			db.execSQL(
					"insert into "
							+ RECENT_TABLE_NAME
							+ " (userId,name,img,time,num,message) values(?,?,?,?,?,?)",
					new Object[] { item.getUserId(), item.getName(),
							item.getHeadImg(), item.getTime(),
							item.getNewNum(), item.getMessage() });
		}
	}

	public LinkedList<RecentItem> getRecentList() {
		LinkedList<RecentItem> list = new LinkedList<RecentItem>();
		Cursor c = db.rawQuery("SELECT * from " + RECENT_TABLE_NAME, null);
		while (c.moveToNext()) {
			String userId = c.getString(c.getColumnIndex("userId"));
			String name = c.getString(c.getColumnIndex("name"));
			int icon = c.getInt(c.getColumnIndex("img"));
			long time = c.getLong(c.getColumnIndex("time"));
			int num = c.getInt(c.getColumnIndex("num"));
			String message = c.getString(c.getColumnIndex("message"));
			RecentItem item = new RecentItem(userId, icon, name, message, num,
					time);
			list.add(item);
		}
		Collections.sort(list);// 按时间降序
		return list;
	}

	public void delRecent(String userId) {
		db.delete(RECENT_TABLE_NAME, "userId=?", new String[] { userId });
	}

	private boolean isExist(String userId) {
		Cursor c = db.rawQuery("SELECT * FROM " + RECENT_TABLE_NAME
				+ " WHERE userId = ?", new String[] { userId });
		return c.moveToFirst();
	}

	public void close() {
		if (db != null)
			db.close();
	}
}
