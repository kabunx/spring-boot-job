package com.kabunx.app;

import com.kabunx.app.mapstruct.UserMapper;
import com.kabunx.app.model.Role;
import com.kabunx.app.model.User;
import com.kabunx.app.web.resource.UserResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LaborerApplication.class)
@Slf4j
public class MapperTest {

    @Test
    void testUserMapper() {
        User user = new User();
        user.setAccount("123");
        user.setName("测试");
        user.setMobile("18912690699");
        Role role1 = new Role();
        role1.setName("角色1");
        Role role2 = new Role();
        role2.setName("角色2");
        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        user.setRoles(roles);
        log.info("{}", user);
        UserResource resource = UserMapper.MAPPER.map(user);
        System.out.println(resource);
    }
}
