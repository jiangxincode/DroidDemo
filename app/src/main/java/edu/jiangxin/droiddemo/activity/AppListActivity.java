package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.x500.X500Principal;

import edu.jiangxin.droiddemo.R;

public class AppListActivity extends Activity implements OnClickListener {

	// 当前上下文
	private Context mContext;
	// ================ 控件View ================
	// HintTv
	private TextView am_hint_tv;
	// 显示App信息
	private ListView am_listview;
	// ================ 其他对象  ================
	// 获取全部app信息
	private ArrayList<PackageInfo> listPInfos = new ArrayList<PackageInfo>();
	// 适配器
	private AppAdapter aAdapter;
	// 获取图片、应用名、包名  
	private PackageManager pManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_list);
		mContext = AppListActivity.this;
		am_hint_tv = this.findViewById(R.id.am_hint_tv);
		am_listview = this.findViewById(R.id.am_listview);
		// 字体加粗
		TextPaint tp = am_hint_tv.getPaint();
		tp.setFakeBoldText(true);
		// 初始化
		pManager = mContext.getPackageManager();
		// 初始化APP数据
		getAllApps();
		// 初始化适配器,并绑定
		aAdapter = new AppAdapter();
		am_listview.setAdapter(aAdapter);
		am_listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> vAdapter, View view, int position,long id) {
				try {
					// 原始的PackageInfo
					PackageInfo oPInfo = listPInfos.get(position);
					// 获取包名
					String packName = oPInfo.applicationInfo.packageName;

					Intent intent = new Intent(mContext,SignaturesActivity.class);
					intent.putExtra("packName", packName);
					mContext.startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
					showToast("获取App信息失败");
				}
			}
		});
	}

	

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	/**
	 * 重写返回键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppListActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	// =============================  其他操作  =============================
	/**
	 * 获取全部app信息
	 */
	@SuppressWarnings("static-access")
	public void getAllApps() {
		this.listPInfos.clear(); // 清空旧数据
		// 管理应用程序包
		PackageManager pManager = mContext.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> packageInfos = pManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
			// packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM  如果<=0则为自己装的程序，否则为系统工程自带
			this.listPInfos.add(packageInfo);
        }
	}
	
	// =============================  适配器    =============================
	class AppAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return listPInfos.size();
		}

		@Override
		public PackageInfo getItem(int position) {
			return listPInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				// 导入XML
				convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_app_list_item, null);
				holder = new ViewHolder();
				convertView.setTag(holder);
				holder.ai_igview = convertView.findViewById(R.id.ai_igview);
				holder.ai_name_tv = convertView.findViewById(R.id.ai_name_tv);
				holder.ai_pack_tv = convertView.findViewById(R.id.ai_pack_tv);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 获取App信息
			PackageInfo pInfo = this.getItem(position);
			// 包名
			String aPack = pInfo.applicationInfo.packageName;
			// app名
			String aName = pManager.getApplicationLabel(pInfo.applicationInfo).toString();
			// app图标
			Drawable aIcon = pManager.getApplicationIcon(pInfo.applicationInfo);
            // 设置显示信息
			holder.ai_igview.setImageDrawable(aIcon); // 设置图标
			holder.ai_name_tv.setText(aName); // 设置app名
			holder.ai_pack_tv.setText(aPack); // 设置pack名
			return convertView;
		}
		
		class ViewHolder {
			ImageView ai_igview;
			TextView ai_name_tv;
			TextView ai_pack_tv;
		}
	}
	
	// =================== 显示Dialog =====================
	private Toast mToast;
	private void showToast(String hint){
		if (mToast != null) {
			mToast.setText(hint);
		} else {
			mToast = Toast.makeText(mContext, hint, Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
}

/**
 * 签名信息
 */
class SignaturesMsg {

	// 如需要小写则把ABCDEF改成小写
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 检测应用程序是否是用"CN=Android Debug,O=Android,C=US"的debug信息来签名的
	 * 判断签名是debug签名还是release签名
	 */
	private final static X500Principal DEBUG_DN = new X500Principal(
			"CN=Android Debug,O=Android,C=US");

	/**
	 * 进行转换
	 */
	public static String toHexString(byte[] bData) {
		StringBuilder sb = new StringBuilder(bData.length * 2);
		for (int i = 0; i < bData.length; i++) {
			sb.append(HEX_DIGITS[(bData[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[bData[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 返回MD5
	 */
	public static String signatureMD5(Signature[] signatures) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			if (signatures != null) {
				for (Signature s : signatures)
					digest.update(s.toByteArray());
			}
			return toHexString(digest.digest());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * SHA1
	 */
	public static String signatureSHA1(Signature[] signatures) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			if (signatures != null) {
				for (Signature s : signatures)
					digest.update(s.toByteArray());
			}
			return toHexString(digest.digest());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * SHA256
	 */
	public static String signatureSHA256(Signature[] signatures) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			if (signatures != null) {
				for (Signature s : signatures)
					digest.update(s.toByteArray());
			}
			return toHexString(digest.digest());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断签名是debug签名还是release签名
	 * @return true = 开发(debug.keystore)，false = 上线发布（非.android默认debug.keystore）
	 */
	public static boolean isDebuggable(Signature[] signatures) {
		// 判断是否默认key(默认是)
		boolean debuggable = true;
		try {
			for (int i = 0, c = signatures.length; i < c; i++) {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
				X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
				debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
				if (debuggable) {
					break;
				}
			}
		} catch (Exception e) {
		}
		return debuggable;
	}

	/**
	 * 获取App 证书对象
	 */
	public static X509Certificate getX509Certificate(Signature[] signatures){
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ByteArrayInputStream stream = new ByteArrayInputStream(signatures[0].toByteArray());
			X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
			return cert;
		} catch (Exception e) {
		}
		return null;
	}

	public static String signatureName(Signature[] signatures){
		try {
			for (int i = 0, c = signatures.length; i < c; i++) {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
				X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);

				String pubKey = cert.getPublicKey().toString();   //公钥
				String signNumber = cert.getSerialNumber().toString();
				System.out.println("signName:" + cert.getSigAlgName());//算法名
				System.out.println("pubKey:" + pubKey);
				System.out.println("signNumber:" + signNumber);//证书序列编号
				System.out.println("subjectDN:"+cert.getSubjectDN().toString());

				System.out.println(cert.getNotAfter() + "--" + cert.getNotBefore());
			}
		} catch (Exception e) {
		}
		return "";
	}
}

