package cc.yiueil.entity.file;

import cc.yiueil.lang.instance.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "file", schema = "yl_inst", indexes = {
        @Index(name = "idx_file_guid_unq", columnList = "guid", unique = true)
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity implements BaseEntity<Long> {
    //region 公共属性
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "generator")
    @SequenceGenerator(name = "generator", schema = "yl_inst", sequenceName = "s_file", allocationSize = 1)
    private Long id;
    private String guid;

    private Long createUserId;

    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    //endregion

    //region 主体属性
    @Column(nullable = false)
    private String fileName;

    @Column
    private String filePath;

    @Column
    private String fileExtension;

    @Column
    private String mimeType;

    @Column
    private String fileDetectedType;

    @Column
    private Long fileSize;

    @Column
    private String previewUrl;

    @Column
    private String downloadUrl;

    @Column
    private String deleteUrl;

    @Column
    private Boolean isDeleted = false;
    //endregion
}
