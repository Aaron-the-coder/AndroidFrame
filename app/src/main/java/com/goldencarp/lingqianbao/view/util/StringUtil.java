package com.goldencarp.lingqianbao.view.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by sks on 2017/12/2.
 *
 */

public class StringUtil {

    private StringUtil(){}
    static String getSimpleFormatDate(long timeMills){
        Date date = new Date(timeMills);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 验证密码是否为字母数字或特殊符号
     */
    public static boolean isValidPassword1(String pwd){
        return validation("^[\\x21-\\x7e]*$", pwd);
    }


    /**
     * 正则校验
     *
     * @param pattern 正则表达式
     * @param str
     *            需要校验的字符串
     * @return 验证通过返回true
     */
    private static boolean validation(String pattern, String str) {
        return str != null && Pattern.compile(pattern).matcher(str).matches();
    }

    //sha1加密
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
