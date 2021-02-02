package controller;


import com.art.dip.service.interfaces.AuthService;
import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={TestConfig.class})
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Mock
    private AuthService service;

    @Autowired
    private MockMvc mock;


    @Test
    public void start() {
        Assertions.assertNotNull(service);
        Assertions.assertNotNull(mock);
    }

    @Test
    public void testForEmptyToken() throws Exception {
        Mockito.when(service.getVerifyToken("token")).thenReturn(null);

        mock.perform(get("/registrationConfirm?token=token")).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/register")).
                andExpect(flash().attribute("message", "Your verification token is expired. Please, register again"));
    }

}
