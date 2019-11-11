package com.robby.app.commons.webclient;

import com.google.gson.JsonElement;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
@Headers("Content-Type: application/json;charset=utf-8")
public interface ApiClient {

    @RequestLine("GET /{url}")
    JsonElement get(@Param("url") String url);

    @RequestLine("POST /{url}")
    @Headers("Accept: application/json")
    JsonElement post(@Param("url") String url, Object body);

    @RequestLine("POST /{url}")
    @Headers("Accept: application/json")
    JsonElement post(@Param("url") String url, @QueryMap Map<String, Object> requestParameters, Object body);

    @RequestLine("PUT /{url}")
    @Headers("Accept: application/json")
    JsonElement put(@Param("url") String url, Object body);

    @RequestLine("DELETE /{url}")
    @Headers("Accept: application/json")
    JsonElement delete(@Param("url") String url, Object body);

}
