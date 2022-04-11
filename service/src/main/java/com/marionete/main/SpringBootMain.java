package com.marionete.main;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.error.CustomExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@ComponentScan(basePackages = "com.marionete")
@SpringBootApplication
public class SpringBootMain {

    private static final Logger logger = LogManager.getLogger(SpringBootMain.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }

    @PostConstruct
    public void init(){
        logger.info("Starting  - AccountInfo mock & userInfoMock server " +
                "   and it will be invoked only once (immediately after the bean's initialization)");
        AccountInfoMock.start();
        UserInfoMock.start();
    }



}
