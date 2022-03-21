package com.kabunx.app.mapstruct;

import com.kabunx.app.model.User;
import com.kabunx.app.web.resource.UserResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "roles", target = "roles")
    })
    UserResource map(User user);
}
