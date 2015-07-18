package com.satan.cimchat.app;

import android.app.Application;
import android.content.Context;

import com.satan.cimchat.db.ChatDao;
import com.satan.cimchat.db.ContactDao;
import com.satan.cimchat.db.DaoMaster;
import com.satan.cimchat.db.DaoSession;
import com.satan.cimchat.db.MessageDao;
import com.satan.cimchat.db.UserDao;

/**
 * Created by Satan on 2015/7/18.
 */
public class BaseApplication extends Application {


    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, "cimchat", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 获取UserDao
     *
     * @param context
     * @return
     */
    public static UserDao getUserDao(Context context) {
        return getDaoSession(context).getUserDao();
    }

    /**
     * 获取ChatDao
     *
     * @param context
     * @return
     */
    public static ChatDao getChatDao(Context context) {
        return getDaoSession(context).getChatDao();
    }

    /**
     * 获取MessageDao
     *
     * @param context
     * @return
     */
    public static MessageDao getMessageDao(Context context) {
        return getDaoSession(context).getMessageDao();
    }

    /**
     * 获取ContactDao
     *
     * @param context
     * @return
     */
    public static ContactDao getContactDao(Context context) {
        return getDaoSession(context).getContactDao();
    }
}
