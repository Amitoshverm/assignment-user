package com.tin.user.dto;

import lombok.Data;

@Data
public class LogoutDto {
    private String token;
    private Long userId;
}
