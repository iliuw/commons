package com.robby.app.commons.properties.socketio;

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
public class SocketIOServerProperties {
    int upgradeTimeout;
    int pingInterval;
    int pingTimeout;
    int maxIdle;
    int maxSession;
    String idleIdPrefix;
    String sessionIdPrefix;
    int maxFramePayloadLength;
    int maxHttpContentLength;
    int bossCount;
    int workCount;
    boolean allowCustomRequests;

}
