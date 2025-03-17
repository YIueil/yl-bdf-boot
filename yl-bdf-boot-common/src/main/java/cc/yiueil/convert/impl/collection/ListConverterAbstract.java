package cc.yiueil.convert.impl.collection;

import cc.yiueil.convert.AbstractCollectionConverter;
import cc.yiueil.convert.ConverterHolder;
import cc.yiueil.lang.reflect.AbstractTypeReference;
import cc.yiueil.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ListConverter
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/30 22:49
 */
public class ListConverterAbstract<T> extends AbstractCollectionConverter<List<T>> {
    private final AbstractTypeReference<T> abstractTypeReference;

    public ListConverterAbstract(AbstractTypeReference<T> abstractTypeReference) {
        this.abstractTypeReference = abstractTypeReference;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<T> convertInternal(Object obj) {
        ArrayList<T> result = new ArrayList<>();
        if (obj instanceof Collection) {
            Collection<?> collection = (Collection<?>) obj;
            for (Object item : collection) {
                T resultItem = ConverterHolder.getConverter(abstractTypeReference).convert(item, null);
                if (resultItem != null) {
                    result.add(resultItem);
                }
            }
        } else if (obj instanceof CharSequence) {
            String string = obj.toString();
            List<String> strings = Arrays.asList(string.split(","));
            if (CollectionUtils.isNotEmpty(strings)) {
                return strings.stream()
                        .map(str -> ConverterHolder.getConverter(abstractTypeReference).convert(str, null))
                        .collect(Collectors.toList());
            }

        }
        return result;
    }
}
