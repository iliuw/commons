package com.robby.app.commons.enums;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
public enum Methods implements Supplier<Methods> {
    GET,POST,PUT,DELETE
    ;

    @Override
    public Methods get() {
        return this;
    }
}
