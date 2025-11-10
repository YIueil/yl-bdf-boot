package cc.yiueil.service;

import cc.yiueil.dto.FileDto;
import cc.yiueil.entity.result.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ResourceService {

    UploadResult fileUpload(MultipartFile multipartFile) throws IOException;

    List<FileDto> listFile();

    FileDto getFileByGuid(String guid);
}
