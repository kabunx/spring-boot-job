package com.kabunx.app.service.impl;

import com.kabunx.app.builder.UserBuilder;
import com.kabunx.app.model.User;
import com.kabunx.app.service.UserService;
import com.kabunx.core.pagination.LengthPagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserBuilder userBuilder;

    @Override
    public LengthPagination<User> paginate() {
        return userBuilder.paginate(1, 10);
    }

    public List<User> getUsers() {
        return userBuilder.filter(wrapper -> {
                    wrapper.in("id", Arrays.asList(1, 2));
                })
                .with("roles")
                .get();
    }
}
