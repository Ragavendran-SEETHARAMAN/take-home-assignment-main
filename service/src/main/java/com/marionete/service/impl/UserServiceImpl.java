package com.marionete.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.model.UserInfoDto;
import com.marionete.service.UserService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Value("${userDetailsURL}")
    String userServiceurl;

    @Value("${request_retry_count}")
    private int request_RetryCount;

    @Value("${authorization_header}")
    private String request_Header;

    @Value("${request_retry_interval_milliseconds}")
    private long retry_Interval_ms;

    /**
     * Method to fetch UserDetails
     *
     * @param token
     * @return
     * @throws IOException
     */
    public UserInfoDto getUserDetails(String token) throws IOException {

        logger.info("Entered UserServiceImpl to fetch userDetails");
        logger.info(" Connecting User Service URL: " + userServiceurl);
        HttpGet request = new HttpGet(userServiceurl);
        ObjectMapper mapper = new ObjectMapper();
        UserInfoDto userDtoObject = null;
        // add request headers
        logger.info("Adding Token to the Request header");
        if (!token.isEmpty() && token != null) {
            request.addHeader(request_Header, token);
        } else {
            logger.info("Token is empty");
        }

        try (CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler()).setServiceUnavailableRetryStrategy(new ServiceUnavailableRetryStrategy() {
            @Override
            public boolean retryRequest(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
                int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
                if (executionCount < request_RetryCount && responseStatusCode != 200) {
                    return true;
                }
                return false;
            }

            @Override
            public long getRetryInterval() {
                return retry_Interval_ms;
            }
        }).build();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                String result = null;
                try {
                    result = EntityUtils.toString(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!result.isEmpty())
                    userDtoObject = mapper.readValue(result, UserInfoDto.class);
                logger.info("After fetching UserDetails" + result);

            } else {
                logger.info("Empty response");
            }
        }
        return userDtoObject;
    }
}
