package cc.yiueil.util;

import java.io.File;
import java.util.Locale;

/**
 * FileUtils文件处理工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/1/12 11:55
 * @version 1.0
 */
public class FileUtils {
    /**
     * 判断文件是否为制定扩展名的文件
     * @param fileName 文件名称
     * @param extension 文件扩展类型
     * @return 判断结果
     */
    public static boolean checkExtra(String fileName, String extension) {
        int i = fileName.lastIndexOf('.');
        if (i > 0 && StringUtils.isNotEmpty(extension)) {
            return extension.toLowerCase(Locale.ROOT).equals(fileName.substring(i + 1).toLowerCase(Locale.ROOT));
        }
        return false;
    }

    /**
     * 判断文件是否为制定扩展名的文件
     * @param file 目标文件
     * @param extension 文件扩展类型
     * @return 判断结果
     */
    public static boolean checkExtra(File file, String extension) {
        return checkExtra(file.getName(), extension);
    }
}
