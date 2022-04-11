package com.marionete.controller;

import com.marionete.model.LoginRequestDto;
import com.marionete.model.UserAccountResponseDto;
import com.marionete.service.UserAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    private static final Logger logger = LogManager.getLogger(UserAccountController.class);

    /**  fetching user account details by passing the username & password
     *
     * @param loginRequestDto
     * @return
     */
    @PostMapping(value = "/marionete/useraccount")
    public ResponseEntity<UserAccountResponseDto> fetchUserAccountDetails(@RequestBody @Valid LoginRequestDto loginRequestDto) throws Exception {
        logger.info("Entered fetchUserAccountDetails");
        UserAccountResponseDto userAccountResponseDto = userAccountService.fetchUserAccount(loginRequestDto);
        return new ResponseEntity<>(userAccountResponseDto,HttpStatus.ACCEPTED);
    }

}
