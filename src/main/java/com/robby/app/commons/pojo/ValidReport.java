package com.robby.app.commons.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 参数校验报告
 * Created @ 2020/2/21
 * @author liuwei
 */
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ValidReport implements Serializable {
    @Builder.Default
    boolean hasErrors = false;
    List<String> errorMsg;
}
