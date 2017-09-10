package com.sp.shangpin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ShangPin
 * Created by 蔡雨峰 on 2017/9/8.
 */

public class ReExpressUtil {
    public static final String PHONE = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    public static boolean isMatcher(String express, String text) {
        Pattern pattern = Pattern.compile(express);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
