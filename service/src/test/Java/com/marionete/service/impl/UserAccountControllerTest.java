

package com.marionete.service.impl;

import com.marionete.controller.UserAccountController;
import com.marionete.main.SpringBootMain;
import com.marionete.model.AccountInfoDto;
import com.marionete.model.LoginRequestDto;
import com.marionete.model.UserAccountResponseDto;
import com.marionete.model.UserInfoDto;
import com.marionete.service.UserAccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringBootMain.class)
@WebMvcTest(value = UserAccountController.class)
public class UserAccountControllerTest {

    @InjectMocks
    UserAccountController userAccountController;

    private MockMvc mockMvc;

    @Mock
    UserAccountService userAccountService;

    String requestJson = "{\"username\":\"bla\",\"password\":\"foo\"}";

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build();
    }

    @Test
    public void testUserAccountController() throws Exception {
        UserAccountResponseDto userAccountResponseDto = new UserAccountResponseDto(new AccountInfoDto("12345-3346-3335-4456"),new UserInfoDto("John","Doe","male",32));
        Mockito.when(userAccountService.fetchUserAccount(Mockito.any(LoginRequestDto.class))).thenReturn(userAccountResponseDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/marionete/useraccount")
                .accept(MediaType.APPLICATION_JSON).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());

   }

}


