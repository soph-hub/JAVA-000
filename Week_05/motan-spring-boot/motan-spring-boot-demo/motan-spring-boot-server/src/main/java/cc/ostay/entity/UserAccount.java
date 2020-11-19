package cc.ostay.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserAccount {

    private Long id;

    private String username;

    private String password;
}
