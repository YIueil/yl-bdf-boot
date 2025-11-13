package cc.yiueil2;

import cc.yiueil.util.ext.SpringContextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BootApplicationTest {
    @Test
    public void test1() {
        Object springContextUtils = SpringContextUtils.getBean("springContextUtils");
        System.out.println();
    }
}