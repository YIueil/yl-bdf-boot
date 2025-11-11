package cc.yiueil.query.instance;

import lombok.Getter;
import lombok.Setter;

/**
 * Filter
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:48
 * @version 1.0
 */
@Getter
@Setter
public class Filter {
    private String name;

    private String left;

    private String right;

    private String text;

    private String type;

    @Override
    public String toString() {
        return text;
    }
}
