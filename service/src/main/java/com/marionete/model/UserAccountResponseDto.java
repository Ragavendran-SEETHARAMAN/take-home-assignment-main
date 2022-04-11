package com.marionete.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccountResponseDto {

    AccountInfoDto accountInfoDto;
    UserInfoDto userInfoDto;

}
