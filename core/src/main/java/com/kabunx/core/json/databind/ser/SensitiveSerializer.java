package com.kabunx.core.json.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.kabunx.core.constant.enums.SensitiveTypeEnum;
import com.kabunx.core.json.annotation.SensitiveProperty;
import com.kabunx.core.util.SensitiveUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 属性脱敏序列化
 */
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveSerializer extends JsonSerializer<String> implements ContextualSerializer {
    /**
     * 脱敏类型
     */
    private SensitiveTypeEnum type;

    @Override
    public void serialize(String str, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (type) {
            case CHINESE_NAME:
                jsonGenerator.writeString(SensitiveUtils.chineseName(str));
                break;
            case FIXED_PHONE:
                jsonGenerator.writeString(SensitiveUtils.fixedPhone(str));
                break;
            case MOBILE_PHONE:
                jsonGenerator.writeString(SensitiveUtils.mobilePhone(str));
                break;
            case ADDRESS:
                jsonGenerator.writeString(SensitiveUtils.address(str, 4));
                break;
            case EMAIL:
                jsonGenerator.writeString(SensitiveUtils.email(str));
                break;
            case ID_CARD:
                jsonGenerator.writeString(SensitiveUtils.idCardNum(str));
                break;
            case BANK_CARD:
                jsonGenerator.writeString(SensitiveUtils.bankCard(str));
                break;
            default:
                jsonGenerator.writeString("******");
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        // 为空直接跳过
        if (property != null) {
            // 非 String 类直接跳过
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                SensitiveProperty annotation = property.getAnnotation(SensitiveProperty.class);
                if (annotation == null) {
                    annotation = property.getContextAnnotation(SensitiveProperty.class);
                }
                if (annotation != null) {
                    // 如果能得到注解，就将注解的 value 传入 SensitiveSerializer
                    return new SensitiveSerializer(annotation.value());
                }
            }
            return provider.findValueSerializer(property.getType(), property);
        }
        return provider.findNullValueSerializer(null);
    }
}
