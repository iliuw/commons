package com.robby.app.commons.utils;

import com.robby.app.commons.properties.docs.DocProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Swagger初始化方法封装
 * Created @ 2019/11/11
 * @author liuwei
 */
public class DocUtil {

    public static UiConfiguration getUiConfiguration() {
        return new UiConfiguration(
                false,
                true,
                1,
                1,
                ModelRendering.MODEL,
                false,
                DocExpansion.LIST,
                false,
                null,
                OperationsSorter.ALPHA,
                true,
                TagsSorter.ALPHA,
                null
        );
    }

    public static Docket genDocket(DocProperties prop, List<Parameter> headers) {
        Contact contact = new Contact(prop.getAuthor(), prop.getDomain(), prop.getEmail());
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(prop.getTitle())
                .description(prop.getDesc())
                .contact(contact)
                .version(prop.getVersion())
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(prop.getBasePackage()))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo)
                .pathMapping(prop.getPathMapper());
        if(headers != null && !headers.isEmpty()) {
            docket.globalOperationParameters(headers);
        }
        return docket;
    }

    public static List<Parameter> genHeader(Map<String, Object> header) {
        List<Parameter> result = new ArrayList<>();
        ParameterBuilder builder = new ParameterBuilder();

        // 设置编码格式
        builder.name("Content-Type")
                .description("Set content type to application/json")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .defaultValue("application/json;charset=UTF-8");
        result.add(builder.build());

        // 设置http-header
        header.keySet().stream().forEach(key -> {
            Object val = header.get(key);
            String type = val.getClass().getTypeName().toLowerCase();
            String headVal = !"string".equals(type) ? JsonUtil.toJson(val) : (String)val;
            builder.name(key)
                    .description(key)
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(false)
                    .defaultValue(headVal);
            result.add(builder.build());
        });

        return result;
    }

}
