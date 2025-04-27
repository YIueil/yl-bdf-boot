package cc.yiueil;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BootApplicationTest {
    @Autowired
    ResourceLoader resourceLoader;

    @Test
    public void test1() throws IOException {
        String serverPort = "22162";
        String serverIp = "9.77.254.9";
        String targetPath = "/mnt/f/YNYTGZ_Materials/";
        String scriptGeneratePath = "F:\\YNYTGZ_Materials\\";
        Map<Integer, List<String>> projectMap = new HashMap<>();
        Resource resource = resourceLoader.getResource("classpath:data.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<MyJsonObject> list = objectMapper.readValue(resource.getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, MyJsonObject.class));
        for (MyJsonObject myJsonObject : list) {
            int projectId = myJsonObject.getRefid();
            String regexpReplace = myJsonObject.getRegexpReplace();
            projectMap.computeIfAbsent(projectId, k -> new ArrayList<>()).add(regexpReplace);
        }

        try (FileWriter writer = new FileWriter(scriptGeneratePath + "1.sh")) {
            writer.write("#!/bin/bash"); // 写入脚本头部
            writer.append("\n");
            projectMap.forEach((key, pathList) -> {
                try {
                    String echoStr = "echo \"项目ID: " + key + "\"";
                    writer.write(echoStr);
                    writer.append("\n");
                    for (String sourcePath : pathList) {
                        writer.write("rsync -avz --delete --progress " + "-e \"ssh -p " + serverPort + "\" " + "app@" + serverIp + ":/opt/file" + sourcePath + " " + targetPath + " >> ./temp.log\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}