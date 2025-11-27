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
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_0yukhv2cc222", variables);
        System.out.println(processInstance);
    }

    @Test
    public void testUser1Complete() {
        String userId = "李四";
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee(userId)
                .active()
                .list();
        System.out.println(list);
        if (!list.isEmpty()) {
            taskService.complete(list.get(0).getId());
            System.out.println("完成了用户:" + userId + "的任务" );
        }
    }
}