package cc.yiueil.util;

import cc.yiueil.general.StringPool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FakeUtils 虚假数据生成工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:18
 * @version 1.0
 */
public class FakeUtils {
    /**
     * 身份证校验码生成
     *
     * @param sb 除验证码部分
     * @return 生成的身份证校验码
     */
    private static char calcTrailingNumber(StringBuilder sb) {
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(String.valueOf(sb.charAt(i)));
        }
        for (int i = 0; i < n.length; i++) {
            result += StringPool.ID_CARD_CALC_C[i] * n[i];
        }
        return StringPool.ID_CARD_CALC_R[result % 11];
    }

    /**
     * 随机获取一段长度的字符串
     *
     * @param length 字符串长度
     * @return 随机生成的字符串
     */
    public static String getString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(StringPool.BASE[RandomUtils.randomInt(0, StringPool.BASE.length)]);
        }
        return sb.toString();
    }

    /**
     * 生成手机号
     *
     * @return 随机的一个手机号
     */
    public static String getPhone() {
        int index = RandomUtils.randomInt(0, StringPool.FIRST_TEL.length);
        String first = StringPool.FIRST_TEL[index];
        String second = String.valueOf(RandomUtils.randomInt(1, 888) + 10000).substring(1);
        String third = String.valueOf(RandomUtils.randomInt(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    /**
     * 获取到一个随机的邮箱地址
     *
     * @return 随机生成的邮箱地址
     */
    public static String getEmail() {
        String first = getString(RandomUtils.randomInt(3, 6));
        String end = StringPool.LAST_MAIL[RandomUtils.randomInt(0, StringPool.LAST_MAIL.length)];
        return first + "@" + end;
    }

    /**
     * 获取到一个随机的身份证
     * @return 随机生成的身份证
     */
    public static String getIdCard() {
        return getIdCard(RandomUtils.randomBoolean());
    }

    /**
     * 指定性别获取到一个随机的身份证
     * @param male 性别
     * @return 随机生成的身份证
     */
    public static String getIdCard(boolean male) {
        // 100年内
        long begin = System.currentTimeMillis() - 3153600000000L;
        // 1年内
        long end = System.currentTimeMillis() - 31536000000L;
        long rtn = begin + (long) (Math.random() * (end - begin));
        Date date = new Date(rtn);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String birth = simpleDateFormat.format(date);
        StringBuilder sb = new StringBuilder();

        int value = RandomUtils.randomInt(0, StringPool.FIRST_ID_CARD.length);
        sb.append(StringPool.FIRST_ID_CARD[value]);
        sb.append(birth);
        value = RandomUtils.randomInt(0, 999) + 1;
        if (male && value % 2 == 0) {
            value++;
        }
        if (!male && value % 2 == 1) {
            value++;
        }
        if (value >= 100) {
            sb.append(value);
        } else if (value >= 10) {
            sb.append('0').append(value);
        } else {
            sb.append("00").append(value);
        }
        sb.append(calcTrailingNumber(sb));
        return sb.toString();
    }

    /**
     * 获得一个中文人名
     *
     * @return 随机一个中文人名
     */
    public static String getChineseName() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringPool.FIRST_NAME[RandomUtils.randomInt(0, StringPool.FIRST_NAME.length)]);
        sb.append(StringPool.LAST_NAME[RandomUtils.randomInt(0, StringPool.LAST_NAME.length)]);
        if (Math.random() % 2 > 0.5) {
            sb.append(StringPool.LAST_NAME[RandomUtils.randomInt(0, StringPool.LAST_NAME.length)]);
        }
        return sb.toString();
    }

    /**
     * 获得一个三国人物名字
     *
     * @return 随机一个三国人名
     */
    public static String getSgChineseName() {
        return StringPool.SG_NAME[RandomUtils.randomInt(0, StringPool.SG_NAME.length)];
    }

}
