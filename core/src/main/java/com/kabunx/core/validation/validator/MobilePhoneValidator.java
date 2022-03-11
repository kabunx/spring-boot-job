package com.kabunx.core.validation.validator;

import com.kabunx.core.validation.annotation.MobilePhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 仅验证中国地区的手机号，后续可以扩展
 */
public class MobilePhoneValidator implements ConstraintValidator<MobilePhone, String> {
    private String national;

    @Override
    public void initialize(MobilePhone constraintAnnotation) {
        national = constraintAnnotation.national();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 创建 Pattern 对象
        switch (national) {
            case "CN":

        }
        String cn = "1740[0-5]\\d{6}|1(?:[38]\\d|4[57]|5[0-35-9]|6[25-7]|7[0-35-8]|9[0135-9])\\d{8}";
        Pattern pattern = Pattern.compile(cn);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
