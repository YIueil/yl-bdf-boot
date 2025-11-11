package cc.yiueil.query.instance;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * DynamicQuery 动态查询
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:28
 * @version 1.0
 */
@Getter
@Setter
public class DynamicQuery {
    String configId;

    String configPath;

    String configFile;

    private String mixSql;

    private String endSql;

    private Map<String, Param> params;

    private Map<String, Filter> filters;
}
