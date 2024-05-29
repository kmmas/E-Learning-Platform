package com.example.demo.controllers;

import com.example.demo.config.CustomUserDetailsService;
import com.example.demo.config.TestSecurityConfig;
import com.example.demo.login.LoginController;
import com.example.demo.utilities.Helper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestSecurityConfig.class)
@WebMvcTest(LoginController.class)
public class MockLoginControllerTests {
    private final String path = "src/test/resources/loginTestFiles/";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Wrong password
     */
    @Test
    public void test1() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException(""));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(Helper.readFile(path + "test2.json")))
                .andExpect(status().is(401))
                .andExpect(content().string("the email or password is wrong"));
    }

    /**
     * Locked user
     */
    @Test
    public void test2() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new LockedException(""));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(Helper.readFile(path + "test2.json")))
                .andExpect(status().is(401))
                .andExpect(content().string("the account is locked, contact the admin"));
    }

    /**
     * Disabled user
     */
    @Test
    public void test3() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class))).thenThrow(new DisabledException(""));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(Helper.readFile(path + "test2.json")))
                .andExpect(status().is(401))
                .andExpect(content().string("the account is disabled, contact the admin"));
    }
}
