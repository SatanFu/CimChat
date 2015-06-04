package com.satan.cimchat.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * Created by Administrator on 2015/5/21.
 */
public class BaseAPI {



    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(2000);
        // client.addHeader("Content-Type", "text/html");
        client.addHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=utf-8");
    }


    public static void getAsyncHttpResponse(String url, RequestParams params,
                                            TextHttpResponseHandler responseHandler) {


        client.get(url, params, responseHandler);

    }


    public static void postAsyncHttpResponse(String url, RequestParams params,
                                             TextHttpResponseHandler responseHandler) {

        client.post(url, params, responseHandler);
    }


}
