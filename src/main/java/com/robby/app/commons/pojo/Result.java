package com.robby.app.commons.pojo;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Result", description = "统一返回数据封装")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        defaultImpl = Result.class,
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "@class"
)
public class Result<T extends Object> implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 5717310186449254793L;
    @JsonProperty("code")
    @ApiModelProperty(value = "执行结果状态码", name = "code", example = "0")
    @Builder.Default
    int code = 0;
    @JsonProperty("message")
    @ApiModelProperty(value = "执行结果说明", name = "message", example = "Do success")
    @Builder.Default
    String message = "Do success";
    @JsonProperty("data")
    @ApiModelProperty(value = "返回数据", name = "data")
    T data;
}
