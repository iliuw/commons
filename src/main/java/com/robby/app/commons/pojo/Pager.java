package com.robby.app.commons.pojo;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(value = "Pager", description = "分页数据封装")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        defaultImpl = Pager.class,
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "@class"
)
public class Pager<T extends Object> implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 1685120591623155888L;
    @JsonProperty("current")
    @ApiModelProperty(value = "当前页码", name = "current", example = "1")
    int current;
    @JsonProperty("size")
    @ApiModelProperty(value = "分页长度", name = "size", example = "20")
    int size;
    @JsonProperty("count")
    @ApiModelProperty(value = "数据总长度", name = "count", example = "100")
    int count;
    @JsonProperty("total")
    @ApiModelProperty(value = "总页码数", name = "total", example = "5")
    int total;
    @JsonProperty("data")
    @ApiModelProperty(value = "当前页数据集合", name = "data")
    List<T> data;
}
