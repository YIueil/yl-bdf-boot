package cc.yiueil;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ActModelerApplicationTest {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void testCreateProcess() {
        Map<String, Object> variables = new HashMap<>();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TASK_3065547b4b1f4c50aa89bcacdb47093833", variables);
        System.out.println(processInstance);
    }

    @Test
    public void testUser1Complete() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("1")
                .active()
                .list();
        System.out.println(list);
        if (!list.isEmpty()) {
            taskService.complete(list.get(0).getId());
        }
    }
}