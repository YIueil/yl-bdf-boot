package cc.yiueil.query.instance;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * DynamicQueryInst 动态查询实例对象 通过DynamicQuery模板和filter参数构建
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:28
 * @version 1.0
 */
@Getter
@Setter
public class DynamicQueryInst extends DynamicQuery {
    private DynamicQueryInst() {}

    public static DynamicQueryInst of(DynamicQuery dynamicQuery) {
        DynamicQueryInst dynamicQueryInst = new DynamicQueryInst();
        dynamicQueryInst.setConfigId(dynamicQuery.getConfigId());
        dynamicQueryInst.setConfigPath(dynamicQuery.getConfigPath());
        dynamicQueryInst.setConfigFile(dynamicQuery.getConfigFile());
        dynamicQueryInst.setMixSql(dynamicQuery.getMixSql());
        dynamicQueryInst.setEndSql(dynamicQuery.getEndSql());
        dynamicQueryInst.setFilters(dynamicQuery.getFilters());
        dynamicQueryInst.setParams(dynamicQuery.getParams());
        return dynamicQueryInst;
    }

    private String sql;

    private String countSql;

    private Map<String, Object> parameters;
}
