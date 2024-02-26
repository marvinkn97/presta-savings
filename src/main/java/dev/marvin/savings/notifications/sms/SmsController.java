package dev.marvin.savings.notifications.sms;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/sms")
public class SmsController {

    private final TiaraConnectService tiaraConnectService;

    public SmsController(TiaraConnectService tiaraConnectService) {
        this.tiaraConnectService = tiaraConnectService;
    }

    @PostMapping
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest smsRequest){
        return ResponseEntity.ok(tiaraConnectService.sendSMS(smsRequest));
    }

}
