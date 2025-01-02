package kr.hhplus.be.server.api.token.presentation.controller;

import kr.hhplus.be.server.api.common.response.TokenIssueResponse;
import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import kr.hhplus.be.server.api.token.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping("/api/v1")
    TokenIssueResponse<TokenResponse.Response> getToken(@RequestBody TokenRequest.Request request){
        TokenResponse.Response response = new TokenResponse.Response("fcdba094-a7b2-4bb3-b646-eb143cb83ee0", "Pending");
        return TokenIssueResponse.ok(response);
    }
}
