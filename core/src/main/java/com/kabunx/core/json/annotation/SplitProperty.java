package com.kabunx.core.json.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kabunx.core.json.databind.deser.String2ListDeserializer;
import com.kabunx.core.json.databind.ser.List2StringSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = List2StringSerializer.class)
@JsonDeserialize(using = String2ListDeserializer.class)
public @interface SplitProperty {

    String type() default "";

    String delimiter() default ",";
}
