package com.robby.app.commons.annotations;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.reflect.TypeToken;
import com.robby.app.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 重复数据检查
 * Created @ 2020/7/11
 * @author liuwei
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {isRepet.Validator.class})
@Documented
public @interface isRepet {

    String message() default "数据重复了";
    String[] params();
    Class<? extends Object> target();
    Class<? extends Model> entity();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class Validator implements ConstraintValidator<isRepet, Object> {
        String[] params;
        Class target;
        Class entity;

        @Override
        public void initialize(isRepet constraintAnnotation) {
            this.params = constraintAnnotation.params();
            this.target = constraintAnnotation.target();
            this.entity = constraintAnnotation.entity();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            try {
                return valid(value);
            } catch (NoSuchMethodException e) {
                log.error("= [Validate repetition] ==+> Cannot found Getter method\n", e);
                return false;
            } catch (IllegalAccessException e) {
                log.error("= [Validate repetition] ==+> Illegal access\n", e);
                return false;
            } catch (InvocationTargetException e) {
                log.error("= [Validate repetition] ==+> Invocation target class\n", e);
                return false;
            } catch (InstantiationException e) {
                log.error("= [Validate repetition] ==+> Instantiation\n", e);
                return false;
            }
        }

        private boolean valid(Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException,IllegalArgumentException {
            if(!Optional.ofNullable(obj).isPresent()) {
                return true;
            }
            List<Field> attrs = Arrays.asList(target.getDeclaredFields());
            QueryWrapper w = new QueryWrapper();
            List<String> args = Arrays.asList(params);
            int nulls = 0;
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            for(Field field: attrs) {
                String fieldName = field.getName();
                log.debug("= [Validate repetition] ==+> Validate: {}", fieldName);
                if(args.indexOf(fieldName) >= 0) {
                    // 在指定需验证的属性中
                    String methodName = String.format("get%s%s", fieldName.substring(0,1).toUpperCase(), fieldName.substring(1));
                    Map<String, Object> vo = JsonUtil.fromJson(JsonUtil.toJson(obj), type);
                    if(vo.containsKey(fieldName) && Optional.ofNullable(fieldName).isPresent()) {
                        Method method = entity.getMethod(methodName, new Class[0]);
                        w.eq(fieldName.replaceAll("([A-Z])", "_$1").toLowerCase(), vo.get(fieldName));
                    } else {
                        nulls += 1;
                    }
                }
            }
            if(nulls == args.size()) {
                // 没有对应的待验证属性
                return true;
            } else {
                Model model = (Model) entity.getConstructor(new Class[0]).newInstance(new Object[0]);
                return model.selectCount(w) <= 0;
            }
        }

    }

}
