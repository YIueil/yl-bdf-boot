package cc.yiueil.entity.result;

import cc.yiueil.dto.FileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UploadResult {
    private boolean isSuccess;

    private FileDto file;

    public static UploadResult success(FileDto fileDto) {
        return new UploadResult(true, fileDto);
    }
}
