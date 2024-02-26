package dev.marvin.savings.notifications.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SmsResponse {
    public String cost;
    public String mnc;
    public String balance;
    public String msgId;
    public String to;
    public String mcc;
    public String desc;
    public String status;
    public String statusCode;
}
