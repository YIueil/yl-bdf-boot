package cc.yiueil.util;

import cc.yiueil.lang.regex.PatternPool;

import java.util.regex.Pattern;

/**
 * CheckUtils 字符串验证工具类
 * @see PatternPool
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:14
 * @version 1.0
 */
public class CheckUtils {
    /**
     * 检查字符串是否为手机号
     * @param str 字符串
     * @return 检查结果
     */
    public static boolean isPhone(String str) {
        return validator(PatternPool.PHONE, str);
    }

    /**
     * 检查字符串是否为邮箱
     * @param str 字符串
     * @return 检查结果
     */
    public static boolean isEmail(String str) {
        return validator(PatternPool.EMAIL, str);
    }

    /**
     * 检查字符串是否为身份证
     * @param str 字符串
     * @return 检查结果
     */
    public static boolean isIdCard(String str) {
        return validator(PatternPool.ID_CARD, str);
    }

    /**
     * 检查字符串是否包含字母
     * @param str 字符串
     * @return 检查结果
     */
    public static boolean containsLetters(String str) {
        return validator(PatternPool.CONTAINS_LETTERS, str);
    }

    /**
     * 检查字符串是否包含数字
     * @param str 字符串
     * @return 检查结果
     */
    public static boolean containsNumber(String str) {
        return validator(PatternPool.CONTAINS_NUMBER, str);
    }

    /**
     * 检查字符串是否包含字符
     * @param str 字符串
     * @return 检查结果
     */
    public static boolean containsSymbol(String str) {
        return validator(PatternPool.CONTAINS_SYMBOL, str);
    }

    /**
     * 使用校验对象进行字符匹配
     * @param pattern pattern对象
     * @param str 字符串
     * @return 检查结果
     */
    private static boolean validator(Pattern pattern, String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return pattern.matcher(str).matches();
    }
}
