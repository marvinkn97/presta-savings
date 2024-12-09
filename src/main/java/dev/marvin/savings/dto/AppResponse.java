package dev.marvin.savings.dto;

public record AppResponse<T>(String status, T payload) {
}
