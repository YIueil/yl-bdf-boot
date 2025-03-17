package cc.yiueil.lang.instance;

import java.time.LocalDateTime;

/**
 * UserToken 用户认证信息类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 23:01
 * @version 1.0
 */
public interface UserToken {
    /**
     * 获取 id
     * @return id
     */
    Long getId();

    /**
     * 设置 id
     * @param id id
     */
    void setId(Long id);

    /**
     * 获取 guid
     * @return guid
     */
    String getGuid();

    /**
     * 设置 guid
     * @param guid guid
     */
    void setGuid(String guid);

    /**
     * 获取 username
     * @return username
     */
    String getUserName();

    /**
     * 设置 username
     * @param userName username
     */
    void setUserName(String userName);

    /**
     * 获取登陆名
     * @return 登陆名
     */
    String getLoginName();

    /**
     * 设置登陆名
     * @param loginName 登陆名
     */
    void setLoginName(String loginName);

    /**
     * 获取创建时间
     * @return 创建时间
     */
    LocalDateTime getCreateDateTime();

    /**
     * 设置创建时间
     * @param localDateTime 创建时间
     */
    void setCreateDateTime(LocalDateTime localDateTime);
}
