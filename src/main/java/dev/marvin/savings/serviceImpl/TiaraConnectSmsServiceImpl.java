package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.dto.SmsRequest;
import dev.marvin.savings.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TiaraConnectSmsServiceImpl implements SmsService {
    private final RestTemplateBuilder restTemplateBuilder;

    @Value(value = "${sms.gateway.endpoint}")
    String endpoint;

    @Value(value = "${sms.gateway.apikey}")
    String authorizationToken;


    @Override
    public void sendSMS(SmsRequest smsRequest) {
        log.info("Inside sendSms method of TiaraConnectSmsServiceImpl");
        try {
            RestTemplate restTemplate = restTemplateBuilder
                    .setConnectTimeout(Duration.ofSeconds(10))
                    .setReadTimeout(Duration.ofSeconds(10))
                    .build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Authorization", "Bearer " + authorizationToken);

            RequestEntity<SmsRequest> requestEntity = RequestEntity.post(endpoint).headers(httpHeaders).body(smsRequest);
            log.info("Request body : {}", requestEntity);

            ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);
            log.debug("Response: {}", response.getBody());
        } catch (Exception e) {
            log.error(dev.marvin.constants.MessageConstants.UNEXPECTED_ERROR, e.getMessage(), e);
        }

    }
}
