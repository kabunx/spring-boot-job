package com.kabunx.app.web.resource;

import com.kabunx.core.constant.enums.SensitiveTypeEnum;
import com.kabunx.core.json.annotation.SensitiveProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserResource {

    private Integer id;

    private String name;

    private String mobile;

    private String email;

    private List<RoleResource> roles;
}
