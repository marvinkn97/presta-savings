package dev.marvin.savings.customer.dto;

public record CustomerUpdateRequest(

        String name,

        String email,

        String password,

        String mobile,

        String governmentId,

        String profileImage) {
}
