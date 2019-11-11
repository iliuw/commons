package com.robby.app.commons.properties.redis;

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
public class RedisPoolProperties {
    int maxActive;
    int maxIdle;
    int minIdle;
    long maxWaitMillis;
    Boolean testOnBorrow;
    Boolean testOnReturn;
    Boolean testWhileIdle;
    Boolean blockWhenExhausted;
    long timeBetweenEvictionRunsMillis;
    long minEvictableIdleTimeMillis;
    int numTestsPerEvictionRun;
}
