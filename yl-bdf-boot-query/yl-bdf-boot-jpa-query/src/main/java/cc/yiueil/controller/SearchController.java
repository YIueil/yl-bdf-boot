package cc.yiueil.controller;

import cc.yiueil.constant.SearchRestUrl;
import cc.yiueil.dto.DynamicQueryDto;
import cc.yiueil.general.RestUrl;
import cc.yiueil.service.SearchService;
import cc.yiueil.vo.PageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchController 通用查询
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:26
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = RestUrl.BASE_PATH + SearchRestUrl.QUERY)
public class SearchController implements BaseController {
    @Autowired
    SearchService searchService;

    @PostMapping(value = "searchPage")
    @Operation(summary = "分页动态查询", description = "通过配置查询分页结构", responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = PageVo.class))) })
    public String searchPage(@RequestParam(defaultValue = "1") Integer pageIndex,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestBody @Validated DynamicQueryDto dynamicQueryDTO) {
        // 1、构建查询参数
        Map<String, Object> filterMap = dynamicQueryDTO.getFilter();
        filterMap = filterMap == null ? new HashMap<>(16) : new HashMap<>(filterMap);
        try {
            return success(searchService.searchPage(dynamicQueryDTO, filterMap, pageIndex, pageSize));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return error("查询服务配置文件不存在", e);
        } catch (DocumentException e) {
            return error("查询配置文件结构有误", e);
        }
    }

    @PostMapping(value = "searchOne")
    @Operation(summary = "动态查询对象", description = "通过配置查询对象结构", responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = PageVo.class))) })
    public String searchOne(@RequestBody @Validated DynamicQueryDto dynamicQueryDTO) {
        return success();
    }
}
