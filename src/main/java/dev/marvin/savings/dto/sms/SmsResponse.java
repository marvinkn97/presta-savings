package dev.marvin.savings.dto.sms;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
