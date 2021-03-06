package com.robby.app.commons.utils;

/**
 * Created @ 2020/7/18
 *
 * @author liuwei
 */

import com.robby.app.commons.pojo.ValidReport;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 辅助参数校验
 */
public class ValidUtil {
    static Validator valid = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验参数
     * @param target
     * @param <T>
     * @return
     */
    public static <T> ValidReport validReport(T target) {
        ValidReport report = ValidReport.builder().build();
        Set<ConstraintViolation<T>> sets = valid.validate(target, Default.class);
        if (sets != null && sets.size() > 0) {
            report.setHasErrors(true);
            report.setErrorMsg(sets.stream().map(m -> {return m.getMessage();}).collect(Collectors.toList()));
        }
        return report;
    }
}
