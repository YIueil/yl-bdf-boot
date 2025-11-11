package cc.yiueil.service.impl;

import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.dto.DynamicQueryDto;
import cc.yiueil.exception.BusinessException;
import cc.yiueil.query.ConfigResolver;
import cc.yiueil.query.DynamicQueryNode;
import cc.yiueil.query.DynamicQueryPool;
import cc.yiueil.query.instance.DynamicQuery;
import cc.yiueil.query.instance.DynamicQueryInst;
import cc.yiueil.service.SearchService;
import cc.yiueil.util.StringUtils;
import cc.yiueil.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.URL;
import java.util.Map;

/**
 * SearchServiceImpl 动态查询服务实现
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:31
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    @Qualifier("jpaBaseDao")
    JpaBaseDao baseDao;

    @Autowired
    ConfigResolver configResolver;

    @Autowired
    DynamicQueryPool dynamicQueryPool;

    @Override
    public DynamicQueryInst buildQueryInst(DynamicQueryDto dynamicQueryDto, Map<String, Object> parameters) {
        URL url = SearchServiceImpl.class.getClassLoader().getResource(dynamicQueryDto.getFullPath());
        if (url == null) {
            throw new RuntimeException("没有找到配置文件:" + dynamicQueryDto.getFullPath());
        }
        File file = new File(url.getFile());
        DynamicQueryNode dynamicQueryNode = dynamicQueryPool.findDynamicNode(dynamicQueryDto).orElseThrow(() -> new BusinessException("没有找到配置节点"));
        // 文件已更新, 需要重新加载节点
        if (file.lastModified() != dynamicQueryNode.getLastModified()) {
            dynamicQueryNode = configResolver.buildDynamicQueryNode(file);
        }
        DynamicQuery dynamicQuery = dynamicQueryNode.getDynamicQueryMap().get(dynamicQueryDto.getConfigId());
        return configResolver.constructInst(dynamicQuery, parameters);
    }

    /**
     * 动态查询服务
     * @param dynamicQueryDto 动态查询参数
     * @param parameters 过滤条件
     * @param pageIndex 当前页码
     * @param pageSize 每页数量
     * @return PageVo
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public PageVo searchPage(DynamicQueryDto dynamicQueryDto, Map<String, Object> parameters, Integer pageIndex, Integer pageSize) {
        PageVo pageVo = new PageVo();
        pageVo.setPageIndex(pageIndex);
        pageVo.setPageSize(pageSize);
        if (StringUtils.isEmpty(dynamicQueryDto.getConfigPath())) {
            dynamicQueryDto.setConfigPath("dynamicsql");
        }
        if (StringUtils.isEmpty(dynamicQueryDto.getConfigFile())) {
            dynamicQueryDto.setConfigFile("database.xml");
        }
        // todo 判断文件是否已经变更过
        URL url = SearchServiceImpl.class.getClassLoader().getResource(dynamicQueryDto.getFullPath());
        if (url == null) {
            throw new RuntimeException("没有找到配置文件:" + dynamicQueryDto.getFullPath());
        }
        String filepath = url.getFile();
        File file = new File(filepath);
        DynamicQueryNode dynamicQueryNode = dynamicQueryPool.findDynamicNode(dynamicQueryDto).orElseThrow(() -> new BusinessException("没有找到配置节点"));
        // 文件已更新, 需要重新加载节点
        if (file.lastModified() != dynamicQueryNode.getLastModified()) {
            dynamicQueryNode = configResolver.buildDynamicQueryNode(file);
        }
        DynamicQuery dynamicQuery = dynamicQueryNode.getDynamicQueryMap().get(dynamicQueryDto.getConfigId());
        if (dynamicQuery == null) {
            throw new BusinessException("没有找到对应配置服务: " + dynamicQueryDto.getConfigId());
        }
        // 仅construct时使用外部parameters
        DynamicQueryInst dynamicQueryInst = configResolver.constructInst(dynamicQuery, parameters);
        pageVo.setList(baseDao.sqlAsMap(dynamicQueryInst.getSql(), dynamicQueryInst.getParameters(), pageIndex, pageSize));
        pageVo.setItemCounts(baseDao.countSize(dynamicQueryInst.getCountSql(), dynamicQueryInst.getParameters()));
        pageVo.setPageTotal((pageVo.getItemCounts() / pageSize) + 1);
        return pageVo;
    }
}
