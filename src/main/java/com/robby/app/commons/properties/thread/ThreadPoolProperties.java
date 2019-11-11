package com.robby.app.commons.properties.thread;

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
public class ThreadPoolProperties {
    int coreIdle;
    int maxIdle;
    long keepAlive;
    int capacity;
}
