package com.marionete.service;

import com.marionete.model.AccountInfoDto;
import org.springframework.stereotype.Service;

import java.io.IOException;


public interface AccountService  {
    public AccountInfoDto getAccountDetails(String token) throws IOException;
}
