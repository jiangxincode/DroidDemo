package com.itheima62.smartbj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	
	/**
	 * @param path
	 *    �ļ���·��
	 * @return
	 *    �ļ���MD5ֵ
	 */
	public static String getFileMD5(String path){
		StringBuilder mess = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			//��ȡMD5������
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[10240];
			int len = fis.read(buffer);
			while (len != -1) {
				md.update(buffer, 0, len);
				//�����ȡ
				len = fis.read(buffer);
			}
			byte[] digest = md.digest();
			for (byte b : digest){
				//��ÿ���ֽ�ת��16������  
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {//�ֽڵĸ�4λΪ0
					hexString = "0" + hexString;
				}
				hexString = hexString.toUpperCase();
				mess.append(hexString);//��ÿ���ֽڶ�Ӧ��2λʮ���������ַ�ƴ��һ��
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess + "";
	}
	public static String md5(String str){
		StringBuilder mess = new StringBuilder();
		try {
			//��ȡMD5������
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = str.getBytes();
			byte[] digest = md.digest(bytes);
			
			for (byte b : digest){
				//��ÿ���ֽ�ת��16������  
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {//�ֽڵĸ�4λΪ0
					hexString = "0" + hexString;
				}
				mess.append(hexString);//��ÿ���ֽڶ�Ӧ��2λʮ���������ַ�ƴ��һ��
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mess + "";
	}
}
