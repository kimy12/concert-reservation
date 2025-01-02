package kr.hhplus.be.server.api.docs;

import kr.hhplus.be.server.RestDocsSupport;
import kr.hhplus.be.server.api.token.presentation.controller.TokenController;
import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TokenControllerDocsTest extends RestDocsSupport {
    @Override
    protected Object initController() {
        return new TokenController();
    }

    @DisplayName("토큰을 생성하는 API")
    @Test
    void createToken() throws Exception {
        TokenRequest.Request request = new TokenRequest.Request(1);

        mockMvc.perform(
                        post("/token/api/v1")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token").value("fcdba094-a7b2-4bb3-b646-eb143cb83ee0"))
                .andExpect(jsonPath("$.data.status").value("Pending"))
                .andDo(document("token-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER)
                                        .description("유저 아이디")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("응답 메시지"),
                                fieldWithPath("data.token").type(JsonFieldType.STRING)
                                        .description("토큰 uuid"),
                                fieldWithPath("data.status").type(JsonFieldType.STRING)
                                        .description("토큰 상태")
                        )
                ));
    }
}
