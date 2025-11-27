package cc.yiueil.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class StencilSetController {

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 获取编辑器组件及配置项信息
     * @return 配置项语言包
     */
    @RequestMapping(value="/rest/stencil-sets/editor", method= RequestMethod.GET, produces="application/json")
    public JsonNode getStencilSetForEditor(HttpServletRequest request) {
        String acceptLanguage = request.getLocale().getLanguage();
        try {
            if ("zh".equals(acceptLanguage) || "zh_CN".equals(acceptLanguage)) {
                return this.objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("static/editor-app/stencilsets/stencilset_bpmn_cn.json"));
            }
            return this.objectMapper.readTree(getClass().getClassLoader().getResourceAsStream("static/editor-app/stencilsets/stencilset_bpmn.json"));
        } catch (Exception e) {
            log.error("Error reading bpmn stencil set json", e);
            throw new RuntimeException("Error reading bpmn stencil set json");
        }
    }

}
