package cc.yiueil.api.impl;

import cc.yiueil.api.ImageResource;
import cc.yiueil.entity.response.SmmsUploadResultEntity;
import cc.yiueil.entity.result.DownloadResult;
import cc.yiueil.entity.result.UploadResult;
import cc.yiueil.enums.SmmsEnum;
import cc.yiueil.exception.FileUploadException;
import cc.yiueil.lang.http.HttpHeader;
import cc.yiueil.url.SmmsUrl;
import cc.yiueil.util.HttpUtils;
import cc.yiueil.util.IoUtils;
import cc.yiueil.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * SmmsImageBedImpl 聚合SM.MS实现
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/18 14:33
 */
@Slf4j
public class SmmsImageHostImpl implements ImageResource {

    @Override
    public UploadResult upload(InputStream inputStream, String fileName, String fileType, boolean watermark) throws FileUploadException {
        try {
            Map<String, Object> formData = new HashMap<>(2);
            byte[] bytes = IoUtils.toByteArray(inputStream);
            formData.put(SmmsEnum.smfile.name(), bytes);
            formData.put(SmmsEnum.format.name(), "json");
            Map<String, String> header = new HashMap<>(3);
            header.put(HttpHeader.CONTENT_TYPE, HttpHeader.CONTENT_TYPE_MULTIPART_VALUE);
            header.put(HttpHeader.USERAGENT, HttpHeader.USERAGENT_DEFAULT);
            // todo 替换为配置项
            header.put(HttpHeader.AUTHORIZATION, "TEyA2TCcm4x7l0P4cxHFPp8PajEl7w76");
            String jsonString = HttpUtils.doPost(SmmsUrl.SMMS_URL, null, formData, header);
            System.out.println(jsonString);
            SmmsUploadResultEntity smmsUploadResultEntity = JsonUtils.parse(SmmsUploadResultEntity.class, jsonString);
            return UploadResult.success(smmsUploadResultEntity.getMessage(), smmsUploadResultEntity.getData().getUrl(), smmsUploadResultEntity.getData().getDelete());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileUploadException("文件上传失败");
        }
    }

    @Override
    public DownloadResult download() {
        return null;
    }

    @Override
    public void watermark(InputStream inputStream) {

    }
}
