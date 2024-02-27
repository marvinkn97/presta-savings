package dev.marvin.savings.notifications.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CallbackRequest {
    public String msgId;
    public String from;
    @JsonProperty("to")
    public String myto;
    public String refId;
    public String status;
    public String statusReason;
    public String deliveryTime;
}
