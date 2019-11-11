package com.robby.app.commons.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 基于Gson的JSON函数库
 * Created @ 2019/11/11
 * @author liuwei
 */
public class JsonUtil {

    static final Gson GSON = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:SS").create();

    /**
     * Object to JSON-String
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        checkNotNull(src, "The source object can not be null!");
        return GSON.toJson(src);
    }

    /**
     * JSON-String to Object
     * @param src
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String src) {
        checkNotNull(src, "The source json-string can not be null!");
        Type type = new TypeToken<T>(){}.getType();
        return GSON.fromJson(src, type);
    }

    /**
     * Object to JSON-Element
     * @param src
     * @return
     */
    public static JsonElement parseJson(Object src) {
        checkNotNull(src, "The source object can not be null!");

        String type = src.getClass().getSimpleName();
        if("List".equalsIgnoreCase(type) ||
                "Map".equalsIgnoreCase(type) ||
                "Set".equalsIgnoreCase(type) ||
                "HashSet".equalsIgnoreCase(type) ||
                "LinkedHashSet".equalsIgnoreCase(type) ||
                "ArrayList".equalsIgnoreCase(type)) {
            return new JsonParser().parse(toJson(src)).getAsJsonArray();
        } else {
            return new JsonParser().parse(toJson(src));
        }
    }

}
