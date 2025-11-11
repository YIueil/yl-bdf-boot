package cc.yiueil.query;

import cc.yiueil.dto.DynamicQueryDto;
import cc.yiueil.query.instance.DynamicQuery;
import cc.yiueil.util.ArrayUtils;
import cc.yiueil.util.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * DynamicQueryPool 动态查询资源池 资源池包含多个节点，一个文件一个节点
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:30
 * @version 1.0
 */
@Component
public class DynamicQueryPool implements InitializingBean {
    /**
     * 处理器
     */
    @Autowired
    private ConfigResolver configResolver;

    private static final String DEFAULT_CONFIG_PATH = "dynamicsql";

    /**
     * 动态查询目录
     */
    private String[] locations = {DEFAULT_CONFIG_PATH};

    private final Map<String, DynamicQueryNode> dynamicSqlMap = new HashMap<>();

    /**
     * 获取动态查询节点
     * @param dynamicQueryDTO 动态查询数据传输对象
     * @return 动态查询节点
     */
    public Optional<DynamicQueryNode> findDynamicNode(DynamicQueryDto dynamicQueryDTO) {
        DynamicQueryNode dynamicQueryNode = null;
        if (dynamicSqlMap.containsKey(dynamicQueryDTO.getFullPath())) {
            dynamicQueryNode = dynamicSqlMap.get(dynamicQueryDTO.getFullPath());
        }
        return Optional.ofNullable(dynamicQueryNode);
    }

    public String[] getLocations() {
        return locations;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (String location : locations) {
            URL dynamicsql = DynamicQuery.class.getClassLoader().getResource(location);
            if (dynamicsql == null) {
                return;
            }
            File dynamicsqlDirectory = new File(dynamicsql.getFile());
            if (!dynamicsqlDirectory.isDirectory()) {
                return;
            }
            File[] files = dynamicsqlDirectory.listFiles();
            if (ArrayUtils.isNotEmpty(files)) {
                for (File configFile : files) {
                    if (!FileUtils.checkExtra(configFile, "xml")) {
                        continue;
                    }
                    dynamicSqlMap.put(
                            DEFAULT_CONFIG_PATH + "/" + configFile.getName(),
                            configResolver.buildDynamicQueryNode(configFile)
                    );
                }
            }
        }
    }
}
