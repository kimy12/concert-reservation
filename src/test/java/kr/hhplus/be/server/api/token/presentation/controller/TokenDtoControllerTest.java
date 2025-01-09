package kr.hhplus.be.server.api.token.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
class TokenDtoControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;


    @DisplayName("토큰을 발급받는다")
    @Test
    void getToken() throws Exception {
        // given
        TokenRequest.Request request = new TokenRequest.Request(1);

        // when // then
        mockMvc.perform(
                        post("/token/api/v1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").value("fcdba094-a7b2-4bb3-b646-eb143cb83ee0"))
                .andExpect(jsonPath("$.data.status").value("Pending"));
    }
}