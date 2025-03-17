package cc.yiueil.lang.instance;

import java.io.Serializable;

/**
 * Builder 建造者模式 门面
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/30 22:53
 * @version 1.0
 */
public interface Builder<T> extends Serializable {
	/**
	 * 构建
	 *
	 * @return 被构建的对象
	 */
	T build();
}