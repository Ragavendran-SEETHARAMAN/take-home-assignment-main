package com.marionete.service;

import com.marionete.model.UserInfoDto;

import java.io.IOException;

public interface UserService {
    public UserInfoDto getUserDetails(String token) throws IOException;

}
