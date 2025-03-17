package cc.yiueil.util;

import java.util.HashMap;

/**
 * PasswordUtils 密码工具类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/3/18 16:48
 */
public class PasswordUtils {
    private final static HashMap<Integer, String> PASSWORD_STRENGTH_MAP;

    /**
     * 密码强度必要长度
     */
    private final static int STRENGTH_LENGTH = 8;

    static {
        PASSWORD_STRENGTH_MAP = new HashMap<>();
        PASSWORD_STRENGTH_MAP.put(0, "无效密码");
        PASSWORD_STRENGTH_MAP.put(1, "弱密码");
        PASSWORD_STRENGTH_MAP.put(2, "中密码");
        PASSWORD_STRENGTH_MAP.put(3, "强密码");
        PASSWORD_STRENGTH_MAP.put(4, "极强密码");
    }

    /**
     * 检查密码健壮性
     *
     * @param password 密码
     * @return 密码健壮性
     */
    public static String checkPasswordStrength(String password) {
        int strengthLevel = 0;
        if (CheckUtils.containsLetters(password)) {
            strengthLevel++;
        }
        if (CheckUtils.containsNumber(password)) {
            strengthLevel++;
        }
        if (CheckUtils.containsSymbol(password)) {
            strengthLevel++;
        }
        if (password.length() >= STRENGTH_LENGTH) {
            strengthLevel++;
        }
        return PASSWORD_STRENGTH_MAP.get(strengthLevel);
    }

    /**
     * 估算大致时间
     *
     * @param password 密码
     * @return 大致破解时间
     */
    public static String estimateCrackTime(String password) {
        int passwordLength = password.length();
        // 穷举法破解时间（小时）
        double crackTime = Math.pow(96, passwordLength) / 2 / 3600;
        String[] units = {"秒", "分钟", "小时", "天", "月", "年", "世纪"};
        double[] divisors = {60, 60, 24, 30.4, 12, 100, Double.MAX_VALUE};
        for (int i = 0; i < units.length; i++) {
            if (crackTime < divisors[i]) {
                return String.format("破解时间: %d %s", (int) (crackTime), units[i]);
            } else if (i < divisors.length - 1) {
                crackTime /= divisors[i];
            }
        }
        return "破解时间: 无穷";
    }
}
