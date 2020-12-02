package org.geekbang.homework.service.write;

import java.util.List;
import org.geekbang.homework.entity.User;

public interface UserService {

    boolean insertBatch(List<User> users);

    boolean updateById(User user);

}
