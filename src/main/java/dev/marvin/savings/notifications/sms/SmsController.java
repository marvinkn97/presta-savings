package dev.marvin.savings.notifications.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/delivery-report")
public class SmsController {

    @GetMapping
    public ResponseEntity<CallbackRequest> incomingMessage(@RequestBody CallbackRequest callbackRequest){
        log.info(callbackRequest.toString());
        return ResponseEntity.ok(callbackRequest);
    }

}
