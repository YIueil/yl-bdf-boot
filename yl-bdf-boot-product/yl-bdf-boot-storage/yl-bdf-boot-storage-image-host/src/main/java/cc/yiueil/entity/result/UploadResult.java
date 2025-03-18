package cc.yiueil.entity.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * UploadResult 上传结果类
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/18 11:58
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class UploadResult {
    private boolean isSuccess;
    private String message;
    private String removeUrl;
    private String mdUrl;
    private String htmlUrl;
    private String aUrl;
    private String url;

    public static UploadResult success(String message, String url, String removeUrl) {
        return new UploadResult(true, message, removeUrl, null, null, null, url);
    }
}
