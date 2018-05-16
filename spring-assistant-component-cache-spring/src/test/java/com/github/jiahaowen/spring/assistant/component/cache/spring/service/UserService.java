package com.github.jiahaowen.spring.assistant.component.cache.spring.service;

import com.github.jiahaowen.spring.assistant.component.cache.spring.condition.UserCondition;
import com.github.jiahaowen.spring.assistant.component.cache.spring.entity.UserDO;
import java.util.List;

public interface UserService {

    UserDO getUserById(Long userId);

    List<UserDO> listByCondition(UserCondition condition);

    Long register(UserDO user);

    UserDO doLogin(String name, String password);

    void updateUser(UserDO user);

    void deleteUserById(Long userId);
}
