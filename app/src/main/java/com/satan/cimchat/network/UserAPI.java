package com.satan.cimchat.network;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.satan.cimchat.app.Constant;

/**
 * Created by Administrator on 2015/5/31.
 */
public class UserAPI extends BaseAPI {

    private static String URL = Constant.SERVER_URL + "/cgi";

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param responseHandler
     */
    public static void register(String username, String password, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        postAsyncHttpResponse(URL + "/user_register.api", params, responseHandler);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @param responseHandler
     */
    public static void login(String account, String password, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("account", account);
        params.put("password", password);
        postAsyncHttpResponse(URL + "/user_login.api", params, responseHandler);
    }

    /**
     * 获取所有用户
     *
     * @param responseHandler
     */
    public static void getAllUser(TextHttpResponseHandler responseHandler) {

        postAsyncHttpResponse(URL + "/user_getAllUser.api", null, responseHandler);
    }

    /**
     * 获取我的朋友
     *
     * @param id
     * @param responseHandler
     */
    public static void getFriend(int id, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        postAsyncHttpResponse(URL + "/user_getFriend.api", params, responseHandler);
    }

    /**
     * 获取我的朋友
     *
     * @param userId
     * @param friendId
     * @param responseHandler
     */
    public static void addFriend(int userId, int friendId, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("user_id", userId);
        params.put("friend_id", friendId);
        postAsyncHttpResponse(URL + "/user_addFriend.api", params, responseHandler);
    }

}
