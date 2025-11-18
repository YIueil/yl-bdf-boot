package cc.yiueil.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@Schema(name = "分页视图对象")
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1195144831131702958L;

    @NotNull
    @Min(value = 1, message = "页码必须是正整数")
    @Schema(name = "页码")
    private int pageIndex;

    @Schema(name = "每页数量")
    @Min(value = 1, message = "每页数量必须是正整数")
    private int pageSize;

    @Schema(name = "页面总数")
    private int pageTotal;

    @Schema(name = "元素总数")
    private int itemCounts;

    @Schema(name = "数据体")
    private Object list;
}
