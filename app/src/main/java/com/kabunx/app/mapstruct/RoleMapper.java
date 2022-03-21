package com.kabunx.app.mapstruct;

import com.kabunx.app.model.Role;
import com.kabunx.app.web.resource.RoleResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

    RoleResource map(Role role);
}
