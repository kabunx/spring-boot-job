package com.kabunx.core.util;

public class SensitiveUtils {
    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String fullName) {
        if (isBlank(fullName)) {
            return "";
        }
        final String family = fullName.substring(0, 1);
        return family + "**";
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     */
    public static String chineseName(final String familyName, final String givenName) {
        if (isBlank(familyName) || isBlank(givenName)) {
            return "";
        }
        return chineseName(familyName + givenName);
    }

    /**
     * 【身份证号】显示最后四位，其他隐藏。共计18位或者15位。<例子：420**********5762>
     */
    public static String idCardNum(final String id) {
        if (isBlank(id)) {
            return "";
        }
        final String left = id.substring(0, 3);
        final String right = id.substring(id.length() - 4);
        return left + "**********" + right;
    }

    /**
     * 【固定电话】后四位，其他隐藏<例子：****1234>
     */
    public static String fixedPhone(final String num) {
        if (isBlank(num)) {
            return "";
        }
        return "****" + num.substring(num.length() - 4);
    }

    /**
     * 【手机号码】前三位，后四位，其他隐藏<例子:138****1234>
     */
    public static String mobilePhone(final String num) {
        if (isBlank(num)) {
            return "";
        }
        String left = num.substring(0, 3);
        String right = num.substring(num.length() - 4);
        return left + "****" + right;

    }

    /**
     * 【地址】只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
     *
     * @param sensitiveSize 敏感信息长度
     */
    public static String address(final String address, final int sensitiveSize) {
        if (isBlank(address)) {
            return "";
        }
        String left = address.substring(0, sensitiveSize);
        return left + "****";
    }

    /**
     * 【电子邮箱】邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     */
    public static String email(final String email) {
        if (isBlank(email)) {
            return "";
        }
        final int index = email.indexOf("@");
        if (index <= 1 || index == email.length() - 1) {
            return email;
        } else {
            String left = email.substring(0, 1);
            String right = email.substring(email.length() - index);
            return left + "****" + right;
        }
    }

    /**
     * 【银行卡号】前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600******1234>
     */
    public static String bankCard(final String cardNum) {
        if (isBlank(cardNum)) {
            return "";
        }
        String left = cardNum.substring(0, 6);
        String right = cardNum.substring(cardNum.length() - 4);
        return left + "******" + right;
    }

    private static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}