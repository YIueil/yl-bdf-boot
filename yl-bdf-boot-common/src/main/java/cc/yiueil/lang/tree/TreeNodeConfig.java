package cc.yiueil.lang.tree;

import lombok.*;

import java.io.Serializable;

/**
 * TreeNodeConfig 树属性配置
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:12
 * @version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final TreeNodeConfig DEFAULT_CONFIG = new TreeNodeConfig();

    /**
     * 属性名配置字段
     */
    private String idKey = "id";
    private String parentIdKey = "parentId";
    private String weightKey = "weight";
    private String nameKey = "name";
    private String childrenKey = "children";
}
