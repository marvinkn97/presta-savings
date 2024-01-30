package dev.marvin.savings.savingsaccount.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record SavingsAccountResponse(
        @JsonProperty(value = "account number")
        String accountNumber,
        @JsonProperty(value = "account name")
        String accountName,
        @JsonProperty(value = "account type")
        String accountType,
        @JsonProperty(value = "created date")
        Long createdDate,
        @JsonProperty(value = "owner id")
        String memberNumber) {
}
