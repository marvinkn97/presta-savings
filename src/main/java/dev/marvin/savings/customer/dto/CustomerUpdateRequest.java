package dev.marvin.savings.customer.dto;

public record CustomerUpdateRequest(String name, String email, String mobile) {
}

//TODO: add password field