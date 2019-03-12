package edu.jiangxin.droiddemo.quickshow.runable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.UserHandle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;

public class UserInfoRunnable implements Runnable {

    private Handler handler;

    public UserInfoRunnable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Process.myUid(): " + Process.myUid()).append("\n");
        stringBuilder.append("Process.myPid(): " + Process.myPid()).append("\n");
        stringBuilder.append("Process.myTid(): " + Process.myTid()).append("\n");

        try {
            Method getUserId = UserHandle.class.getMethod("getUserId", int.class);
            int userUserId = (Integer) getUserId.invoke(null, Process.myUid());
            stringBuilder.append("UserHandle.getUserId(int uid): " + userUserId).append("\n");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示用户信息");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
