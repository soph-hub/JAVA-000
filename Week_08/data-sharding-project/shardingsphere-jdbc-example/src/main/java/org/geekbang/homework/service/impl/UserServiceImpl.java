package org.geekbang.homework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.mapper.UserMapper;
import org.geekbang.homework.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
