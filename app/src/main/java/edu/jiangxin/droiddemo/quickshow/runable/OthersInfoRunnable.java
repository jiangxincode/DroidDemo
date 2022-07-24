package edu.jiangxin.droiddemo.quickshow.runable;

import android.app.KeyguardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import edu.jiangxin.droiddemo.ApplicationExt;
import edu.jiangxin.droiddemo.quickshow.activity.ShowInfoActivity;

public class OthersInfoRunnable implements Runnable {

    private final Handler handler;

    public OthersInfoRunnable(Handler mHandler) {
        this.handler = mHandler;
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        PowerManager pm = (PowerManager) ApplicationExt.getContext().getSystemService(Context.POWER_SERVICE);
        //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了
        stringBuilder.append("isInteractive: ").append(pm.isInteractive()).append("\n");
        //如果flag为true，表示有两种状态：a、屏幕是黑的 b、目前正处于解锁状态 。
        // 如果flag为false，表示目前未锁屏
        KeyguardManager mKeyguardManager = (KeyguardManager) ApplicationExt.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        stringBuilder.append("isKeyguardLocked: ").append(mKeyguardManager.isKeyguardLocked()).append("\n");
        stringBuilder.append("IP address: ").append(getIPAddress(ApplicationExt.getContext()));

        Message message = new Message();
        message.what = ShowInfoActivity.UPDATE_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("title", "显示屏幕状态");
        bundle.putString("content", stringBuilder.toString());
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        }

        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
