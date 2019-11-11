package com.robby.app.commons.properties.db;

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
public class DataSourceProperties {
    String url;
    String username;
    String password;
    String driverClassName;
    String type;
}
