package cc.yiueil.lang.instance;

import javax.persistence.Column;

/**
 * SortAble 排序支持声明接口
 *
 * @author 弋孓 YIueil@163.com
 * @version 1.0
 * @date 2024/4/5 20:20
 */
public interface SortAble {
    /**
     * 设置排序
     *
     * @param sort 排序编号
     */
    void setSort(Integer sort);

    /**
     * 获取排序
     *
     * @return 排序编号
     */
    @Column(name = "sort", columnDefinition = "排序")
    Integer getSort();
}
