package cc.yiueil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class ExampleBootApplicationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test1() {
        try (InputStream inputStream = ExampleBootApplicationTest.class.getClassLoader().getResourceAsStream("static/result.json")) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            System.out.println(jsonNode);
            JsonNode dataObject = jsonNode.get("data");
            JsonNode listArray = dataObject.get("list");
            for(JsonNode item : listArray) {
                System.out.println(item.get("season_id").asInt());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}