package edu.jiangxin.droiddemo.applist;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
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

	private Context mContext;

	private ImageView mLogoIv;

	private TextView mNameTv;

	private TextView mPackageNameTv;

	private TextView mIsSystemSignatureTv;

	private TextView mMd5Tv;
	private LinearLayout mMd5LinearLayout;

	private TextView mSha1Tv;
	private LinearLayout mSha1LinearLayout;

	private TextView mSha256Tv;
	private LinearLayout mSha256LinearLayout;

	private TextView mEffectiveTv;
	private LinearLayout mEffectiveLinearLayout;

	private TextView mIsEffectiveTv;

	private TextView mPrincipalTv;
	private LinearLayout mPrincipalLinearLayout;

	private TextView mVersionTv;
	private LinearLayout mVersionLinearLayout;

	private TextView mSigAlgNameTv;
	private LinearLayout mSigAlgNameLinearLayout;

	private TextView mSigAlgOidTv;
	private LinearLayout mSigAlgOidLinearLayout;

	private TextView mSerialNumberTv;
	private LinearLayout mSerialNumberLinearLayout;

	private TextView mDerCodeTv;
	private LinearLayout mDerCodeLinearLayout;

	private PackageManager mPackageManager;

	private String mPackageName;

	private final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signatures);
		mContext = this;

		Intent intent = getIntent();
		if (intent != null) {
			mPackageName = intent.getStringExtra("packageName");
		} else {
			showToast("packageName is null");
			finish();
		}
		initViews();
		initValues();
		initListeners();
	}

	private void initViews() {
		mLogoIv = this.findViewById(R.id.LogoIv);
		mNameTv = this.findViewById(R.id.NameTv);
		mPackageNameTv = this.findViewById(R.id.PackageNameTv);
		mIsSystemSignatureTv = this.findViewById(R.id.IsSystemSignatureTv);
		mMd5Tv = this.findViewById(R.id.Md5Tv);
		mSha1Tv = this.findViewById(R.id.Sha1Tv);
		mSha256Tv = this.findViewById(R.id.Sha256Tv);
		mEffectiveTv = this.findViewById(R.id.EffectiveTv);
		mIsEffectiveTv = this.findViewById(R.id.IsEffectiveTv);
		mPrincipalTv = this.findViewById(R.id.PrincipalTv);
		mVersionTv = this.findViewById(R.id.VersionTv);
		mSigAlgNameTv = this.findViewById(R.id.SigAlgNameTv);
		mSigAlgOidTv = this.findViewById(R.id.SigAlgOidTv);
		mSerialNumberTv = this.findViewById(R.id.SerialNumberTv);
		mDerCodeTv = this.findViewById(R.id.DerCodeTv);


		mMd5LinearLayout = this.findViewById(R.id.Md5LinearLayout);
		mSha1LinearLayout = this.findViewById(R.id.Sha1LinearLayout);
		mSha256LinearLayout = this.findViewById(R.id.Sha256LinearLayout);
		mEffectiveLinearLayout = this.findViewById(R.id.EffectiveLinearLayout);
		mPrincipalLinearLayout = this.findViewById(R.id.PrincipalLinearLayout);
		mVersionLinearLayout = this.findViewById(R.id.VersionLinearLayout);
		mSigAlgNameLinearLayout = this.findViewById(R.id.SigAlgNameLinearLayout);
		mSigAlgOidLinearLayout = this.findViewById(R.id.SigAlgOidLinearLayout);
		mSerialNumberLinearLayout = this.findViewById(R.id.SerialNumberLinearLayout);
		mDerCodeLinearLayout = this.findViewById(R.id.DerCodeLinearLayout);
	}

	private void initValues() {
		mPackageManager = mContext.getPackageManager();
		try {
			// 获取对应的PackageInfo(原始的PackageInfo 获取 signatures 等于null,需要这样获取)
			PackageInfo pInfo = mPackageManager.getPackageInfo(mPackageName, PackageManager.GET_SIGNATURES);
			String name = mPackageManager.getApplicationLabel(pInfo.applicationInfo).toString();
			Drawable logo = mPackageManager.getApplicationIcon(pInfo.applicationInfo);
			mLogoIv.setImageDrawable(logo);
			mNameTv.setText(name);
			mPackageNameTv.setText(mPackageName);
			mIsSystemSignatureTv.setText(String.valueOf(isSystemSign(mPackageManager, mPackageName)));
			mMd5Tv.setText(SignaturesMsg.signatureMD5(pInfo.signatures));
			mSha1Tv.setText(SignaturesMsg.signatureSHA1(pInfo.signatures));
			mSha256Tv.setText(SignaturesMsg.signatureSHA256(pInfo.signatures));
			// 获取证书对象
			X509Certificate cert = SignaturesMsg.getX509Certificate(pInfo.signatures);
			// 设置有效期
            String sbEffective = mDateFormat.format(cert.getNotBefore()) +
                    "\t至\t" +
                    mDateFormat.format(cert.getNotAfter()) +
                    "\n\n" +
                    cert.getNotBefore() +
                    "\t至\t" +
                    cert.getNotAfter();
			mEffectiveTv.setText(sbEffective);
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
			mIsEffectiveTv.setText(isEffective?"已过期":"未过期");
			// 证书发布方
			String principal = cert.getIssuerX500Principal().toString();
			mPrincipalTv.setText(principal);
			// 证书版本号
			mVersionTv.setText(cert.getVersion()+"");
			// 证书算法名称
			mSigAlgNameTv.setText(cert.getSigAlgName());
			// 证书算法OID
			mSigAlgOidTv.setText(cert.getSigAlgOID());
			// 证书机器码
			mSerialNumberTv.setText(cert.getSerialNumber().toString());
			// 证书 DER编码
			mDerCodeTv.setText(SignaturesMsg.toHexString(cert.getTBSCertificate()));
			
			
			// 获取证书发行者   可根据证书发行者来判断该应用是否被二次打包（被破解的应用重新打包后，签名与原包一定不同，据此可以判断出该应用是否被人做过改动）
			// String[] certMsg = new String[2];
			// certMsg[0] = cert.getIssuerDN().toString();
			// certMsg[1] = cert.getSubjectDN().toString();
		} catch (Exception e) {
			e.printStackTrace();
			showToast("获取异常：" + e);
		}
	}

	/**
	 * 初始化事件
	 */
	private void initListeners() {
		mMd5LinearLayout.setOnClickListener(this);
		mSha1LinearLayout.setOnClickListener(this);
		mSha256LinearLayout.setOnClickListener(this);
		mEffectiveLinearLayout.setOnClickListener(this);
		mPrincipalLinearLayout.setOnClickListener(this);
		mVersionLinearLayout.setOnClickListener(this);
		mSigAlgNameLinearLayout.setOnClickListener(this);
		mSigAlgOidLinearLayout.setOnClickListener(this);
		mSerialNumberLinearLayout.setOnClickListener(this);
		mDerCodeLinearLayout.setOnClickListener(this);
	}

	private static boolean isSystemSign(PackageManager pm, String packageName) {
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
			if (pm.checkSignatures(applicationInfo.uid, 1000) == PackageManager.SIGNATURE_MATCH) {
				return true;
			}
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.Md5LinearLayout) {
			setClipboardText(mMd5Tv);
		} else if (id == R.id.Sha1LinearLayout) {
			setClipboardText(mSha1Tv);
		} else if (id == R.id.Sha256LinearLayout) {
			setClipboardText(mSha256Tv);
		} else if (id == R.id.EffectiveLinearLayout) {
			setClipboardText(mEffectiveTv);
		} else if (id == R.id.PrincipalLinearLayout) {
			setClipboardText(mPrincipalTv);
		} else if (id == R.id.VersionLinearLayout) {
			setClipboardText(mVersionTv);
		} else if (id == R.id.SigAlgNameLinearLayout) {
			setClipboardText(mSigAlgNameTv);
		} else if (id == R.id.SigAlgOidLinearLayout) {
			setClipboardText(mSigAlgOidTv);
		} else if (id == R.id.SerialNumberLinearLayout) {
			setClipboardText(mSerialNumberTv);
		} else if (id == R.id.DerCodeLinearLayout) {
			setClipboardText(mDerCodeTv);
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

	private Toast mToast;
	private void showToast(String hint){
		if (mToast != null) {
			mToast.setText(hint);
		} else {
			mToast = Toast.makeText(mContext, hint, Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
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
