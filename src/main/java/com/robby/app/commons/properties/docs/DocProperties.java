package com.robby.app.commons.properties.docs;

import lombok.*;

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
@EqualsAndHashCode(callSuper = false)
public class DocProperties {
    String title;
    String desc;
    String author;
    String email;
    String version;
    String domain;
    String basePackage;
    String pathMapper;
}
