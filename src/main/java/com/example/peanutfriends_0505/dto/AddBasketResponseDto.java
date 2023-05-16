package com.example.peanutfriends_0505.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBasketResponseDto {
    private Long basketId;
    private String name;
    private String masterMember;
    private String startDate;
    private String endDate;
    private String regDate;
}
