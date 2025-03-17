package cc.yiueil.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * PageVo 分页包装类
 * @author 弋孓 YIueil@163.com
 * @date 2023/5/31 23:23
 * @version 1.0
 */
@Getter
@Setter
@ApiModel(value = "分页视图对象")
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1195144831131702958L;

    @NotNull
    @Min(value = 1, message = "页码必须是正整数")
    @ApiModelProperty(value = "页码")
    private int pageIndex;

    @ApiModelProperty(value = "每页数量")
    @Min(value = 1, message = "每页数量必须是正整数")
    private int pageSize;

    @ApiModelProperty(value = "页面总数")
    private int pageTotal;

    @ApiModelProperty(value = "元素总数")
    private int itemCounts;

    @ApiModelProperty(value = "数据体")
    private Object list;
}
