package dev.marvin.savings.service;

import dev.marvin.savings.dto.SmsRequest;

public interface SmsService{
    void sendSMS(SmsRequest smsRequest);

}
