package com.github.jiahaowen.spring.assistant.component.cache.spring.web;

import com.github.jiahaowen.spring.assistant.component.cache.spring.entity.UserDO;
import com.github.jiahaowen.spring.assistant.component.cache.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;

    @RequestMapping("/{id}")
    public UserDO list(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping("/add")
    public UserDO add() {
        UserDO user = new UserDO();
        user.setName("name_" + System.currentTimeMillis());
        user.setPassword("11111");
        userService.register(user);
        return user;
    }

    @RequestMapping("/update/{id}")
    public void update(@PathVariable Long id) {
        UserDO user = new UserDO();
        user.setId(id);
        user.setName("name:" + id);
        userService.updateUser(user);
    }
}
