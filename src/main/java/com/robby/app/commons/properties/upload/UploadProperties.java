package com.robby.app.commons.properties.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
public class UploadProperties implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 7472161497975119036L;
    String saveFolder;
    String visitBasePath;
    @Builder.Default
    Boolean useOriginalName = false;
}
