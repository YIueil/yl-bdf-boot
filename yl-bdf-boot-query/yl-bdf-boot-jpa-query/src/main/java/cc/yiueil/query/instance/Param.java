package cc.yiueil.query.instance;

import lombok.Getter;
import lombok.Setter;

/**
 * Param
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:29
 * @version 1.0
 */
@Getter
@Setter
public class Param {
    private String name;

    private String defaultValue;

    private String text;

    @Override
    public String toString() {
        return text;
    }
}
