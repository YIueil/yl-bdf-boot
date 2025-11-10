package cc.yiueil.data;

import cc.yiueil.util.CollectionUtils;
import cc.yiueil.util.MapUtils;

import javax.persistence.Parameter;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SqlDao 原生sql执行 查询相关内容
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/31 22:34
 */
public interface SqlDao {
    /**
     * 执行sql查询封装Entity对象
     *
     * @param sql   sql
     * @param clazz clazz
     * @param <T>   实体类型
     * @return TList
     */
    <T> List<T> sqlAsEntity(String sql, Class<T> clazz);

    /**
     * 执行sql查询封装Entity对象
     *
     * @param sql   sql
     * @param clazz clazz
     * @param parameters 查询参数
     * @param <T>   实体类型
     * @return TList
     */
    <T> List<T> sqlAsEntity(String sql, Map<String, Object> parameters, Class<T> clazz);

    /**
     * 执行sql查询返回map
     *
     * @param sql sql
     * @return map
     */
    List<Map<String, Object>> sqlAsMap(String sql);

    /**
     * 执行sql分页查询返回map
     *
     * @param sql       sql
     * @param pageIndex 页码
     * @param pageSize  单页数量
     * @return map
     */
    List<Map<String, Object>> sqlAsMap(String sql, int pageIndex, int pageSize);

    /**
     * 执行sql查询返回map
     *
     * @param sql        sql
     * @param parameters 执行参数
     * @return map
     */
    List<Map<String, Object>> sqlAsMap(String sql, Map<String, Object> parameters);

    /**
     * 执行sql分页查询返回map
     *
     * @param sql        sql
     * @param parameters 参数
     * @param pageIndex  页码
     * @param pageSize   单页数量
     * @return map
     */
    List<Map<String, Object>> sqlAsMap(String sql, Map<String, Object> parameters, int pageIndex, int pageSize);

    /**
     * 执行sql
     *
     * @param sql        sql
     * @param parameters 参数
     * @return 更新数量
     */
    int executeUpdate(String sql, Map<String, Object> parameters);

    /**
     * 获取sql执行结果数量
     *
     * @param sql        sql
     * @param parameters 参数
     * @return 结果集行数
     */
    int countSize(String sql, Map<String, Object> parameters);

    /**
     * 设置查询参数
     *
     * @param query query
     * @param args  参数集
     */
    default void setParameters(Query query, Map<String, Object> args) {
        // 获取到真实的参数列表
        List<String> parameters = query.getParameters().stream().map(Parameter::getName).collect(Collectors.toList());
        if (MapUtils.isNotEmpty(args) && CollectionUtils.isNotEmpty(parameters)) {
            Set<String> keySet = args.keySet();
            for (String key : keySet) {
                if (parameters.contains(key)) {
                    query.setParameter(key, args.get(key));
                }
            }
        }
    }
}
