package cc.yiueil.convert;

/**
 * CollectionConverter 集合类型转换器
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:49
 * @version 1.0
 */
public abstract class AbstractCollectionConverter<T> implements Converter<T>{
    @Override
    public T convert(Object obj, T defaultValue) {
        return convertInternal(obj);
    }

    /**
     * 内部对象转换
     * @param obj 内部对象
     * @return 转换结果
     */
    protected abstract T convertInternal(Object obj);
}
