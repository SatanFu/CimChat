package com.satan.cimchat.core.android;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.orhanobut.logger.Logger;
import com.satan.cimchat.R;
import com.satan.cimchat.app.BaseApplication;
import com.satan.cimchat.core.nio.constant.CIMConstant;
import com.satan.cimchat.core.nio.mutual.Message;
import com.satan.cimchat.core.nio.mutual.ReplyBody;
import com.satan.cimchat.core.nio.mutual.SentBody;
import com.satan.cimchat.db.ChatDao;
import com.satan.cimchat.model.Chat;
import com.satan.cimchat.ui.fragment.ChatFragment;
import com.satan.cimchat.util.ChangeUtil;


/**
 * 消息入口，所有消息都会经过这里
 *
 * @author 3979434
 */
public abstract class CIMEnventListenerReceiver extends BroadcastReceiver
        implements OnCIMMessageListener {

    public Context context;

    @Override
    public void onReceive(Context ctx, Intent it) {

        context = ctx;

        if (it.getAction().equals(CIMConnectorManager.ACTION_NETWORK_CHANGED)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService("connectivity");
            android.net.NetworkInfo info = connectivityManager
                    .getActiveNetworkInfo();

            onDevicesNetworkChanged(info);
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_CLOSED)) {
            dispatchConnectionClosed();
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_FAILED)) {
            onConnectionFailed((Exception) it.getSerializableExtra("exception"));
        }

        if (it.getAction()
                .equals(CIMConnectorManager.ACTION_CONNECTION_SUCCESS)) {
            dispatchConnectionSucceed();
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_MESSAGE_RECEIVED)) {
            filterType999Message((Message) it.getSerializableExtra("message"));
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_REPLY_RECEIVED)) {
            onReplyReceived((ReplyBody) it.getSerializableExtra("replyBody"));
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_SENT_FAILED)) {
            onSentFailed((Exception) it.getSerializableExtra("exception"),
                    (SentBody) it.getSerializableExtra("sentBody"));
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_SENT_SUCCESS)) {
            onSentSucceed((SentBody) it.getSerializableExtra("sentBody"));
        }

        if (it.getAction()
                .equals(CIMConnectorManager.ACTION_UNCAUGHT_EXCEPTION)) {
            onUncaughtException((Exception) it
                    .getSerializableExtra("exception"));
        }

        if (it.getAction().equals(CIMConnectorManager.ACTION_CONNECTION_STATUS)) {
            onConnectionStatus(it.getBooleanExtra(
                    CIMPushManager.KEY_CIM_CONNECTION_STATUS, false));
        }

    }

    private void dispatchConnectionClosed() {

        if (CIMConnectorManager.netWorkAvailable(context)) {
            CIMPushManager.init(context);
        }

        onConnectionClosed();
    }

    protected boolean isInBackground(Context context) {
        List<RunningTaskInfo> tasksInfo = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
        if (tasksInfo.size() > 0) {

            if (context.getPackageName().equals(
                    tasksInfo.get(0).topActivity.getPackageName())) {

                return false;
            }
        }
        return true;
    }

    private void onConnectionFailed(Exception e) {

        if (CIMConnectorManager.netWorkAvailable(context)) {
            // 间隔30秒后重连
            connectionHandler.sendMessageDelayed(
                    connectionHandler.obtainMessage(), 30 * 1000);
        }
    }

    Handler connectionHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            CIMPushManager.init(context);
        }

    };

    private void dispatchConnectionSucceed() {

        CIMPushManager.setAccount(context);
        onConnectionSucceed();

    }

    private void onUncaughtException(Throwable arg0) {
    }

    private void onDevicesNetworkChanged(NetworkInfo info) {

        if (info != null) {
            CIMPushManager.init(context);
        }

        onNetworkChanged(info);
    }

    private void filterType999Message(Message message) {
        if (CIMConstant.MessageType.TYPE_999.equals(message.getType())) {
            CIMDataConfig.putBoolean(context, CIMDataConfig.KEY_MANUAL_STOP, true);
        } else {
            MediaPlayer.create(context, R.raw.classic).start();
            BaseApplication.getMessageDao(context).insert(ChangeUtil.NioMsgToMyMsg(message));
            Chat chat = BaseApplication.getChatDao(context).queryBuilder().where(ChatDao.Properties.Sender.eq(message.getSender())).unique();
            if (chat == null) {
                chat = new Chat();
                chat.setNewNum(1);
                Logger.e(chat.getNewNum() + "-----num---null");
                chat.setTime(message.getTimestamp());
                chat.setSender(message.getSender());
                chat.setContent(message.getContent());
                BaseApplication.getChatDao(context).insert(chat);
            } else {
                Logger.e(chat.getNewNum() + "-----num");
                chat.setNewNum(chat.getNewNum() + 1);
                chat.setTime(message.getTimestamp());
                chat.setSender(message.getSender());
                chat.setContent(message.getContent());
                BaseApplication.getChatDao(context).update(chat);
            }

            Intent intent = new Intent();
            intent.setAction(ChatFragment.NEW_MSG_ACTION);
            context.sendBroadcast(intent);
        }
        Logger.e(message.getContent() + "----" + message.getReceiver() + "----" + message.getSender());
        onMessageReceived(message);
    }

    private void onSentFailed(Exception e, SentBody body) {

        // 与服务端端开链接，重新连接
        if (e instanceof CIMSessionDisableException) {
            CIMPushManager.init(context);
        } else {
            // 发送失败 重新发送
            CIMPushManager.sendRequest(context, body);
        }

    }

    private void onSentSucceed(SentBody body) {
    }

    @Override
    public abstract void onMessageReceived(Message message);

    @Override
    public abstract void onReplyReceived(ReplyBody body);

    public abstract void onNetworkChanged(NetworkInfo info);

}