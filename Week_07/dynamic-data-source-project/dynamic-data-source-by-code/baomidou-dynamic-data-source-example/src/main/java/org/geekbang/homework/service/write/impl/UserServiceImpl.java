package org.geekbang.homework.service.write.impl;

import com.baomidou.dynamic.datasource.annotation.Master;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.mapper.UserMapper;
import org.geekbang.homework.service.write.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Master
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean updateById(User user) {
        return getBaseMapper().updateById(user) > 0;
    }

    @Override
    public boolean insertBatch(List<User> users) {
        return saveBatch(users);
    }
}
