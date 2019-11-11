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
public class RedisConnectProperties {
    String host;
    int port;
    int timeout;
    String password;
}
