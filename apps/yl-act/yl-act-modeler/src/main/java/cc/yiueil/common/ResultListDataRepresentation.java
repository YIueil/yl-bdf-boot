package cc.yiueil.common;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultListDataRepresentation {
    protected Integer size;
    protected Long total;
    protected Integer start;
    protected List<? extends Object> data;

    public ResultListDataRepresentation() {}

    public ResultListDataRepresentation(List<? extends Object> data)
    {
        this.data = data;
        if (data != null)
        {
            this.size = data.size();
            this.total = (long) data.size();
            this.start = 0;
        }
    }
}
