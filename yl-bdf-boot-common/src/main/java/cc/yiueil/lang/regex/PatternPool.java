package cc.yiueil.lang.regex;

import java.util.regex.Pattern;

/**
 * PatternPool 正则资源池
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:07
 * @version 1.0
 */
public class PatternPool {
    public static final Pattern PHONE = Pattern.compile(RegexPool.PHONE);
    public static final Pattern EMAIL = Pattern.compile(RegexPool.EMAIL);
    public static final Pattern ID_CARD = Pattern.compile(RegexPool.ID_CARD);
    public static final Pattern CONTAINS_LETTERS = Pattern.compile(RegexPool.CONTAINS_LETTERS);
    public static final Pattern CONTAINS_NUMBER = Pattern.compile(RegexPool.CONTAINS_NUMBER);
    public static final Pattern CONTAINS_SYMBOL = Pattern.compile(RegexPool.CONTAINS_SYMBOL);
}
