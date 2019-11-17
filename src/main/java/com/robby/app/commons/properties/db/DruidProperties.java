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
public class DruidProperties {
    int initialSize;
    int maxIdle;
    int maxActive;
    int minIdle;
    long maxWait;
    long timeBetweenEvictionRunsMillis;
    long minEvictableIdleTImeMillis;
    String validationQuery;
    Boolean testOnBorrow;
    Boolean testOnReturn;
    Boolean testWhileIdle;
    Boolean poolPreparedStatements;
    String filter;
    String connectionProperties;
    Boolean debug;
    String dbName;
}
