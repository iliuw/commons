package com.robby.app.commons.enums;

import java.util.function.Supplier;

/**
 * Created @ 2019/11/12
 *
 * @author liuwei
 */
public enum Excels implements Supplier<String> {
    xls("xls"),
    xlsx("xlsx")
    ;
    final String target;

    Excels(String target) {
        this.target = target;
    }

    public static Excels getByTarget(String target) {
        for(Excels e: Excels.values()) {
            if(target.equals(e.target)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String get() {
        return this.target;
    }
}
