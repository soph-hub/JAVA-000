package cc.ostay.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserInfo {

    private Long id;

    private Long accountId;

    private String realName;

    private Integer gender;

    private LocalDate birthday;
}
