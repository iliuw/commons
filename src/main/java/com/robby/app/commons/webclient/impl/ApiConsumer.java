package com.robby.app.commons.webclient.impl;

import com.google.gson.JsonElement;
import com.robby.app.commons.properties.feign.FeignParameteres;
import com.robby.app.commons.webclient.ApiClient;
import com.robby.app.commons.webclient.logger.SimpleLogger;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
@Data
@ToString
@NoArgsConstructor
public class ApiConsumer {

    FeignParameteres parameteres;
    Logger.Level level = Logger.Level.FULL;
    Logger logger = new SimpleLogger();

    public JsonElement call() throws IllegalAccessException {
        ApiClient client = client();
        JsonElement result = null;

        switch(parameteres.getMethod()) {
            case GET:
                result = client.get(parameteres.getUri());
                break;
            case POST:
                Map<String, Object> reqs = parameteres.getRequests();
                if(reqs != null && !reqs.isEmpty()) {
                    result = client.post(parameteres.getUri(), reqs, parameteres.getBody());
                } else {
                    result = client.post(parameteres.getUri(), parameteres.getBody());
                }
                break;
            case PUT:
                result = client.put(parameteres.getUri(), parameteres.getBody());
                break;
            case DELETE:
                result = client.delete(parameteres.getUri(), parameteres.getBody());
                break;
            default:
                throw new IllegalAccessException("非法的远程接口调用！无效的请求方法: " + parameteres.getMethod());
        }
        return result;
    }

    private ApiClient client() {
        return Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .logLevel(level)
                .logger(logger)
                .options(new Request.Options(60000, 300000))
                .target(ApiClient.class, parameteres.getDomain());
    }

}
