package com.ecommerce.sb_ecom.dto;

public record ApiResponse<T>(String message, T data) {
}
