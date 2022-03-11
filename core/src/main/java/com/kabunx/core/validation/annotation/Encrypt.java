package com.kabunx.core.validation.annotation;

import com.kabunx.core.constant.enums.EncryptEnum;
import com.kabunx.core.validation.validator.EncryptValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EncryptValidator.class})
public @interface Encrypt {
    EncryptEnum value() default EncryptEnum.EMPTY;

    String key() default "";

    String message() default "非法参数";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
