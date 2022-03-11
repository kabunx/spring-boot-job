package com.kabunx.app.mapstruct;

import com.kabunx.app.model.Role;
import com.kabunx.app.web.resource.RoleResource;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleResource map(Role role);
}
