package cc.yiueil.util;

import cc.yiueil.lib.TextSimilarity;

import java.util.Map;

/**
 * StringUtils 字符串工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:38
 * @version 1.0
 */
public class StringUtils {
    /**
     * 严格判空：判断字符串是否为空,换行符、空格会被判定为空。
     * @param str 要判定的对象
     * @return 判定结果
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (str.length() != 0) {
            for (int i = 0; i < str.length(); i++) {
                // 只要其一字符不为空或空白占位字符即为真
                if (!CharUtils.isBlankChar(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 宽松判空：判断字符串是否为空,换行符、空格会被判定为非空。
     * @param str 要判定的对象
     * @return 判定结果
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 反转字符串
     * @param str 待反转字符串
     * @return 反转结果
     */
    public static String reverse(String str) {
        return new String(ArrayUtils.reverse(str.toCharArray()));
    }

    /**
     * 将已有字符串填充为规定长度，如果已有字符串超过这个长度则返回这个字符串
     *
     * @param str        被填充的字符串
     * @param filledChar 填充的字符
     * @param len        填充目标长度
     * @param isPre      填充的位置
     * @return 填充后的字符串
     */
    public static String fill(String str, char filledChar, int len, boolean isPre) {
        if (str.length() >= len) {
            return str;
        }
        final int size = len - str.length();
        return isPre ? str.concat(repeat(filledChar, size)) : repeat(filledChar, size).concat(str);

    }

    /**
     * 计算两个字符串的相似度
     *
     * @param strA 字符串1
     * @param strB 字符串2
     * @return 计算出的相似度
     */
    public static double similar(String strA, String strB) {
        return TextSimilarity.similar(strA, strB);
    }

    /**
     *
     * @param template 模板
     * @param map 映射集
     * @param leftStr 匹配符的左侧 使用的是正则,注意添加转移符,默认值"\\$\\{"
     * @param rightStr 匹配符的右侧 使用的是正则,注意添加转义符,默认值"}"
     * @param ignoreNull 是否忽略值为空的节点
     * @return 替换后的结果
     */
    public static String format(CharSequence template, Map<?, ?> map, CharSequence leftStr, CharSequence rightStr, boolean ignoreNull) {
        return null;
    }

    /**
     * 生成某字符填充的一定长度的字符串
     * @param c 字符
     * @param count 长度
     * @return 填充结果
     */
    public static String repeat(char c, int count) {
        if (count <= 0) {
            return "";
        }

        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }
}
