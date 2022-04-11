package com.marionete.service;

import com.marionete.model.AccountInfoDto;
import com.marionete.model.UserInfoDto;
import com.marionete.security.JwtUtil;
import com.marionete.security.UserDetailsService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import services.*;
import services.LoginServiceGrpc.LoginServiceImplBase;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@GrpcService
public class LoginService extends LoginServiceImplBase {

    private static final Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    private String jwtToken;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        logger.info("Entered Login Service Implementation class");

        logger.info("Jwt Token is being generated");
        LoginResponse.Builder response = LoginResponse.newBuilder();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        jwtToken = jwtUtil.generateJwtToken(userDetails);
        logger.info("Generate Jwt Token: " + jwtToken);

        response.setToken(jwtToken);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

    @Override
    public void userAccount(Token request, StreamObserver<UserAccountResponse> responseObserver) {
        String token = request.getToken();
        logger.info("Passing {} to fetch Account Details", token);
        AccountResponse.Builder accountBuilder = null;
        try {
            logger.info("Executing Completable future Async call - Fetching AccountDetails");
            CompletableFuture<AccountInfoDto> accountDetailsFuture =
                    CompletableFuture.supplyAsync(() -> {
                        try {
                            return accountService.getAccountDetails(token);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });
            accountBuilder = AccountResponse.newBuilder();

            accountBuilder.setAccountNumber(accountDetailsFuture.get().getAccountNumber());
            logger.info("Received Account details");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        logger.info("Passing token to fetch UserDetails");
        UserResponse.Builder userBuilder = null;
        CompletableFuture<UserInfoDto> userInfoFuture = null;
        try {
            logger.info("Executing Completable future Async call - Fetch UserInfo");
            userInfoFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return userService.getUserDetails(token);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            });

            userBuilder = UserResponse.newBuilder();
            userBuilder.setName(userInfoFuture.get().getName());
            userBuilder.setSurname(userInfoFuture.get().getSurname());
            userBuilder.setSex(userInfoFuture.get().getSex());
            userBuilder.setAge(userInfoFuture.get().getAge());
            logger.info("Fetched User Details ");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        logger.info("Sending response back to the calling service");
        UserAccountResponse.Builder response = UserAccountResponse.newBuilder();
        response.setAccountInfo(accountBuilder);
        response.setUserInfo(userBuilder);

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();

    }

}
