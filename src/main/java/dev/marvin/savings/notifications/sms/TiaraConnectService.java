package dev.marvin.savings.notifications.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TiaraConnectService implements SmsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${sms.gateway.endpoint}")
    private String endpoint;
    @Value(value = "${sms.gateway.apikey}")
    private String authorizationToken;

    @Override
    public void sendSMS(String to, String message, String from) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authorizationToken);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", "PRESTA");
        requestBody.put("to", to);
        requestBody.put("message", message);
        requestBody.put("refId", "09wiwu088e"); // Assuming you want to include a reference ID

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, Map.class);

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        Map<String, Object> responseBody = responseEntity.getBody();

        if (statusCode == HttpStatus.OK) {
            // Successful response
            log.info("SMS sent successfully. Response: {}", responseBody);
        } else {
            // Error response
            log.error("Failed to send SMS. Status code: {}, Response: {}", statusCode, responseBody);
        }
    }
}
