package cc.ostay.motan;

import cc.ostay.convert.UserConvert;
import cc.ostay.dto.User;
import cc.ostay.entity.UserAccount;
import cc.ostay.entity.UserInfo;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserConvertTest {

    @Test
    public void testConvertToEntity() {
        User user = new User().setUserId(1L).setAccountId(2L).setUsername("admin").setRealName("管理员").setGender(1).setBirthday(LocalDate.now());

        UserInfo userInfo = UserConvert.INSTANCE.convertToUserInfo(user);

        System.out.println(userInfo);

        UserAccount userAccount = UserConvert.INSTANCE.convertToUserAccount(user);

        System.out.println(userAccount);
    }

    @Test
    public void testConvertToDto() {
        UserInfo userInfo = new UserInfo().setId(1L).setAccountId(2L).setRealName("管理员").setGender(1).setBirthday(LocalDate.now());

        UserAccount userAccount = new UserAccount().setId(2L).setUsername("admin");

        User user = UserConvert.INSTANCE.convert(userInfo,userAccount);

        System.out.println(user);
    }

    @Test
    public void testConvertToList() {
        Long start = System.currentTimeMillis();
        List<UserInfo> userInfos = Lists.newArrayList();

        for (int i = 0; i < 100000; i++) {
            UserInfo userInfo = new UserInfo()
                    .setId(101L + i)
                    .setAccountId(201L + i)
                    .setRealName("管理员" + (i + 1))
                    .setGender(1)
                    .setBirthday(LocalDate.now());
            userInfos.add(userInfo);
        }

        List<User> users = UserConvert.INSTANCE.convertToList(userInfos);

        System.out.println(users);

        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
    }
}
