package com.kabunx.app.web.request;

import com.kabunx.core.validation.annotation.Confirmed;
import com.kabunx.core.validation.annotation.MobilePhone;
import com.kabunx.core.web.request.QueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Confirmed(field = "password", confirmedField = "passwordConfirmation")
public class UserQuery extends QueryRequest {
    private String name;

    @MobilePhone
    private String phone;

    private String password;

    private String passwordConfirmation;
}
