package controller;

import com.art.dip.service.interfaces.AdminActionService;
import config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={TestConfig.class})
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class AdminControllerTest {
    @Autowired
    private MockMvc mock;

    @MockBean
    private AdminActionService service;

    @Test
    public void start() {
        Assertions.assertNotNull(service);
        Assertions.assertNotNull(mock);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void adminMenu() throws Exception {
        mock.perform(get("/admin/menu")).andExpect(status().isOk()).andExpect(content().string(containsString("Admin Menu")));

    }

    @Test
    @WithMockUser(username = "user")
    public void userAdminMenu() throws Exception {
        mock.perform(get("/admin/menu")).andExpect(status().is(403));
    }


}
