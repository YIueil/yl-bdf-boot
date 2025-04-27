package cc.yiueil;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MyJsonObject {
    @JsonProperty("refid")
    private int refid;

    @JsonProperty("regexp_replace")
    private String regexpReplace;
}
