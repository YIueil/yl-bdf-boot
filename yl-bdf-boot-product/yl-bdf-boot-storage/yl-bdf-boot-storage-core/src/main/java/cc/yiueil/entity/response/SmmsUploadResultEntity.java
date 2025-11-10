package cc.yiueil.entity.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * SmmsUploadResultEntity SMMS上传返回结果对象
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/19 0:01
 */
@Getter
@Setter
@ToString
public class SmmsUploadResultEntity implements Serializable {
    private boolean success;
    private String code;
    private String message;
    private Data data;

    @Getter
    @Setter
    @ToString
    public static class Data implements Serializable {
        private int fileId;
        private int width;
        private int height;
        private String filename;
        private String storename;
        private int size;
        private String path;
        private String hash;
        private String url;
        private String delete;
        private String page;
    }
}
