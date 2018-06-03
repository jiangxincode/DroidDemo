package edu.jiangxin.easymarry.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import edu.jiangxin.easymarry.R;
import edu.jiangxin.easymarry.view.PullDownTextView;

public class ShowInfoActivity extends AppCompatActivity {

    private static final String TAG = "ShowInfoActivity";

    private ListView lv;

    private ShowInfoAdapter adapter;
    private List<ShowInfoContent> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        menuList = new ArrayList<ShowInfoContent>();


        StringBuilder sb01 = new StringBuilder();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了
        sb01.append("isScreenOn: " + pm.isScreenOn()).append("\n");
        //如果flag为true，表示有两种状态：a、屏幕是黑的 b、目前正处于解锁状态 。
        // 如果flag为false，表示目前未锁屏
        KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        sb01.append("inKeyguardRestrictedInputMode: " + mKeyguardManager.inKeyguardRestrictedInputMode()).append("\n");
        sb01.append("IP address: " + getIPAddress(this));



        menuList.add(new ShowInfoContent("显示屏幕状态", sb01.toString()));

        StringBuilder sb02 = new StringBuilder();
        sb02.append("Process.myUid(): " + Process.myUid()).append("\n");
        sb02.append("Process.myPid(): " + Process.myPid()).append("\n");
        sb02.append("Process.myTid(): " + Process.myTid()).append("\n");

        try {
            Method getUserId = UserHandle.class.getMethod("getUserId", int.class);
            int userUserId = (Integer) getUserId.invoke(null, Process.myUid());
            sb02.append("UserHandle.getUserId(int uid): " + userUserId).append("\n");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        menuList.add(new ShowInfoContent("显示用户信息", sb02.toString()));

        StringBuilder sb03 = new StringBuilder();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        sb03.append("Screen width(px): " + metric.widthPixels).append("\n"); // 屏幕宽度（像素）
        sb03.append("Screen height(px): " + metric.heightPixels).append("\n"); // 屏幕高度（像素）
        sb03.append("Screen density: " + metric.density).append("\n"); // 屏幕密度（0.75 / 1.0 / 1.5）
        sb03.append("Screen densityDpi(dpi): " + metric.densityDpi).append("\n"); // 屏幕密度DPI（120 / 160 / 240）
        sb03.append("Screen width(inch): " + (float) metric.widthPixels / metric.densityDpi).append("\n");
        sb03.append("Screen height(inch): " + (float) metric.heightPixels / metric.densityDpi).append("\n");
        sb03.append("Screen width(dp): " + (float) metric.widthPixels / metric.density).append("\n");
        sb03.append("Screen height(dp): " + (float) metric.heightPixels / metric.density).append("\n");

        menuList.add(new ShowInfoContent("显示屏幕信息", sb03.toString()));


        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        PackageInfo packageInfo = packageInfos.get(0);
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        Intent startIntent = new Intent(Intent.ACTION_MAIN, null);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(startIntent, 0);
        ResolveInfo resolveInfo = resolveInfos.get(0);
        ActivityInfo activityInfo = resolveInfo.activityInfo;

        lv = findViewById(R.id.lv_show_info);

        adapter = new ShowInfoAdapter(this, menuList);
        lv.setAdapter(adapter);
    }



    public static String getIPAddress(Context context) {
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
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}

class ShowInfoAdapter extends BaseAdapter {

    private List<ShowInfoContent> mMenuList;
    private Context mContext;

    public ShowInfoAdapter(Context context, List<ShowInfoContent> mMenuList) {
        this.mContext = context;
        this.mMenuList = mMenuList;
    }

    @Override
    public int getCount() {
        return mMenuList.size();
    }

    @Override
    public ShowInfoContent getItem(int position) {
        return mMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ShowInfoViewHolder holder = null;

        if (view == null) {
            holder = new ShowInfoViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_show_info_item, null);
            holder.tvTitle = view.findViewById(R.id.expand_text_view_title);
            holder.pdtvContent = view.findViewById(R.id.expand_text_view);

            view.setTag(holder);
        } else {
            holder = (ShowInfoViewHolder) view.getTag();
        }
        holder.tvTitle.setText(mMenuList.get(position).title);
        holder.pdtvContent.setText(mMenuList.get(position).content);
        return view;
    }


}

class ShowInfoContent {
    public ShowInfoContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    String title;
    String content;
}

class ShowInfoViewHolder {
    TextView tvTitle;
    PullDownTextView pdtvContent;
}
