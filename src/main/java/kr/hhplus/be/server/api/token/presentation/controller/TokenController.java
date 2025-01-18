package kr.hhplus.be.server.api.token.presentation.controller;

import kr.hhplus.be.server.api.token.domain.service.TokenService;
import kr.hhplus.be.server.api.token.presentation.dto.TokenRequest;
import kr.hhplus.be.server.api.token.presentation.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static kr.hhplus.be.server.api.token.domain.enums.TokenStatus.PENDING;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController implements SwaggerApi{

    private final TokenService tokenService;

    @Override
    @PostMapping("/api/v1")
    public ResponseEntity<TokenResponse.Response> getToken(@RequestBody TokenRequest.Request request){
        UUID tokenUUID = tokenService.saveTokenInfo(request.userId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("USER-TOKEN", tokenUUID.toString());
        TokenResponse.Response response = new TokenResponse.Response(tokenUUID.toString(), PENDING);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }
}
