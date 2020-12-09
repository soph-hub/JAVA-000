package org.geekbang.homework.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {
    @TableId
    private Long userId;
    private String userName;
    private String userNamePlain;
    private String pwd;
    private String assistedQueryPwd;
}
