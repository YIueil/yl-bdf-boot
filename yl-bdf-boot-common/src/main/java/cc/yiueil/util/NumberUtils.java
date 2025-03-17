package cc.yiueil.util;

/**
 * NumberUtils 数字类型工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:21
 * @version 1.0
 */
public class NumberUtils {
    /**
     * 将字符串转换为整数，默认值为0
     * @param str 字符串
     * @return 整数
     */
    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    /**
     * 将字符串转换为整数，指定默认值
     * @param str 字符串
     * @param defaultValue 默认值
     * @return 整数
     */
    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串转换为长整型，默认值为0L
     * @param str 字符串
     * @return 长整型
     */
    public static long parseLong(String str) {
        return parseLong(str, 0L);
    }

    /**
     * 将字符串转换为长整型，指定默认值
     * @param str 字符串
     * @param defaultValue 默认值
     * @return 长整型
     */
    public static long parseLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串转换为浮点数，默认值为0.0f
     * @param str 字符串
     * @return 浮点数
     */
    public static float parseFloat(String str) {
        return parseFloat(str, 0.0f);
    }

    /**
     * 将字符串转换为浮点数，默认值为0.0f
     * @param str 字符串
     * @param defaultValue 默认值
     * @return 浮点数
     */
    public static float parseFloat(String str, float defaultValue) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 将字符串转换为双精度浮点数，默认值为0.0
     * @param str 字符串
     * @return 双精度浮点数
     */
    public static double parseDouble(String str) {
        return parseDouble(str, 0.0);
    }

    /**
     * 将字符串转换为双精度浮点数，指定默认值
     * @param str 字符串
     * @param defaultValue 默认值
     * @return 双精度浮点数
     */
    public static double parseDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 判断一个数是否为偶数
     * @param num int数
     * @return 是否为偶数
     */
    public static boolean isEven(int num) {
        return (num & 1) == 0;
    }

    /**
     * 判断一个数是否为奇数
     * @param num int数
     * @return 是否为奇数
     */
    public static boolean isOdd(int num) {
        return (num & 1) == 1;
    }

    /**
     * 获取两个数的最大公约数
     * @param a int
     * @param b int
     * @return 最大公约数
     */
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    /**
     * 获取两个整数的最小公倍数
     * @param a int
     * @param b int
     * @return 最小公约数
     */
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * 将一个十进制数字转换为二进制字符串
     * @param num int
     * @return 二进制字符串
     */
    public static String toBinaryString(int num) {
        return Integer.toBinaryString(num);
    }

    /**
     * 将一个十进制数字转换为八进制字符串
     * @param num int
     * @return 八进制字符串
     */
    public static String toOctalString(int num) {
        return Integer.toOctalString(num);
    }

    /**
     * 将一个十进制数字转换为十六进制字符串
     * @param num int
     * @return 十六进制字符串
     */
    public static String toHexString(int num) {
        return Integer.toHexString(num);
    }
}
