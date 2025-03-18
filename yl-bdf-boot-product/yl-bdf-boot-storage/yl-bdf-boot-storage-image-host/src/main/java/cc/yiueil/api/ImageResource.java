package cc.yiueil.api;

import cc.yiueil.entity.result.DownloadResult;
import cc.yiueil.entity.result.UploadResult;
import cc.yiueil.exception.FileUploadException;

import java.io.InputStream;

/**
 * ImageResource 图片资源处理接口
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/1/18 11:54
 */
public interface ImageResource {
    /**
     * 上传图片
     *
     * @param inputStream 文件流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param watermark 是否添加水印
     * @return 上传结果
     * @throws FileUploadException 文件上传异常
     */
    UploadResult upload(InputStream inputStream, String fileName, String fileType, boolean watermark) throws FileUploadException;

    /**
     * 上传图片
     *
     * @param inputStream 文件流
     * @param fileName 文件名
     * @param fileType 文件类型
     * @return 上传结果
     * @throws FileUploadException 文件上传异常
     */
    default UploadResult upload(InputStream inputStream, String fileName, String fileType) throws FileUploadException {
        return upload(inputStream, fileName, fileType, false);
    }

    /**
     * 下载图片
     * @return 下载结果
     */
    DownloadResult download();

    /**
     * 为图片添加水印
     * @param inputStream 文件流
     */
    void watermark(InputStream inputStream);
}
