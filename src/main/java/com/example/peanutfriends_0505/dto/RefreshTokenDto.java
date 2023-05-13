package com.example.peanutfriends_0505.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenDto {
    @NotEmpty
    public String refreshToken;
}
