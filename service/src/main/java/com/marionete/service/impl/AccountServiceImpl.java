package com.marionete.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.model.AccountInfoDto;
import com.marionete.service.AccountService;
import lombok.Data;
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
@Data
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    @Value("${accountDetailsURL}")
    private String accountServiceUrl;

    @Value("${request_retry_count}")
    private int request_RetryCount;

    @Value("${authorization_header}")
    private String request_Header;

    @Value("${request_retry_interval_milliseconds}")
    private long retry_Interval_ms;

    /**
     * Method to fetchAccount Details
     *
     * @param token
     * @return
     * @throws IOException
     */
    public AccountInfoDto getAccountDetails(String token) throws IOException {
        logger.info("Entered AccountServiceImpl to fetchAccountDetails from AccountInfoMock.Start()");
        logger.info(" Connecting Account Service URL: " + accountServiceUrl);
        HttpGet request = new HttpGet(accountServiceUrl);
        ObjectMapper accountDetailsMapper = new ObjectMapper();
        AccountInfoDto accountInfoDto = null;

        logger.info("Adding Token to the Request header");
        if (!token.isEmpty() && token != null) {
            request.addHeader(request_Header, token);
        } else {
            logger.info("Token is empty");
        }
        logger.info("Method having mechanism to retry if any failure to invoke the AccountInfoMock.Start()");
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

            logger.info("Response Status code: " + response.getStatusLine().getStatusCode());   // 200
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = null;
                try {
                    result = EntityUtils.toString(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!result.isEmpty())
                    accountInfoDto = accountDetailsMapper.readValue(result, AccountInfoDto.class);
                logger.info("After fetching Account Details");
            }

        }

        return accountInfoDto;
    }

}
