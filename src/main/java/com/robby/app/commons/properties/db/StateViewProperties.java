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
public class StateViewProperties {
    String loginUsername;
    String loginPassword;
    String allow;
    String deny;
}
