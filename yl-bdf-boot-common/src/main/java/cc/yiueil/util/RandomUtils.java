package cc.yiueil.util;

import java.util.Random;

/**
 * RandomUtils 随机工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:37
 * @version 1.0
 */
public class RandomUtils {
    private static final Random RANDOM = new Random();

    /**
     * 随机生成boolean值
     * @return 随机boolean值
     */
    public static boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }

    /**
     * 随机生成一个范围类的int数
     * @param min 最小值(闭区间: 会取到该值)
     * @param max 最大值(开区间: 不会取到该值)
     * @return 随机int值
     */
    public static int randomInt(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 生成指定长度的纯数字验证码
     * @param length 指定长度
     * @return 随机验证码
     */
    public static String randomNumberVerifyCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
