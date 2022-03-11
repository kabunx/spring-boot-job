package com.kabunx.core.validation.annotation;

import com.kabunx.core.validation.validator.ConfirmedValidator;
import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * field_confirmation
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConfirmedValidator.class})
@Documented
public @interface Confirmed {

    /**
     * 需要验证的第一字段的字段名"{field}"
     * 需要验证的第二字段的字段名"{field}_confirmation"
     **/

    String field();

    String twoField();

    String message() default "两次数据不一致";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Confirmed[] value();
    }
}
