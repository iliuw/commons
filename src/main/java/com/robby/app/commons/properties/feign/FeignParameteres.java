package com.robby.app.commons.properties.feign;

import com.fasterxml.jackson.annotation.*;
import com.robby.app.commons.enums.Methods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("FeignParameteres")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        defaultImpl = FeignParameteres.class,
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "@class"
)
public class FeignParameteres implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -6051988252862841621L;
    @JsonProperty("uri")
    @ApiModelProperty(value = "接口URI地址", name = "uri")
    String uri;
    @JsonProperty("domain")
    @ApiModelProperty(value = "接口域", name = "domain")
    String domain;
    @Builder.Default
    @JsonProperty("requests")
    @ApiModelProperty(value = "HTTP/1.1 Request参数", name = "requests")
    Map<String, Object> requests = new LinkedHashMap<>();
    @Builder.Default
    @JsonProperty("body")
    @ApiModelProperty(value = "通过RequestBody传递的参数对象", name = "body")
    Object body = new LinkedHashMap<>();
    @JsonProperty("method")
    @ApiModelProperty(value = "请求方法", name = "method")
    Methods method;
}
