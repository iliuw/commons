package com.robby.app.commons.pojo;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.core.io.InputStreamResource;

import java.io.Serializable;

/**
 * Created @ 2019/11/12
 *
 * @author liuwei
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UploadDto", description = "上传文件后返回的数据封装")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        defaultImpl = UploadDto.class,
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "@class"
)
public class UploadDto implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 7657101486295001788L;

    @JsonProperty("originalName")
    @ApiModelProperty(value = "文件原始名称", name = "originalName")
    String originalName;

    @JsonProperty("destinationName")
    @ApiModelProperty(value = "服务器上存储的文件名称", name = "destinationName")
    String destinationName;

    @JsonProperty("directory")
    @ApiModelProperty(value = "文件存储的物理路径", name = "directory")
    String directory;

    @JsonProperty("visitUri")
    @ApiModelProperty(value = "文件访问的相对路径，不含域", name = "visitUri")
    String visitUri;

    @JsonProperty("fileSize")
    @ApiModelProperty(value = "文件长度，单位是字节", name = "fileSize")
    long fileSize;

    @JsonProperty("extension")
    @ApiModelProperty(value = "文件扩展名", name = "extension")
    String extension;

    @JsonIgnore
    InputStreamResource resource;
}
