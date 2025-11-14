package cc.yiueil2.scheduler;

import cc.yiueil.util.HttpUtils;
import cc.yiueil.util.ParseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class BasicScheduler {
    // @Scheduled(cron = "0 */1 * * * ?")
    public void basicScheduler() {
        log.debug("一分钟执行一次...");
        Map<String, String> params = new HashMap<>(16);
        Map<String, String> headers = new HashMap<>(16);
        buildParams(params);
        buildHeaders(headers);
        params.put("_", ParseUtils.getString(System.currentTimeMillis(), null));
        try {
            HttpUtils.doGet("https://activity.zlongame.com/activity/cmn/card/csmweb.do", params, headers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildHeaders(Map<String, String> headers) {
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36 Edg/134.0.0.0");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        headers.put("cache-control", "no-cache");
        headers.put("pragma", "no-cache");
        headers.put("referer", "https://wiki.biligame.com/");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"Microsoft Edge\";v=\"134\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "script");
        headers.put("sec-fetch-mode", "no-cors");
        headers.put("sec-fetch-site", "cross-site");
        headers.put("sec-fetch-storage-access", "active");
    }

    private static void buildParams(Map<String, String> params) {
        params.put("pd_acti_cb", "jsonpcard_8089");
        params.put("appkey", "1486458782785");
        params.put("card_user", "3378749108131726175");
        params.put("card_channel", "0123456789");
        params.put("card_server", "6001");
        params.put("card_role", "3378749108131726175");
        params.put("card_code", "9M96C2U2H");
        params.put("type", "2");
    }
}
