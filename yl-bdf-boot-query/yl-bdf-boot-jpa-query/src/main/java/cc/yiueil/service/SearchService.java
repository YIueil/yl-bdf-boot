package cc.yiueil.service;

import cc.yiueil.dto.DynamicQueryDto;
import cc.yiueil.query.instance.DynamicQueryInst;
import cc.yiueil.vo.PageVo;
import org.dom4j.DocumentException;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * SearchService 查询服务
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2023/5/31 23:38
 */
public interface SearchService {
    /**
     * 构造查询实例
     *
     * @param dynamicQueryDto 动态查询DTO
     * @param parameters 参数集合
     * @return 查询实例
     */
    DynamicQueryInst buildQueryInst(DynamicQueryDto dynamicQueryDto, Map<String, Object> parameters);

    /**
     * 动态分页查询
     *
     * @param dynamicQueryDTO 动态查询参数
     * @param parameters      过滤条件
     * @param pageIndex       当前页码
     * @param pageSize        每页数量
     * @return 分页查询结果
     * @throws FileNotFoundException fileNotFoundException
     * @throws DocumentException     documentException
     */
    PageVo searchPage(DynamicQueryDto dynamicQueryDTO, Map<String, Object> parameters, Integer pageIndex, Integer pageSize) throws FileNotFoundException, DocumentException;
}
