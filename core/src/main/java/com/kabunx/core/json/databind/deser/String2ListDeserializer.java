package com.kabunx.core.json.databind.deser;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.kabunx.core.json.annotation.SplitProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class String2ListDeserializer extends JsonDeserializer<List<Object>> implements ContextualDeserializer {
    /**
     * 数据类型
     */
    private String type;
    /**
     * 分隔符
     */
    private String delimiter;

    @Override
    public List<Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (delimiter == null) {
            return null;
        }
        String value = p.getValueAsString();
        String[] values = value.split(delimiter);
        return collect(values);
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        SplitProperty annotation = property.getAnnotation(SplitProperty.class);
        if (annotation != null) {
            return new String2ListDeserializer(annotation.type(), annotation.delimiter());
        }
        return new String2ListDeserializer();
    }

    private List<Object> collect(String[] values) {
        return Arrays.stream(values).map(this::map).collect(Collectors.toList());
    }

    private Object map(String value) {
        return "int".equals(type) ? Integer.parseInt(value) : value;
    }
}
