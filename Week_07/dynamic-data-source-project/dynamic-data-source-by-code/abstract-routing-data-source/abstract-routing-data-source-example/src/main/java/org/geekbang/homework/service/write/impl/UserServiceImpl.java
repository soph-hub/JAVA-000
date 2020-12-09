package org.geekbang.homework.service.write.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import org.geekbang.homework.dynamic.datasource.annotation.Master;
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
        User record = getById(user.getId());
        Preconditions.checkNotNull(record);
        return getBaseMapper().updateById(user) > 0;
    }

    @Override
    public boolean insertBatch(List<User> users) {
        return saveBatch(users);
    }
}
