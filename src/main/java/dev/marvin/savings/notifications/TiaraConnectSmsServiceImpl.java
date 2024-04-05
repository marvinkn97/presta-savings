package dev.marvin.savings.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TiaraConnectSmsServiceImpl extends SmsService {
    private final RestTemplate restTemplate;

    @Value(value = "${sms.gateway.endpoint}")
    private String endpoint;

    @Value(value = "${sms.gateway.apikey}")
    private String authorizationToken;


    @Override
    public String sendNotification(NotificationRequest notificationRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + authorizationToken);

        log.info("Request headers : {}", httpHeaders);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", notificationRequest.from());
        requestBody.put("to", notificationRequest.to());
        requestBody.put("message", notificationRequest.message());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

        log.info("Request body : {}", requestEntity);

        URI uri = null;
        try {
            uri = new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        ResponseEntity<SmsResponse> responseEntity  = restTemplate.postForEntity(uri, requestEntity, SmsResponse.class);
        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        SmsResponse smsResponse = responseEntity.getBody();

        if (statusCode == HttpStatus.OK) {
            // Successful response
            assert smsResponse != null;
            log.info("SMS sent successfully. Response: {}", smsResponse);
            return "SMS sent successfully. Response: {}" + smsResponse;
        } else {
            // Error response
            log.error("Failed to send sms");
            return "Failed to send SMS";

        }
    }
}
