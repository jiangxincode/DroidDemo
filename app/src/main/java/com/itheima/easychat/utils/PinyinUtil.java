package com.itheima.easychat.utils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class PinyinUtil {
    public static String getPinyin(String str) {
        try {
            return PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return null;
    }
}
