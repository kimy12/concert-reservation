package kr.hhplus.be.server.api.token.presentation.controller;

import kr.hhplus.be.server.api.common.response.TokenIssueResponse;
import kr.hhplus.be.server.api.token.domain.service.TokenService;
import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import kr.hhplus.be.server.api.token.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController implements SwaggerApi{

    private final TokenService tokenService;

    @Override
    @PostMapping("/api/v1")
    public TokenIssueResponse<TokenResponse.Response> getToken(@RequestBody TokenRequest.Request request){
        UUID tokenUUID = tokenService.saveTokenInfo(request.userId());
        TokenResponse.Response response = new TokenResponse.Response(tokenUUID.toString(), "Pending");
        return TokenIssueResponse.ok(response);
    }
}
