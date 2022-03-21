package com.kabunx.core.json.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kabunx.core.constant.enums.SensitiveTypeEnum;
import com.kabunx.core.json.databind.ser.SensitiveSerializer;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerializer.class)
public @interface SensitiveProperty {

    @AliasFor("type")
    SensitiveTypeEnum value() default SensitiveTypeEnum.GENERAL;

    @AliasFor("value")
    SensitiveTypeEnum type() default SensitiveTypeEnum.GENERAL;
}
