package cc.ostay.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class User implements Serializable {

    private Long userId;

    private Long accountId;

    private String username;

    private String realName;

    private Integer gender;

    private LocalDate birthday;
}
