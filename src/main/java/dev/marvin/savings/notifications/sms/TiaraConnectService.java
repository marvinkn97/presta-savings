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
    public String sendSMS(SmsRequest smsRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization:", "Bearer " + authorizationToken);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", smsRequest.from());
        requestBody.put("to", smsRequest.to());
        requestBody.put("message", smsRequest.message());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<SmsResponse> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, SmsResponse.class);

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        SmsResponse smsResponse = responseEntity.getBody();

        if (statusCode == HttpStatus.OK) {
            // Successful response
            log.info("SMS sent successfully. Response: {}", smsResponse);
            return "SMS sent successfully. Response: {}" + smsResponse;
        } else {
            // Error response
            log.error("Failed to send SMS. Status code: [%s], Response: [%s]".formatted(statusCode, smsResponse));
            return "Failed to send SMS. Status code: [%s], Response: [%s]".formatted(statusCode,  smsResponse);

        }
    }
}
