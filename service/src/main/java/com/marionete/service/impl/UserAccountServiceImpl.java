package com.marionete.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.error.EmptyCredentialsException;
import com.marionete.error.InvalidCredentialsException;
import com.marionete.mapper.UserAccountMapper;
import com.marionete.model.AccountInfoDto;
import com.marionete.model.LoginRequestDto;
import com.marionete.model.UserAccountResponseDto;
import com.marionete.model.UserInfoDto;
import com.marionete.service.UserAccountService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import services.*;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LogManager.getLogger(UserAccountServiceImpl.class);

    @Value("${server_host}")
    private String localHost;

    @Value("${server_port}")
    private int port;

    /**
     * Method to fetch UserAccount details
     *
     * @param request
     * @return
     */
    public UserAccountResponseDto fetchUserAccount(LoginRequestDto request) {
        logger.info("Entered fetchUserAccount");

        String userName = request.getUsername();
        String password = request.getPassword();

        logger.info("Received Credentials for User " + userName);

        logger.info("Validating user credentials");
        ValidateCredentials(userName, password);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(localHost, port).usePlaintext().build();
        LoginServiceGrpc.LoginServiceBlockingStub stub = LoginServiceGrpc.newBlockingStub(channel);

        //Fetching String Token from Login Service
        LoginRequest loginrequest = LoginRequest.newBuilder().setUsername(userName).setPassword(password).build();
        LoginResponse loginResponse = stub.login(loginrequest);
        String token = loginResponse.getToken();

        //Fetching User Account response
        UserAccountResponse userAccountResponse = getUserAccountResponse(stub, token);

        String accountNumber = userAccountResponse.getAccountInfo().getAccountNumber();
        ObjectMapper mapper = new ObjectMapper();

        logger.info("Using Mapper to convert User and Account response into required Object");
        UserInfoDto userInfoDto = UserAccountMapper.MAPPER.userMapper(userAccountResponse.getUserInfo());
        AccountInfoDto accountDetails = UserAccountMapper.MAPPER.accountMapper(userAccountResponse.getAccountInfo());
        logger.info("Sending final response to Client");
        UserAccountResponseDto userAccountResponseDto = new UserAccountResponseDto(accountDetails, userInfoDto);

        return userAccountResponseDto;
    }

    private UserAccountResponse getUserAccountResponse(LoginServiceGrpc.LoginServiceBlockingStub stub, String token) {
        logger.info("fetching Account & User info by passing Jwt Token to the respective service");
        Token userToken = Token.newBuilder().setToken(token).build();
        UserAccountResponse userAccountResponse = stub.userAccount(userToken);
        return userAccountResponse;
    }


    /**
     * To Validate credentials
     *
     * @param userName
     * @param password
     */
    private void ValidateCredentials(String userName, String password) {
        if (userName.isEmpty() || password.isEmpty()) {
            throw new EmptyCredentialsException("Username or Password should not be empty");
        } else if (!userName.equals("bla") || !password.equals("foo")) {
            logger.info("Invalid  Credentials");
            throw new InvalidCredentialsException("Invalid UserName or Password");
        } else {
            logger.info("Valid  Credentials");
        }
    }

}
