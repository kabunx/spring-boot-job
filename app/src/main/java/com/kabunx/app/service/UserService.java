package com.kabunx.app.service;

import com.kabunx.app.model.User;
import com.kabunx.core.pagination.LengthPagination;

import java.util.List;

public interface UserService {
    LengthPagination<User> paginate();
    List<User> getUsers();
}
