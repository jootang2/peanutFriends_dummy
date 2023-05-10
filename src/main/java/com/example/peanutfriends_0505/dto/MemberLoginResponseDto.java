package com.example.peanutfriends_0505.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberLoginResponseDto {
    private Long memberId;
    private String name;
    private String jwt;
}
