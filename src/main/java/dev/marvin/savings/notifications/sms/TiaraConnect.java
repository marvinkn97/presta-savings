package dev.marvin.savings.notifications.sms;

import org.springframework.stereotype.Service;

@Service
public class TiaraConnect implements SmsService{

    @Override
    public void sendSMS(String endPoint, String apikey, String from, String to, String message) {

    }
}
