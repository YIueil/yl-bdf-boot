package cc.yiueil.convert.impl.base;

import cc.yiueil.convert.Converter;
import cc.yiueil.util.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * DateConverter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:47
 * @version 1.0
 * @see DateUtils
 */
public class DateConverter implements Converter<Date> {
    @Override
    public Date convert(Object o, Date defaultValue) {
        if (o == null) {
            return defaultValue;
        }
        // 时间戳的转换
        if (o instanceof Long) {
            return new Date((Long) o);
        }
        // 字符串的转换规则
        if (o instanceof CharSequence) {
            return DateUtils.str2Date(o.toString());
        }
        if (o instanceof LocalDate) {
            return DateUtils.localDate2Date((LocalDate) o);
        }
        if (o instanceof LocalDateTime) {
            return DateUtils.localDateTime2Date((LocalDateTime) o);
        }
        return defaultValue;
    }
}
