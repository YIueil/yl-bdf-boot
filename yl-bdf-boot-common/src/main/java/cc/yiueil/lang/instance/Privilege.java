package cc.yiueil.lang.instance;

/**
 * Privilege 权限门面
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:59
 * @version 1.0
 */
public interface Privilege {
    /**
     * 获取 id
     * @return id
     */
    Long getId();

    /**
     * 获取 guid
     * @return guid
     */
    String getGuid();

    /**
     * 获取 name
     * @return name
     */
    String getName();
}
