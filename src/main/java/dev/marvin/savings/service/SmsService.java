package dev.marvin.savings.service;

public interface SmsService{
    String sendSMS(String from, String to, String message);

}
