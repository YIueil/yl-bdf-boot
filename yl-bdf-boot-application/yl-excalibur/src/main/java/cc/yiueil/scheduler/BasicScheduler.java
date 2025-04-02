package cc.yiueil.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BasicScheduler {
    @Scheduled(cron = "0 */1 * * * ?")
    public void basicScheduler() {
        log.debug("一分钟执行一次...");
    }
}
