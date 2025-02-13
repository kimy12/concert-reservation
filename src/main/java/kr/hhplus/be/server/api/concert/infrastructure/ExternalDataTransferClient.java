package kr.hhplus.be.server.api.concert.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ExternalDataTransferClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String EXTERNAL_API_URL = "https://external-platform.com/api/reservation/info";  // 가상 외부 API URL

    public void sendReservationData(long scheduleId, long userId) {
        Map<String, Long> requestBody = new HashMap<>();
        requestBody.put("scheduleId", scheduleId);
        requestBody.put("userId", userId);

        try {
            log.info("data transfer... : {}", requestBody);
            String response = restTemplate.postForObject(EXTERNAL_API_URL, requestBody, String.class);
            log.info("response Succeeded: {}", response);
        } catch (Exception e) {
            log.error("failed transfer... : {}", e.getMessage());
        }
    }
}
