package cc.yiueil.dialect;


import org.hibernate.dialect.MySQL57Dialect;

/**
 * MySQL5Dialect 方言配置类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 22:58
 * @version 1.0
 */
public class MySQL5Dialect extends MySQL57Dialect {
    /**
     *
     * @return
     */
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=UTF8";
    }
}
