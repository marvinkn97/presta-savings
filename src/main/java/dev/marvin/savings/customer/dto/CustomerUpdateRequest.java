package dev.marvin.savings.customer.dto;

public record CustomerUpdateRequest(
        String name,
        String email,
        String pass,
        String mobile,
        Integer governmentId) {
}

//TODO: add password field