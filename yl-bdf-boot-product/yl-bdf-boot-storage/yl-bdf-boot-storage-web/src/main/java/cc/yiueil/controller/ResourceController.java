package cc.yiueil.controller;


import cc.yiueil.service.ImageResourceService;
import cc.yiueil.service.impl.SmmsImageBedServiceImpl;
import cc.yiueil.dto.FileDto;
import cc.yiueil.entity.result.ImageUploadResult;
import cc.yiueil.entity.result.UploadResult;
import cc.yiueil.general.RestUrl;
import cc.yiueil.service.ResourceService;
import cc.yiueil.util.IoUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Tag(name = "资源控制器")
@Slf4j
@RestController
@RequestMapping(value = RestUrl.BASE_PATH + "/resource")
public class ResourceController implements LoggedController {

    @Autowired
    ResourceService resourceService;

    @Operation(summary = "图片上传", description = "图片上传, 图片上传到SM.MS图床")
    @PostMapping(value = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String imageUpload(@RequestParam("file") MultipartFile multipartFile) {
        // 定义具体的实现, 这里是上传到 SM.MS 图床
        ImageResourceService imageResourceService = new SmmsImageBedServiceImpl();
        try {
            ImageUploadResult uploadResult = imageResourceService.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
            return success(uploadResult);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return fail(e.getMessage());
        }
    }

    @Operation(summary = "文件查询", description = "文件查询, 查询出所有文件")
    @GetMapping(value = "/file/list")
    public String listFile() {
        return success(resourceService.listFile());
    }

    @Operation(summary = "文件上传", description = "文件上传, 文件上传到本地路径")
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String fileUpload(@RequestPart("file") MultipartFile multipartFile) {
        try {
            UploadResult uploadResult = resourceService.fileUpload(multipartFile);
            return success(uploadResult);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return fail(e.getMessage());
        }
    }

    @Operation(summary = "文件分片上传", description = "文件分片上传, 文件分片上传到本地路径")
    @PostMapping(value = "/file/MultiPartUpload")
    public String fileMultiPartUpload(@RequestParam("file") MultipartFile multipartFile) {
        // TODO 文件分片上传 功能实现
        return null;
    }

    @Operation(summary = "文件下载", description = "文件下载, 进行文件下载")
    @GetMapping(value = "/file/download/{guid}")
    public void fileDownload(@PathVariable("guid") String guid, HttpServletResponse response) {
        FileDto fileDto = resourceService.getFileByGuid(guid);
        Path filePath = Paths.get(fileDto.getFilePath()).normalize();

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getFileName() + "\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            Resource resource = new UrlResource(filePath.toUri());
            try (InputStream inputStream = resource.getInputStream()) {
                IoUtils.write(inputStream, outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "文件预览", description = "文件预览")
    @GetMapping(value = "/file/preview/{guid}")
    public void filePreview(@PathVariable("guid") String guid, HttpServletResponse response) {
        FileDto fileDto = resourceService.getFileByGuid(guid);
        Path filePath = Paths.get(fileDto.getFilePath()).normalize();

        response.setContentType(fileDto.getMimeType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileDto.getFileName() + "\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            Resource resource = new UrlResource(filePath.toUri());
            try (InputStream inputStream = resource.getInputStream()) {
                IoUtils.write(inputStream, outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
