package kr.hhplus.be.server.api.token.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import kr.hhplus.be.server.api.common.interceptor.TokenInterceptor;
import kr.hhplus.be.server.api.token.domain.service.TokenService;
import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
class TokenControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected TokenService tokenService;

    @MockitoBean
    private TokenInterceptor tokenInterceptor;


    @DisplayName("토큰을 발급받는다")
    @Test
    void getToken() throws Exception {
        // given
        TokenRequest.Request request = new TokenRequest.Request(1);

        UUID tokenUUID = UUID.fromString("fcdba094-a7b2-4bb3-b646-eb143cb83ee0");

        when(tokenService.saveTokenInfo(request.userId(), LocalDateTime.now())).thenReturn(tokenUUID);

        // when // then
        mockMvc.perform(
                        post("/token/api/v1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value(tokenUUID.toString()))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }
}