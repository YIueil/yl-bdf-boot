package cc.yiueil.service.impl;

import cc.yiueil.service.FileResourceService;
import cc.yiueil.data.impl.JpaBaseDao;
import cc.yiueil.dto.FileDto;
import cc.yiueil.entity.file.FileEntity;
import cc.yiueil.entity.result.UploadResult;
import cc.yiueil.exception.ResourceNotFoundException;
import cc.yiueil.properties.FileResourceProperties;
import cc.yiueil.repository.FileRepository;
import cc.yiueil.service.ResourceService;
import cc.yiueil.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    JpaBaseDao baseDao;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileResourceProperties fileResourceProperties;

    @Autowired
    FileResourceService fileResource;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResult fileUpload(MultipartFile multipartFile) throws IOException {
        String dirPath = fileResourceProperties.getUploadPath();

        FileEntity saveFileEntity;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            FileEntity fileEntity =
                    fileResource.upload(inputStream, dirPath, multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize());
            saveFileEntity = baseDao.save(fileEntity);
        }
        FileDto fileDto = BeanUtils.copyProperties(saveFileEntity, new FileDto());
        return UploadResult.success(fileDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileDto> listFile() {
        Iterable<FileEntity> fileEntityList = fileRepository.findAll();
        return StreamSupport.stream(fileEntityList.spliterator(), false)
                .map(fileEntity -> BeanUtils.copyProperties(fileEntity, new FileDto()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FileDto getFileByGuid(String guid) {
        FileEntity fileEntity = baseDao.findByGuid(FileEntity.class, guid).orElseThrow(() -> new ResourceNotFoundException("文件不存在"));
        return BeanUtils.copyProperties(fileEntity, new FileDto());
    }
}
