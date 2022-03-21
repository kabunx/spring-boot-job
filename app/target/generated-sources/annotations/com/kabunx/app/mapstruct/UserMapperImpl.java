package com.kabunx.app.mapstruct;

import com.kabunx.app.model.Role;
import com.kabunx.app.model.User;
import com.kabunx.app.web.resource.RoleResource;
import com.kabunx.app.web.resource.UserResource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-18T18:00:06+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_161 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    private final RoleMapper roleMapper = Mappers.getMapper( RoleMapper.class );

    @Override
    public UserResource map(User user) {
        if ( user == null ) {
            return null;
        }

        UserResource userResource = new UserResource();

        userResource.setName( user.getName() );
        userResource.setRoles( roleListToRoleResourceList( user.getRoles() ) );
        if ( user.getId() != null ) {
            userResource.setId( user.getId().intValue() );
        }
        userResource.setMobile( user.getMobile() );
        userResource.setEmail( user.getEmail() );

        return userResource;
    }

    protected List<RoleResource> roleListToRoleResourceList(List<Role> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleResource> list1 = new ArrayList<RoleResource>( list.size() );
        for ( Role role : list ) {
            list1.add( roleMapper.map( role ) );
        }

        return list1;
    }
}
