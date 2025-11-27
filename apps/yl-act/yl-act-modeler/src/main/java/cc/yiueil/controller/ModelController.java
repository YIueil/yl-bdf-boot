package cc.yiueil.controller;

import cc.yiueil.domain.Model;
import cc.yiueil.repository.ModelRepository;
import cc.yiueil.service.ModelImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
public class ModelController implements BaseController{

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected ModelRepository modelRepository;

    @Autowired
    protected ModelImageService modelImageService;

    @Autowired
    RepositoryService repositoryService;

    /**
     * 根据modelId查询模型信息
     *
     * @param modelId 模型id
     * @return 模型定义
     */
    @RequestMapping(value = "/rest/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public ObjectNode getModelJSON(@PathVariable String modelId) {
        Optional<Model> optional = this.modelRepository.findById(modelId);
        Model model = optional.orElseThrow();
        ObjectNode modelNode = this.objectMapper.createObjectNode();
        modelNode.put("modelId", model.getId());
        modelNode.put("name", model.getName());
        modelNode.put("key", model.getKey());
        modelNode.put("description", model.getDescription());
        modelNode.putPOJO("lastUpdated", model.getLastUpdated());
        modelNode.put("lastUpdatedBy", model.getLastUpdatedBy());
        if (StringUtils.isNotEmpty(model.getModelEditorJson())) {
            try {
                ObjectNode editorJsonNode = (ObjectNode) this.objectMapper.readTree(model.getModelEditorJson());
                editorJsonNode.put("modelType", "model");
                modelNode.putIfAbsent("model", editorJsonNode);
            } catch (Exception e) {
                throw new RuntimeException("Error reading editor json " + modelId);
            }
        } else {
            ObjectNode editorJsonNode = this.objectMapper.createObjectNode();
            editorJsonNode.put("id", "canvas");
            editorJsonNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = this.objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorJsonNode.put("modelType", "model");
            modelNode.putIfAbsent("model", editorJsonNode);
        }
        return modelNode;
    }

    /**
     * 保存流程模型
     *
     * @param modelId 模型id
     * @param values 模型map
     * @return 保存的模型信息
     */
    @RequestMapping(value = "/rest/model/{modelId}/editor/json", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public Model saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
        Optional<Model> optional = this.modelRepository.findById(modelId);
        Model model = optional.orElseThrow();
        String name = values.getFirst("name");
        String key = values.getFirst("key");
        String description = values.getFirst("description");
        String json = values.getFirst("json_xml");

        model.setLastUpdated(new Date());
        model.setName(name);
        model.setKey(key);
        model.setDescription(description);
        model.setModelEditorJson(json);
        model.setVersion(1);
        ObjectNode jsonNode;
        try {
            jsonNode = (ObjectNode) this.objectMapper.readTree(model.getModelEditorJson());
        } catch (Exception e) {
            throw new RuntimeException("Could not deserialize json model");
        }

        modelImageService.generateThumbnailImage(model, jsonNode);
        modelRepository.save(model);
        return model;
    }

    /**
     * 部署流程模型
     * @param modelId 模型id
     */
    @RequestMapping(value = "/rest/model/{modelId}/editor/deploy", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String deployModel(@PathVariable String modelId) {
        Model modelData = modelRepository.findById(modelId).orElseThrow();
        ObjectNode modelNode;
        try {
            modelNode = (ObjectNode) this.objectMapper.readTree(modelData.getModelEditorJson());

            byte[] bpmnBytes;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .key(modelData.getKey())
                    .addString(processName, new String(bpmnBytes, StandardCharsets.UTF_8))
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            modelRepository.save(modelData);
            return success(deployment);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return fail(e.getMessage());
        }
    }
}