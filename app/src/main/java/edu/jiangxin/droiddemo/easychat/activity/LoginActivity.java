package edu.jiangxin.droiddemo.easychat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.jiangxin.droiddemo.easychat.service.IMService;
import edu.jiangxin.droiddemo.easychat.service.PushService;
import edu.jiangxin.droiddemo.easychat.utils.ThreadUtils;
import edu.jiangxin.droiddemo.easychat.utils.ToastUtils;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityJid;

import java.net.InetAddress;

import edu.jiangxin.droiddemo.R;

public class LoginActivity extends AppCompatActivity {

    public static final String HOST = "192.168.0.125";

    public static final int PORT = 5222;
    public static final String SERVICENAME = "itheima.com";
    private TextView mEtUserName;
    private TextView mEtPassWord;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mEtUserName.getText().toString();
                final String passWord = mEtPassWord.getText().toString();
                // 判断用户名是否为空
                if (TextUtils.isEmpty(userName)) {// 用户名为空
                    mEtUserName.setError("用户名不能为空");
                    return;
                }
                // 判断密码是否为空
                if (TextUtils.isEmpty(passWord)) {// 用户名为空
                    mEtPassWord.setError("密码不能为空");
                    return;
                }

                ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InetAddress addr = InetAddress.getByName(HOST);
                            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                                    .setUsernameAndPassword("username", "password")
                                    .setHost(HOST)
                                    .setPort(PORT)
                                    .setXmppDomain("edu.jiangxin.easychat")
                                    //https://stackoverflow.com/questions/43160061/failed-because-de-measite-minidns-hla-resolutionunsuccessfulexception-asking-f/43164006#43164006
                                    .setHostAddress(addr)
                                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)// 明文传输
                                    .setDebuggerEnabled(true)// 开启调试模式,方便我们查看具体发送的内容
                                    .build();

                            AbstractXMPPConnection conn = new XMPPTCPConnection(config);

                            conn.connect();

                            conn.login(userName, passWord);

                            ToastUtils.showToastSafe(LoginActivity.this, "登录成功");

                            finish();
                            // 跳到主界面
                            Intent intent = new Intent(LoginActivity.this, EasyChatActivity.class);
                            startActivity(intent);

                            // 需要保存连接对象
                            IMService.conn = conn;

                            // 保存当前登录的账户
                            EntityJid account = conn.getUser();
                            IMService.mCurAccout = account;// admin--->admin@itheima.com

                            // 启动IMService
                            Intent service = new Intent(LoginActivity.this, IMService.class);
                            startService(service);
                            // 启动PushService
                            Intent pushService = new Intent(LoginActivity.this, PushService.class);
                            startService(pushService);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showToastSafe(LoginActivity.this, "登录失败");
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        mEtUserName = (TextView) findViewById(R.id.et_username);
        mEtPassWord = (TextView) findViewById(R.id.et_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }
}
