package cc.yiueil.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
    private String guid;

    private String fileName;

    private String filePath;

    private String mimeType;

    private String fileExtension;

    private String fileDetectedType;

    private Long fileSize;

    private String previewUrl;

    private String downloadUrl;

    private String deleteUrl;

}
