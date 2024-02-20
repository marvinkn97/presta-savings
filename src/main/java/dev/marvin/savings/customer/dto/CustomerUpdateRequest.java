package dev.marvin.savings.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerUpdateRequest(
        @JsonProperty(value = "name")
        String name,

        @JsonProperty(value = "name")
        String email,

        @JsonProperty(value = "password")
        String password,
        @JsonProperty(value = "mobile number")
        String mobile,

        @JsonProperty(value = "government id")
        String governmentId,

        @JsonProperty(value = "profile image")
        String profileImage) {
}
