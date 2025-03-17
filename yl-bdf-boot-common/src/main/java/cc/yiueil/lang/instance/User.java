package cc.yiueil.lang.instance;


import java.io.Serializable;

/**
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 21:19
 * @version 1.0 用户门面
 * @param <T> 用户实体主键类型
 */
public interface User<T> extends Serializable {
    /**
     * 获取id
     * @return id
     */
    T getId();

    /**
     * 设置id
     * @param id id
     */
    void setId(T id);

    /**
     * 获取guid
     * @return guid
     */
    String getGuid();

    /**
     * 设置guid
     * @param guid guid
     */
    void setGuid(String guid);

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
     * 获取用户名
     * @return 用户名
     */
    String getUserName();

    /**
     * 设置用户名
     * @param userName 用户名
     */
    void setUserName(String userName);

    /**
     * 获取用户密码
     * @return 用户密码
     */
    String getPassword();

    /**
     * 设置用户密码
     * @param password 用户密码
     */
    void setPassword(String password);
}
