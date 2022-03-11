package com.kabunx.core.validation.validator;

import com.kabunx.core.constant.GlobalConstant;
import com.kabunx.core.constant.enums.EncryptEnum;
import com.kabunx.core.entity.EncryptEntity;
import com.kabunx.core.util.EncryptUtils;
import com.kabunx.core.validation.annotation.Encrypt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EncryptValidator implements ConstraintValidator<Encrypt, String> {
    private Encrypt encrypt;
    private EncryptEnum encryptEnum;

    @Override
    public void initialize(Encrypt annotation) {
        this.encrypt = annotation;
        this.encryptEnum = annotation.value();
    }

    /**
     * 1、解密数据
     * 2、获取参数和时间戳
     * 3、比对数据
     *
     * @param value   数据
     * @param context 上下文
     * @return 是否通过验证
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        EncryptEntity entity = decrypt2Entity(value);
        return entity != null && entity.getType().equals(encryptEnum.getType());
    }

    private String getEncryptKey() {
        return encrypt.key().isEmpty() ? "kabunx" : encrypt.key();
    }

    private EncryptEntity decrypt2Entity(String value) {
        String str = EncryptUtils.decrypt(value, getEncryptKey());
        if (str != null) {
            String[] pieces = str.split(GlobalConstant.STR_SPLIT_REGEX);
            if (pieces.length == 2) {
                return new EncryptEntity(pieces[0], Long.parseLong(pieces[1]));
            }
        }
        return null;
    }
}
