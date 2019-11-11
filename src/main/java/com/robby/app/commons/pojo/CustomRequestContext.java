package com.robby.app.commons.pojo;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
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
@ApiModel("CustomRequestContext")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        defaultImpl = CustomRequestContext.class,
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "@class"
)
public class CustomRequestContext implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -634374954109003955L;
    @JsonProperty("ipAddr")
    @ApiModelProperty(value = "Request来源的IP地址", name = "ipAddr")
    String ipAddr;
    @JsonProperty("genToken")
    @ApiModelProperty(value = "OAuth2用来换取access_token的凭证", name = "genToken")
    String genToken;
    @JsonProperty("accessToken")
    @ApiModelProperty(value = "OAuth2请求验权的凭证", name = "accessToken")
    String accessToken;
    @JsonProperty("appId")
    @ApiModelProperty(value = "应用ID", name = "appId")
    String appId;
    @JsonProperty("appSecret")
    @ApiModelProperty(value = "应用安全码", name = "appSecret")
    String appSecret;
    @JsonProperty("sendTime")
    @ApiModelProperty(value = "Request请求时间戳", name = "sendTime")
    long sendTime;
    @JsonProperty("headers")
    @ApiModelProperty(value = "完整的HTTP-HEADER信息", name = "headers")
    Map<String, String> headers;
}
