package org.geekbang.homework.service.read.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.annotation.Slave;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.geekbang.homework.dynamic.datasource.constant.DataSourceConstants.*;
import org.geekbang.homework.entity.User;
import org.geekbang.homework.mapper.UserMapper;
import org.geekbang.homework.service.read.UserReadService;
import org.springframework.stereotype.Service;

@Slave
@Service
public class UserReadServiceImpl extends ServiceImpl<UserMapper, User> implements UserReadService {

    @Override
    @DS(DataSourceName.SLAVE_1)
    public User getById(Long id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public List<User> getByIds(List<Long> ids) {
        return getBaseMapper().selectBatchIds(ids);
    }

    @Override
    public List<User> list(LambdaQueryWrapper<User> queryWrapper) {
        return getBaseMapper().selectList(queryWrapper);
    }

}
