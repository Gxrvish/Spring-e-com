package com.ecommerce.sb_ecom.common.dto;

public record ApiResponse<T>(String message, T data) {
}
