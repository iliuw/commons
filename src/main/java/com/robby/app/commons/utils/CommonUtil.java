package com.robby.app.commons.utils;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

/**
 * 通用函数库
 * Created @ 2019/11/12
 * @author liuwei
 */
public class CommonUtil {

    /**
     * 生成UUID
     * @param source
     * @return
     */
    public static String getUUID(String source) {
        UUID uuid = null;
        try{
            uuid = Optional.ofNullable(source).isPresent() ? UUID.nameUUIDFromBytes(source.getBytes("UTF-8")) :
                    UUID.randomUUID();
        } catch (UnsupportedEncodingException e) {
            uuid = UUID.randomUUID();
        }
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }

}
