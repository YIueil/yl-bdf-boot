package cc.yiueil.controller;

import cc.yiueil.common.ResultListDataRepresentation;
import cc.yiueil.converter.BpmnDisplayJsonConverter;
import cc.yiueil.domain.Model;
import cc.yiueil.exception.BusinessException;
import cc.yiueil.repository.ModelRepository;
import cc.yiueil.util.BeanUtils;
import cc.yiueil.util.XmlUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@RestController
public class ModelsController implements BaseController{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected ModelRepository modelRepository;

    @Autowired
    protected BpmnDisplayJsonConverter bpmnDisplayJsonConverter;

    /**
     * 查询流程模型列表
     *
     * @param filter    过滤条件
     * @param sort      排序
     * @param modelType 模型类型
     * @param request   请求体
     * @return 查询的模型列表集合
     */
    @RequestMapping(value = "/rest/models", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ResultListDataRepresentation getModels(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false) Integer modelType, HttpServletRequest request) {
        String filterText = null;
        List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), StandardCharsets.UTF_8);
        if (params != null) {
            for (NameValuePair nameValuePair : params) {
                if ("filterText".equalsIgnoreCase(nameValuePair.getName())) {
                    filterText = nameValuePair.getValue();
                }
            }
        }

        List<Model> models;
        String validFilter = makeValidFilterText(filterText);
        if (validFilter != null) {
            models = this.modelRepository.findModelsByModelType(modelType, validFilter);
        } else {
            models = this.modelRepository.findModelsByModelType(modelType);
        }

        return new ResultListDataRepresentation(models);
    }

    /**
     * 创建流程模型
     *
     * @param jsonMap 流程模型参数集合
     * @return 创建的模型信息
     */
    @RequestMapping(value = "/rest/models", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public Model createModel(@RequestBody Map<String, String> jsonMap) {
        String name = jsonMap.get("name");

        if (this.modelRepository.existsByName(name)) {
            throw new BusinessException("模型名称重复！");
        }

        Model model = new Model();
        model.setKey(jsonMap.get("key"));
        model.setName(name);
        model.setDescription(jsonMap.get("description"));
        model.setModelType(Integer.valueOf(jsonMap.get("modelType")));
        model.setVersion(1);
        createObjectNode(model);
        model = this.modelRepository.save(model);
        return model;
    }


    /**
     * 克隆模型
     * @param jsonMap 流程模型参数集合
     * @return 克隆的模型信息
     */
    @RequestMapping(value = "/rest/models/{modelId}/clone", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public Model cloneModel(@PathVariable String modelId, @RequestBody Map<String, String> jsonMap) {
        Model originModel = modelRepository.findById(modelId).orElseThrow();
        Model newModel = new Model();

        newModel.setModelEditorJson(originModel.getModelEditorJson());
        newModel.setThumbnail(originModel.getThumbnail());

        String name = jsonMap.get("name");

        if (this.modelRepository.existsByName(name)) {
            throw new BusinessException("模型名称重复！");
        }

        String key = jsonMap.get("key");
        String description = jsonMap.get("description");
        Integer modelType = Integer.valueOf(jsonMap.get("modelType"));

        newModel.setKey(key);
        newModel.setName(name);
        newModel.setDescription(description);
        newModel.setModelType(modelType);
        newModel.setVersion(1);

        createObjectNode(newModel);

        newModel = this.modelRepository.save(newModel);
        return newModel;
    }

    /**
     * 获取流程模型缩列图
     *
     * @param modelId 模型id
     * @return 模型缩略图的 byte[]
     */
    @RequestMapping(value = {"/rest/models/{modelId}/thumbnail"}, method = RequestMethod.GET, produces = "image/png")
    @ResponseStatus(value = HttpStatus.OK)
    public byte[] getModelThumbnail(@PathVariable String modelId) {
        Optional<Model> optional = this.modelRepository.findById(modelId);
        Model model = optional.orElseThrow();
        return model.getThumbnail();
    }

    /**
     * 根据modelId查询流程模型
     *
     * @param modelId 模型id
     * @return Model
     */
    @RequestMapping(value = "/rest/models/{modelId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Model getModel(@PathVariable String modelId) {
        Optional<Model> optional = this.modelRepository.findById(modelId);
        return optional.orElseThrow();
    }

    /**
     * 根据modelId删除流程模型
     *
     * @param modelId 模型id
     * @return Model
     */
    @RequestMapping(value = "/rest/models/{modelId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteModel(@PathVariable String modelId) {
        this.modelRepository.deleteById(modelId);
        return success();
    }

    /**
     * 根据modelId查询流程模型的关联情况
     *
     * @param modelId 模型id
     * @return Model
     */
    @RequestMapping(value = "/rest/models/{modelId}/parent-relations", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Object> getModelRelation(@PathVariable String modelId) {
        // TODO 实现获取流程模型的使用情况
        return new ArrayList<>();
    }

    /**
     * 根据modelId查询流程模型的JSON内容
     *
     * @param modelId 模型id
     * @return 模型json信息
     */
    @RequestMapping(value = "/rest/models/{modelId}/model-json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public JsonNode getModelJSON(@PathVariable String modelId) {
        ObjectNode displayNode = this.objectMapper.createObjectNode();
        Optional<Model> optional = this.modelRepository.findById(modelId);
        Model model = optional.orElseThrow();
        this.bpmnDisplayJsonConverter.processProcessElements(model, displayNode, new GraphicInfo());
        return displayNode;
    }

    /**
     * 导入流程定义xml文件
     *
     * @param request 请求体
     * @param file    文件
     * @return 生成的模型信息
     */
    @RequestMapping(value = "rest/import-process-model", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public Model importProcessModel(HttpServletRequest request, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && (fileName.endsWith(".bpmn") || fileName.endsWith(".bpmn20.xml"))) {
            try {
                XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                InputStreamReader xmlIn = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
                BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
                if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                    throw new RuntimeException("No process found in definition " + fileName);
                }

                if (bpmnModel.getLocationMap().isEmpty()) {
                    BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                    bpmnLayout.execute();
                }

                BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();
                ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);

                org.activiti.bpmn.model.Process process = bpmnModel.getMainProcess();
                String name = process.getId();
                if (StringUtils.isNotEmpty(process.getName())) {
                    name = process.getName();
                }
                String description = process.getDocumentation();

                Model model = new Model();
                model.setVersion(1);
                model.setKey(process.getId());
                model.setName(name);
                model.setDescription(description);
                model.setModelType(0);
                model.setCreated(Calendar.getInstance().getTime());
                model.setLastUpdated(Calendar.getInstance().getTime());
                model.setModelEditorJson(modelNode.toString());
                model = this.modelRepository.save(model);
                return model;
            } catch (Exception e) {
                throw new RuntimeException("Import failed for " + fileName + ", error message " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName);
        }
    }

    @RequestMapping(value = "/rest/import-process-model/text", method = RequestMethod.POST)
    public String importProcessModelText(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Model model = importProcessModel(request, file);
        String modelRepresentationJson;
        try {
            modelRepresentationJson = objectMapper.writeValueAsString(model);
        } catch (Exception e) {
            throw new RuntimeException("Model Representation could not be saved");
        }
        return modelRepresentationJson;
    }

    private String makeValidFilterText(String filterText) {
        String validFilter = null;
        if (filterText != null) {
            String trimmed = StringUtils.trim(filterText);
            if (!trimmed.isEmpty()) {
                validFilter = "%" + trimmed.toLowerCase() + "%";
            }
        }
        return validFilter;
    }

    private void createObjectNode(Model model) {
        ObjectNode editorNode = this.objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = this.objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.putIfAbsent("stencilset", stencilSetNode);
        ObjectNode propertiesNode = this.objectMapper.createObjectNode();
        propertiesNode.put("process_id", model.getKey());
        propertiesNode.put("name", model.getName());
        if (StringUtils.isNotEmpty(model.getDescription())) {
            propertiesNode.put("documentation", model.getDescription());
        }
        editorNode.putIfAbsent("properties", propertiesNode);

        ArrayNode childShapeArray = this.objectMapper.createArrayNode();
        editorNode.putIfAbsent("childShapes", childShapeArray);
        ObjectNode childNode = this.objectMapper.createObjectNode();
        childShapeArray.add(childNode);
        ObjectNode boundsNode = this.objectMapper.createObjectNode();
        childNode.putIfAbsent("bounds", boundsNode);
        ObjectNode lowerRightNode = this.objectMapper.createObjectNode();
        boundsNode.putIfAbsent("lowerRight", lowerRightNode);
        lowerRightNode.put("x", 130);
        lowerRightNode.put("y", 193);
        ObjectNode upperLeftNode = this.objectMapper.createObjectNode();
        boundsNode.putIfAbsent("upperLeft", upperLeftNode);
        upperLeftNode.put("x", 100);
        upperLeftNode.put("y", 163);
        childNode.putIfAbsent("childShapes", this.objectMapper.createArrayNode());
        childNode.putIfAbsent("dockers", this.objectMapper.createArrayNode());
        childNode.putIfAbsent("outgoing", this.objectMapper.createArrayNode());
        childNode.put("resourceId", "startEvent1");
        ObjectNode stencilNode = this.objectMapper.createObjectNode();
        childNode.putIfAbsent("stencil", stencilNode);
        stencilNode.put("id", "StartNoneEvent");
        String json = editorNode.toString();
        model.setModelEditorJson(json);
    }

}