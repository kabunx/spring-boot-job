package com.kabunx.app.mapstruct;

import com.kabunx.app.model.Role;
import com.kabunx.app.web.resource.RoleResource;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-18T18:00:07+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleResource map(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResource roleResource = new RoleResource();

        roleResource.setName( role.getName() );
        roleResource.setDescription( role.getDescription() );

        return roleResource;
    }
}
