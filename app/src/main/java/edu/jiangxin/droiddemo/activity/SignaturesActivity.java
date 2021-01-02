package edu.jiangxin.droiddemo.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import javax.security.auth.x500.X500Principal;

import edu.jiangxin.droiddemo.R;

public class SignaturesActivity extends Activity implements OnClickListener {

	// 当前上下文
	private Context mContext;
	// ================ 控件View ================
	// 图片Logo
	private ImageView as_igview;
	// app名
	private TextView as_name_tv;
	// 包名
	private TextView as_pack_tv;
	// MD5
	private TextView as_md5_tv;
	// MD5 Linear 点击复制
	private LinearLayout as_md5_linear;
	// SHA1
	private TextView as_sha1_tv;
	// SHA1 Linear 点击复制
	private LinearLayout as_sha1_linear;
	// SHA256
	private TextView as_sha256_tv;
	// SHA256 Linear 点击复制
	private LinearLayout as_sha256_linear;
	// 签名有效期
	private TextView as_effective_tv;
	// 签名有效期 Linear 点击复制
	private LinearLayout as_effective_linear;
	// 判断签名有效期
	private TextView as_iseffective_tv;
	// 判断签名有效期 Linear 点击复制
	private LinearLayout as_iseffective_linear;
	// 证书发布方
	private TextView as_principal_tv;
	// 证书发布方 Linear 点击复制
	private LinearLayout as_principal_linear;
	// 证书版本号
	private TextView as_version_tv;
	// 证书版本号 Linear 点击复制
	private LinearLayout as_version_linear;
	// 证书算法名称
	private TextView as_sigalgname_tv;
	// 证书算法名称 Linear 点击复制
	private LinearLayout as_sigalgname_linear;
	// 证书算法OID
	private TextView as_sigalgoid_tv;
	// 证书算法OID Linear 点击复制
	private LinearLayout as_sigalgoid_linear;
	// 证书机器码
	private TextView as_serialnumber_tv;
	// 证书机器码 Linear 点击复制
	private LinearLayout as_serialnumber_linear;
	// 证书DER编码
	private TextView as_dercode_tv;
	// 证书DER编码 Linear 点击复制
	private LinearLayout as_dercode_linear;
	// ================ 其他对象  ================
	// 获取图片、应用名、包名  
	private PackageManager pManager;
	// 项目包名
	private String packName;
	// 格式化日期
	private final SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signatures);
		mContext = SignaturesActivity.this;
		
		Intent intent = this.getIntent();
		if(intent != null){
			packName = intent.getStringExtra("packName");
		} else {
			showToast("包名为null");
			this.finish();
		}
		// 初始化操作
		initViews();
		initValues();
		initListeners();
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		as_igview = this.findViewById(R.id.as_igview);
		as_name_tv = this.findViewById(R.id.as_name_tv);
		as_pack_tv = this.findViewById(R.id.as_pack_tv);
		as_md5_tv = this.findViewById(R.id.as_md5_tv);
		as_sha1_tv = this.findViewById(R.id.as_sha1_tv);
		as_sha256_tv = this.findViewById(R.id.as_sha256_tv);
		as_effective_tv = this.findViewById(R.id.as_effective_tv);
		as_iseffective_tv = this.findViewById(R.id.as_iseffective_tv);
		as_principal_tv = this.findViewById(R.id.as_principal_tv);
		as_version_tv = this.findViewById(R.id.as_version_tv);
		as_sigalgname_tv = this.findViewById(R.id.as_sigalgname_tv);
		as_sigalgoid_tv = this.findViewById(R.id.as_sigalgoid_tv);
		as_serialnumber_tv = this.findViewById(R.id.as_serialnumber_tv);
		as_dercode_tv = this.findViewById(R.id.as_dercode_tv);
		
		
		as_md5_linear = this.findViewById(R.id.as_md5_linear);
		as_sha1_linear = this.findViewById(R.id.as_sha1_linear);
		as_sha256_linear = this.findViewById(R.id.as_sha256_linear);
		as_effective_linear = this.findViewById(R.id.as_effective_linear);
		as_iseffective_linear = this.findViewById(R.id.as_iseffective_linear);
		as_principal_linear = this.findViewById(R.id.as_principal_linear);
		as_version_linear = this.findViewById(R.id.as_version_linear);
		as_sigalgname_linear = this.findViewById(R.id.as_sigalgname_linear);
		as_sigalgoid_linear = this.findViewById(R.id.as_sigalgoid_linear);
		as_serialnumber_linear = this.findViewById(R.id.as_serialnumber_linear);
		as_dercode_linear = this.findViewById(R.id.as_dercode_linear);
	}

	/**
	 * 初始化数据
	 */
	private void initValues() {
		// 初始化
		pManager = mContext.getPackageManager();
		try {
			// 获取对应的PackageInfo(原始的PackageInfo 获取 signatures 等于null,需要这样获取)
			PackageInfo pInfo = pManager.getPackageInfo(packName, PackageManager.GET_SIGNATURES);
			// 包名
			String aPack = pInfo.applicationInfo.packageName;
			// app名
			String aName = pManager.getApplicationLabel(pInfo.applicationInfo).toString();
			// app图标
			Drawable aIcon = pManager.getApplicationIcon(pInfo.applicationInfo);
	        // 设置显示信息
			as_igview.setImageDrawable(aIcon); // 设置图标
			as_name_tv.setText(aName); // 设置app名
			as_pack_tv.setText(aPack); // 设置pack名
			as_md5_tv.setText(SignaturesMsg.signatureMD5(pInfo.signatures)); // 设置MD5
			as_sha1_tv.setText(SignaturesMsg.signatureSHA1(pInfo.signatures)); // 设置SHA1
			as_sha256_tv.setText(SignaturesMsg.signatureSHA256(pInfo.signatures)); // 设置SHA256
			// 获取证书对象
			X509Certificate cert = SignaturesMsg.getX509Certificate(pInfo.signatures);
			// 设置有效期
			StringBuilder sbEffective = new StringBuilder();
			sbEffective.append(dFormat.format(cert.getNotBefore()));
			sbEffective.append("\t至\t");
			sbEffective.append(dFormat.format(cert.getNotAfter()));
			sbEffective.append("\n\n");
			sbEffective.append(cert.getNotBefore());
			sbEffective.append("\t至\t");
			sbEffective.append(cert.getNotAfter());
			as_effective_tv.setText(sbEffective.toString());
			// 证书是否过期 true = 过期,false = 未过期
			boolean isEffective = false;
			try {
				cert.checkValidity();
				// CertificateExpiredException - 如果证书已过期。
				// CertificateNotYetValidException - 如果证书不再有效。
			} catch (CertificateExpiredException ce) {
				isEffective = true;
			} catch (CertificateNotYetValidException ce) {
				isEffective = true;
			}
			as_iseffective_tv.setText(isEffective?getString(R.string.overdue_hint_s):getString(R.string.notoverdue_hint_s));
			// 证书发布方
			String principal = cert.getIssuerX500Principal().toString();
			as_principal_tv.setText(principal);
			// 证书版本号
			as_version_tv.setText(cert.getVersion()+"");
			// 证书算法名称
			as_sigalgname_tv.setText(cert.getSigAlgName());
			// 证书算法OID
			as_sigalgoid_tv.setText(cert.getSigAlgOID());
			// 证书机器码
			as_serialnumber_tv.setText(cert.getSerialNumber().toString());
			// 证书 DER编码
			as_dercode_tv.setText(SignaturesMsg.toHexString(cert.getTBSCertificate()));
			
			
			// 获取证书发行者   可根据证书发行者来判断该应用是否被二次打包（被破解的应用重新打包后，签名与原包一定不同，据此可以判断出该应用是否被人做过改动）
			// String[] certMsg = new String[2];
			// certMsg[0] = cert.getIssuerDN().toString();
			// certMsg[1] = cert.getSubjectDN().toString();
		} catch (Exception e) {
			e.printStackTrace();
			showToast("获取异常：" + e.toString());
		}
	}

	/**
	 * 初始化事件
	 */
	private void initListeners() {
		as_md5_linear.setOnClickListener(this);
		as_sha1_linear.setOnClickListener(this);
		as_sha256_linear.setOnClickListener(this);
		as_effective_linear.setOnClickListener(this);
		as_iseffective_linear.setOnClickListener(this);
		as_principal_linear.setOnClickListener(this);
		as_version_linear.setOnClickListener(this);
		as_sigalgname_linear.setOnClickListener(this);
		as_sigalgoid_linear.setOnClickListener(this);
		as_serialnumber_linear.setOnClickListener(this);
		as_dercode_linear.setOnClickListener(this);
	}
	
	

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.as_md5_linear:
			setClipboardText(as_md5_tv);
			break;
		case R.id.as_sha1_linear:
			setClipboardText(as_sha1_tv);
			break;
		case R.id.as_sha256_linear:
			setClipboardText(as_sha256_tv);
			break;
		case R.id.as_effective_linear:
			setClipboardText(as_effective_tv);
			break;
		case R.id.as_iseffective_linear:
			setClipboardText(as_iseffective_tv);
			break;
		case R.id.as_principal_linear:
			setClipboardText(as_principal_tv);
			break;
		case R.id.as_version_linear:
			setClipboardText(as_version_tv);
			break;
		case R.id.as_sigalgname_linear:
			setClipboardText(as_sigalgname_tv);
			break;
		case R.id.as_sigalgoid_linear:
			setClipboardText(as_sigalgoid_tv);
			break;
		case R.id.as_serialnumber_linear:
			setClipboardText(as_serialnumber_tv);
			break;
		case R.id.as_dercode_linear:
			setClipboardText(as_dercode_tv);
			break;
		}
	}

	/**
	 * 重写返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SignaturesActivity.this.finish();
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
	
	// ===================  复制到剪切板 =====================
	private void setClipboardText(TextView tv){
		ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
		// 复制
		clip.setText(tv.getText().toString());
		// 提示
		showToast("已复制到剪切板");
	}
}

/**
 * 签名信息
 */
class SignaturesMsg {

	// 如需要小写则把ABCDEF改成小写
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

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
				for (Signature s : signatures) {
					digest.update(s.toByteArray());
				}
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
				for (Signature s : signatures) {
					digest.update(s.toByteArray());
				}
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
				for (Signature s : signatures) {
					digest.update(s.toByteArray());
				}
			}
			return toHexString(digest.digest());
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取App 证书对象
	 */
	public static X509Certificate getX509Certificate(Signature[] signatures) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ByteArrayInputStream stream = new ByteArrayInputStream(signatures[0].toByteArray());
			X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
			return cert;
		} catch (Exception e) {
		}
		return null;
	}
}
