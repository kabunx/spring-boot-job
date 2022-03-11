package com.kabunx.app.web.controller;

import com.kabunx.app.event.MyEvent;
import com.kabunx.app.model.User;
import com.kabunx.app.service.UserService;
import com.kabunx.app.web.request.UserRequest;
import com.kabunx.app.web.resource.UserResource;
import com.kabunx.core.web.JsonResponse;
import com.kabunx.core.web.annotation.GetParam;
import com.kabunx.core.web.controller.ApiController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends ApiController {

    @Resource
    UserService userService;

    @Resource
    ApplicationEventPublisher eventPublisher;

    @GetMapping()
    public JsonResponse<List<UserResource>> index(@GetParam UserRequest userQuery) {
        List<User> users = userService.getUsers();
        eventPublisher.publishEvent(new MyEvent("xxx"));
        return toCollection(users, UserResource.class);
    }
}
