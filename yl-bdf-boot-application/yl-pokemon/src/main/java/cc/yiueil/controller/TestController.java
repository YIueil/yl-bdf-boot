package cc.yiueil.controller;

import cc.yiueil.api.ImageResource;
import cc.yiueil.entity.result.UploadResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Tag(name = "APP-测试")
@RestController
@RequestMapping(value = "test")
public class TestController implements BaseController {
    @Autowired
    private ImageResource imageResource;

    @Operation(description = "文件上传测试接口")
    @PostMapping(value = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String imageUpload(
            @RequestPart("multipartFile") MultipartFile multipartFile) {
        try {
            UploadResult uploadResult = imageResource.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType());
            return success(uploadResult);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return fail(e.getMessage());
        }
    }
}
