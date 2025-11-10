package cc.yiueil.api;

import cc.yiueil.entity.file.FileEntity;
import cc.yiueil.exception.FileUploadException;

import java.io.IOException;
import java.io.InputStream;

public interface FileResource {

    /**
     * 文件上传
     * @param inputStream 文件流
     * @param dirPath 上传路径名称
     * @param fileName 文件名称
     * @param fileType MIME类型
     * @param fileSize 文件大小
     * @return 文件上传异常
     * @throws FileUploadException 文件上传异常
     * @throws IOException IO异常
     */
    FileEntity upload(InputStream inputStream, String dirPath, String fileName, String fileType, Long fileSize) throws FileUploadException, IOException;
}
