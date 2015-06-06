package com.satan.cimchat.util;

import android.content.Context;
import android.graphics.Color;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Administrator on 2015/6/6.
 */
public class DialogUtil {

    private static MaterialDialog dialog;

    public static void showProgressDialog(Context context, String content) {
        dialog = new MaterialDialog.Builder(context).backgroundColor(Color.WHITE).content(content).progress(true, 0).show();
    }

    public static void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
