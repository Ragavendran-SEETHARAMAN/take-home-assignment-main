package com.marionete.mapper;

import com.marionete.model.AccountInfoDto;
import com.marionete.model.UserInfoDto;
import org.mapstruct.factory.Mappers;
import services.AccountResponse;
import services.UserResponse;

@org.mapstruct.Mapper
public abstract class UserAccountMapper {

    public static final UserAccountMapper MAPPER =
            Mappers.getMapper(UserAccountMapper.class);

        public abstract UserInfoDto userMapper(UserResponse userResponse);
        public abstract AccountInfoDto accountMapper(AccountResponse accountResponse);
}
