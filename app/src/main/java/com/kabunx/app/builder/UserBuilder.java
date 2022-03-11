package com.kabunx.app.builder;

import com.kabunx.app.mapper.PermissionMapper;
import com.kabunx.app.mapper.RoleMapper;
import com.kabunx.app.mapper.UserMapper;
import com.kabunx.app.mapper.UserPasswordResetMapper;
import com.kabunx.app.model.Permission;
import com.kabunx.app.model.Role;
import com.kabunx.app.model.User;
import com.kabunx.data.mybatis.mapper.PlusMapper;
import com.kabunx.data.mybatis.query.Builder;
import com.kabunx.data.mybatis.relation.BelongsToMany;;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBuilder extends Builder<User, UserBuilder> {

    @Resource
    UserMapper userMapper;

    @Resource
    RoleMapper roleMapper;

    @Resource
    PermissionMapper permissionMapper;

    @Resource
    UserPasswordResetMapper userPasswordResetMapper;

    @Override
    public PlusMapper<User> getMapper() {
        return userMapper;
    }

    @Override
    public void defineAllRelations() {
        // 角色（多对多）
        this.belongsToMany("roles", (BelongsToMany<Role, User> relation) -> {
            relation.setRelatedMapper(roleMapper);
            relation.setRelatedArgs("rbac_user_role", "user_id", "role_id");
            relation.setLocalCollector(User::getId);
            relation.setRelatedGroupBy(Role::getPivotLocalId);
            relation.setMerge(User::setRoles);
        });
        // 权限（多对多）
        this.belongsToMany("permissions", (BelongsToMany<Permission, User> relation) -> {
            relation.setRelatedMapper(permissionMapper);
            relation.setRelatedArgs("rbac_user_permission", "user_id", "permission_id");
            relation.setLocalCollector(User::getId);
            relation.setRelatedGroupBy(Permission::getPivotLocalId);
            relation.setMerge(User::setPermissions);
        });
    }


    // 角色（多对多）
    public UserBuilder roles() {
        return this.with("roles");
    }

    // 权限（多对多）
    public UserBuilder permissions() {
        return this.with("permissions");
    }
}
