package com.kabunx.core.json.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.kabunx.core.json.annotation.SplitProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 将数组对象序列化为字符串
 */
@NoArgsConstructor
@AllArgsConstructor
public class List2StringSerializer extends JsonSerializer<List<Object>> implements ContextualSerializer {
    private String delimiter;

    @Override
    public void serialize(List<Object> values, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String str = list2String(values);
        gen.writeString(str);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        // 为空直接跳过
        if (property != null) {
            // 非 String 类直接跳过
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                SplitProperty annotation = property.getAnnotation(SplitProperty.class);
                if (annotation == null) {
                    annotation = property.getContextAnnotation(SplitProperty.class);
                }
                if (annotation != null) {
                    return new List2StringSerializer(annotation.delimiter());
                }
            }
            return provider.findValueSerializer(property.getType(), property);
        }
        return provider.findNullValueSerializer(null);
    }

    private String list2String(List<Object> values) {
        return values.stream().map(String::valueOf).collect(Collectors.joining(delimiter));
    }
}
