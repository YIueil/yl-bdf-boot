package cc.yiueil.lang.instance;

/**
 * CodeStatus 状态值门面
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:54
 * @version 1.0
 */
public interface CodeStatus {
    /**
     * 获取状态值
     * @return 状态值
     */
    Integer getCode();

    /**
     * 获取状态描述信息
     * @return 描述信息
     */
    String getMsg();
}
