package cc.ostay.convert;

import cc.ostay.dto.User;
import cc.ostay.entity.UserAccount;
import cc.ostay.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mappings({
            @Mapping(source = "userInfo.id",target = "userId"),
            @Mapping(source = "userAccount.id",target = "accountId"),
            @Mapping(source = "userAccount.username",target = "username"),
            @Mapping(source = "userInfo.realName",target = "realName"),
            @Mapping(source = "userInfo.gender",target = "gender"),
            @Mapping(source = "userInfo.birthday",target = "birthday")
    })
    User convert(UserInfo userInfo, UserAccount userAccount);

    @Mappings({
    })
    List<User> convertToList(List<UserInfo> userInfos);

    @Mappings({
            @Mapping(source = "user.userId",target = "id"),
            @Mapping(source = "user.realName",target = "realName"),
            @Mapping(source = "user.gender",target = "gender"),
            @Mapping(source = "user.birthday",target = "birthday")
    })
    UserInfo convertToUserInfo(User user);

    @Mappings({
            @Mapping(source = "user.accountId",target = "id"),
            @Mapping(source = "user.username",target = "username")
    })
    UserAccount convertToUserAccount(User user);
}
