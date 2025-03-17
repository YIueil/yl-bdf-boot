package cc.yiueil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * DateUtils 旧日期API工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:16
 * @version 1.0
 */
public class DateUtils {
    private static final List<SimpleDateFormat> TEXT_FORMAT_LIST;

    static {
        TEXT_FORMAT_LIST = new ArrayList<>();
        // ISO 8601格式
        TEXT_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        // 12小时制 含毫秒
        TEXT_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:sss"));
        // 24小时制 不含毫秒
        TEXT_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 12小时制 不含毫秒
        TEXT_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        // 不含时分秒
        TEXT_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd"));
        //todo 适配更多标准日期格式
    }

    //region 当前日期相关
    /**
     * 获取当前年份
     * @return 年
     */
    public static int getYear() {
        return getYear(new Date());
    }

    /**
     * 获取当前月份
     * @return 月
     */
    public static int getMouth() {
        return getMouth(new Date());
    }

    /**
     * 获取当前是本月的第几天
     * @return 日
     */
    public static int getDayOfMouth() {
        return getDayOfMouth(new Date());
    }

    /**
     * 获取当前的学年
     * <p>如果在9、10、11、12、1月，为此学年第 2 学期，
     * 其中在9、10、11、12月为 year 学年，1月为 year-1 学年。
     * 如果在2、3、4、5、6、7、8月，为此学年第 2 学期，
     * year-1学年。</p>
     * @return 当前学年
     * <p>e.g. : 2021-2022-2</p>
     */
    public static String getCurrentTerm() {
        return getCurrentTerm(new Date());
    }
    //endregion

    //region 指定日期相关
    /**
     * 获取当前年份
     * @param date 当前日期
     * @return 年
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     * @param date 当前日期
     * @return 月
     */
    public static int getMouth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前是本月的第几天
     * @param date 当前日期
     * @return 日
     */
    public static int getDayOfMouth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前的学年
     * <p>如果在9、10、11、12、1月，为此学年第 2 学期，
     * 其中在9、10、11、12月为 year 学年，1月为 year-1 学年。
     * 如果在2、3、4、5、6、7、8月，为此学年第 2 学期，
     * year-1学年。</p>
     * @param date 当前日期
     * @return 当前学年
     * <p>e.g. : 2021-2022-2</p>
     */
    public static String getCurrentTerm(Date date) {
        int topHalfMouth = 2;
        int bottomHalfMouth = 9;
        int year = getYear(date);
        int month = getMouth(date);
        int term = 1;
        if (month < bottomHalfMouth) {
            year = year - 1;
        }
        if (month > topHalfMouth && month < bottomHalfMouth) {
            term = 2;
        }
        return year + "-" + (year + 1) + "-" + term;
    }
    //endregion

    //region 新老API的转换
    /**
     * LocalDate转Date
     * @param localDate 日期
     * @return 转换结果
     */
    public static Date localDate2Date(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atTime(0, 0);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * LocalDateTime转Date
     * @param localDateTime 日期时间
     * @return 转换结果
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Date转LocalDate, 忽略LocalTime
     * @param date 日期
     * @return 日期
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId).toLocalDate();
    }

    /**
     * Date转LocalTime, 忽略LocalDate
     * @param date 日期
     * @return 时间
     */
    public static LocalTime date2LocalTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId).toLocalTime();
    }

    /**
     * Date转LocalDateTime
     * @param date 日期
     * @return 日期时间
     */
    public static LocalDateTime date2localDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 通过标准的日期字符串获取Date
     * @param dateStr 标准日期字符串
     * <pre>
     *       yyyy-MM-dd'T'HH:mm:ss
     *       yyyy-MM-dd hh:mm:sss
     *       yyyy-MM-dd HH:mm:ss
     *       yyyy-MM-dd hh:mm:ss
     *       yyyy-MM-dd
     * </pre>
     * @return 转换结果
     */
    public static Date str2Date(String dateStr) {
        for (SimpleDateFormat simpleDateFormat : TEXT_FORMAT_LIST) {
            try {
                return simpleDateFormat.parse(dateStr);
            } catch (ParseException ignored) {}
        }
        throw new RuntimeException("G! 字符串转换失败");
    }
    //endregion

    /**
     * 通过时间戳，地区，时区等参数，展示用户容易接受的时间字符串
     * <p>
     *     参考： 廖雪峰java教程
     *     @link https://www.liaoxuefeng.com/wiki/1252599548343744/1303978948165666
     * </p>
     * <p>e.g. :
     *         LocalDateTime ldt = LocalDateTime.of(2019, 9, 15, 15, 16, 17);
     *         ZonedDateTime zbj = ldt.atZone(ZoneId.systemDefault());
     *         ZonedDateTime zny = ldt.atZone(ZoneId.of("America/New_York"));
     *         System.out.println(zbj);
     *         System.out.println(zny);
     *
     * </p>
     * <p>
     *     2019-09-15T15:16:17+08:00[Asia/Shanghai]
     *     2019-09-15T15:16:17-04:00[America/New_York]
     * </p>
     * @param epochMilli 时间戳
     * @param lo 地区
     * @param zoneId 时区
     * @return 各地区容易接受的日期字符串
     */
    static String timestampToString(long epochMilli, Locale lo, String zoneId) {
        Instant ins = Instant.ofEpochMilli(epochMilli);
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);
        return f.withLocale(lo).format(ZonedDateTime.ofInstant(ins, ZoneId.of(zoneId)));
    }
}
