package cc.yiueil.service.impl;

import cc.yiueil.service.FileResourceService;
import cc.yiueil.entity.file.FileEntity;
import cc.yiueil.exception.FileUploadException;
import cc.yiueil.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileResourceServiceImpl implements FileResourceService {

    @Override
    public FileEntity upload(InputStream inputStream, String dirPath, String fileName, String fileType, Long fileSize) throws FileUploadException, IOException {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        FileEntity fileEntity = new FileEntity();
        if (StringUtils.isEmpty(fileName)) {
            throw new FileUploadException("原始文件名缺失");
        }
        fileEntity.setFileName(fileName);
        fileEntity.setFileExtension(fileExtension);
        fileEntity.setMimeType(fileType);
        fileEntity.setFileSize(fileSize);

        // 创建上传目录（如果不存在创建出来）
        Path uploadPath = Paths.get(dirPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID() + fileExtension;

        // 保存文件到服务器
        Path filePath = uploadPath.resolve(uniqueFileName);
        fileEntity.setFilePath(filePath.toString());
        Files.copy(inputStream, filePath);
        return fileEntity;
    }
}
