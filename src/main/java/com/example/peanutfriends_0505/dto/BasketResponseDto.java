package com.example.peanutfriends_0505.dto;

import com.example.peanutfriends_0505.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketResponseDto {
    private Long basketId;
    private String name;
    private Member masterMember;
    private String startDate;
    private String endDate;
    private String regDate;
}
