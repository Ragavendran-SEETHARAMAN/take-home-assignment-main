

package com.marionete.service.impl;

import com.marionete.main.SpringBootMain;
import com.marionete.model.AccountInfoDto;
import com.marionete.model.UserInfoDto;
import com.marionete.service.AccountService;
import com.marionete.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackageClasses= {SpringBootMain.class})
public class UserAccountInfoTest {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;


    @Test
    public void testAccountServiceNotNull() throws IOException {
        String token = RandomStringUtils.randomAlphabetic(20);
        AccountInfoDto response = accountService.getAccountDetails(token);
        assertNotNull(response);
    }

    @Test
    public void testAccountServiceNull() throws IOException {
        String token = "";
        AccountInfoDto response = accountService.getAccountDetails(token);
        assertNull(response);
    }
    @Test
    public void testUserServiceNotNull() throws IOException {
        String token = RandomStringUtils.randomAlphabetic(20);
        UserInfoDto response = userService.getUserDetails(token);
        assertNotNull(response);
    }

    @Test
    public void testUserServiceNull() throws IOException {
        String token = "";
        UserInfoDto response = userService.getUserDetails(token);
        assertNull(response);
    }


}


