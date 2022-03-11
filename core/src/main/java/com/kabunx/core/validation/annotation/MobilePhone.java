package com.kabunx.core.validation.annotation;

import com.kabunx.core.validation.validator.MobilePhoneValidator;
import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MobilePhoneValidator.class})
public @interface MobilePhone {

    @AliasFor("national")
    String value() default "CN";

    /**
     * 区域码，默认中国
     *
     * @return String
     */
    String national() default "CN";

    String message() default "手机号错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
