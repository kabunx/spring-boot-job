package com.kabunx.app;

import com.kabunx.app.builder.UserBuilder;
import com.kabunx.app.model.User;
import com.kabunx.app.service.UserService;
import com.kabunx.core.exception.BizException;
import com.kabunx.core.pagination.LengthPagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = LaborerApplication.class)
public class BuilderTest {

    @Resource
    UserBuilder builder;

    @Resource
    UserService userService;

    @Test
    @DisplayName("第一个数据")
    public void testFirst() {
        User user = builder.select("id", "`name`", "email").filter(wrapper -> {
            wrapper.eq("email", "test01@kabunx.local");
        }).first();
        System.out.println(user);
        Assertions.assertNotNull(user);
    }

    @Test
    @DisplayName("分页")
    public void testPaginate() {
        LengthPagination<User> pagination = builder.filter(wrapper -> {
            wrapper.eq("email", "test01@kabunx.local");
        }).paginate(1, 10);
        Assertions.assertEquals(1, pagination.getTotal());
    }

    @Test
    @DisplayName("不唯一")
    public void testRelation() {
        List<User> users = userService.getUsers();
        Assertions.assertEquals(10, users.size());
    }
}
