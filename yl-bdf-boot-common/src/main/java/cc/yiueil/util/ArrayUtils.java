package cc.yiueil.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * ArrayUtils 数组工具类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:13
 * @version 1.0
 */
public class ArrayUtils {
    //region isEmpty
    /**
     * 数组是否为空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 如果给定数组为空，返回默认数组
     *
     * @param <T>          数组元素类型
     * @param array        数组
     * @param defaultArray 默认数组
     * @return 非空（empty）的原数组或默认数组
     * @since 4.6.9
     */
    public static <T> T[] defaultIfEmpty(T[] array, T[] defaultArray) {
        return isEmpty(array) ? defaultArray : array;
    }

    /**
     * 数组是否为空<br>
     * 此方法会匹配单一对象，如果此对象为{@code null}则返回true<br>
     * 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回false<br>
     * 如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(Object array) {
        if (array != null) {
            if (isArray(array)) {
                return 0 == Array.getLength(array);
            }
            return false;
        }
        return true;
    }
    //endregion

    //region isNotEmpty
    /**
     * 数组是否为空
     *
     * @param <T>   数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }

    /**
     * 数组是否为空<br>
     * 此方法会匹配单一对象，如果此对象为{@code null}则返回true<br>
     * 如果此对象为非数组，理解为此对象为数组的第一个元素，则返回false<br>
     * 如果此对象为数组对象，数组长度大于0情况下返回false，否则返回true
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isNotEmpty(Object array) {
        return !isEmpty(array);
    }
    //endregion

    /**
     * 反转char数组会变化原数组
     * @param charArray char数组
     * @return 反序char数组结果
     */
    public static char[] reverse(char[] charArray) {
        if (charArray != null) {
            for (int i = 0; i < charArray.length / 2; i++) {
                swap(charArray, i, charArray.length - (i + 1));
            }
        }
        return charArray;
    }

    /**
     * 交换数组元素的位置
     * @param charArray 数组
     * @param leftIndex 位置1
     * @param rightIndex 位置2
     */
    private static void swap(char[] charArray, int leftIndex, int rightIndex) {
        if (charArray == null) {
            return;
        }
        char temp = charArray[leftIndex];
        charArray[leftIndex] = charArray[rightIndex];
        charArray[rightIndex] = temp;
    }

    private static boolean isArray(Object object) {
        return object != null && object.getClass().isArray();
    }

    /**
     * 将数组转换为List
     * @param arr 数组
     * @return List集合
     */
    public static <T> List<T> arrayToList(T[] arr) {
        return Arrays.asList(arr);
    }
}
