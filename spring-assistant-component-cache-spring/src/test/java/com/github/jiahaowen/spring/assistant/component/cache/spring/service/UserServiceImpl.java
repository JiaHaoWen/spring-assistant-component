package com.github.jiahaowen.spring.assistant.component.cache.spring.service;

import com.github.jiahaowen.spring.assistant.component.cache.annotation.CacheDeleteTransactional;
import com.github.jiahaowen.spring.assistant.component.cache.spring.condition.UserCondition;
import com.github.jiahaowen.spring.assistant.component.cache.spring.entity.UserDO;
import com.github.jiahaowen.spring.assistant.component.cache.spring.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired private UserMapper userMapper;

    @Override
    public UserDO getUserById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public List<UserDO> listByCondition(UserCondition condition) {
        List<Long> ids = userMapper.listIdsByCondition(condition);
        List<UserDO> list = null;
        if (null != ids && ids.size() > 0) {
            list = new ArrayList<>(ids.size());
            UserDO userDO = null;
            for (Long id : ids) {
                userDO = userMapper.getUserById(id);
                if (null != userDO) {
                    list.add(userDO);
                }
            }
        }
        return list;
    }

    @Override
    @CacheDeleteTransactional
    @Transactional(rollbackFor = Throwable.class)
    public Long register(UserDO user) {
        Long userId = userMapper.getUserIdByName(user.getName());
        if (null != userId) {
            throw new RuntimeException("用户名已被占用！");
        }
        userMapper.addUser(user);
        return user.getId();
    }

    @Override
    public UserDO doLogin(String name, String password) {
        Long userId = userMapper.getUserIdByName(name);
        if (null == userId) {
            throw new RuntimeException("用户不存在！");
        }
        UserDO user = userMapper.getUserById(userId);
        if (null == user) {
            throw new RuntimeException("用户不存在！");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码不正确！");
        }
        return user;
    }

    @Override
    @CacheDeleteTransactional
    @Transactional(rollbackFor = Throwable.class)
    public void updateUser(UserDO user) {
        userMapper.updateUser(user);
    }

    @Override
    @CacheDeleteTransactional
    @Transactional(rollbackFor = Throwable.class)
    public void deleteUserById(Long userId) {
        userMapper.deleteUserById(userId);
    }
}
