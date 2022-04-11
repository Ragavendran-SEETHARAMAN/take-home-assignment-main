package com.marionete.service;

import com.marionete.model.LoginRequestDto;
import com.marionete.model.UserAccountResponseDto;

public interface UserAccountService {
    public UserAccountResponseDto fetchUserAccount(LoginRequestDto request);
}
