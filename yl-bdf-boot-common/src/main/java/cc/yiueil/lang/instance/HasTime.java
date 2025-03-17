package cc.yiueil.lang.instance;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * HasTime 具有时间
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:02
 * @version 1.0
 */
public interface HasTime {
    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    void setCreateTime(LocalDateTime createTime);

    /**
     * 获取创建时间
     * @return 创建时间
     */
    @Column(name = "create_time", columnDefinition = "创建时间")
    LocalDateTime getCreateTime();

    /**
     * 设置更新时间
     * @param modifyTime 更新时间
     */
    void setModifyTime(LocalDateTime modifyTime);

    /**
     * 获取更新时间
     * @return 更新时间
     */
    @Column(name = "modify_time", columnDefinition = "修改时间")
    LocalDateTime getModifyTime();
}
