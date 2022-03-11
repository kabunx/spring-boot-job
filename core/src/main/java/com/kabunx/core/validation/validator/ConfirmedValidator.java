package com.kabunx.core.validation.validator;

import com.kabunx.core.validation.annotation.Confirmed;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证字段必须具有匹配字段
 */
public class ConfirmedValidator implements ConstraintValidator<Confirmed, Object> {
    private String fieldName;
    private String confirmedFieldName;

    @Override
    public void initialize(Confirmed constraintAnnotation) {
        fieldName = constraintAnnotation.field();
        confirmedFieldName = constraintAnnotation.twoField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        Object firstObj = wrapper.getPropertyValue(fieldName);
        Object secondObj = wrapper.getPropertyValue(confirmedFieldName);
        return firstObj != null && firstObj.equals(secondObj);
    }
}
