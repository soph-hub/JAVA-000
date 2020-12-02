package org.geekbang.homework.service.read;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import org.geekbang.homework.entity.User;

public interface UserReadService {
    User getById(Long id);

    List<User> getByIds(List<Long> ids);

    List<User> list(LambdaQueryWrapper<User> queryWrapper);
}
