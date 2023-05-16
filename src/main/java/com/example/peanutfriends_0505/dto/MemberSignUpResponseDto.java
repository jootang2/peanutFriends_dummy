package com.example.peanutfriends_0505.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class MemberSignUpResponseDto {

    private Long memberId;
    private String name;
    private String regDate;

    public MemberSignUpResponseDto(Long memberId, String name, String regDate) {
        this.memberId = memberId;
        this.name = name;
        this.regDate = regDate;
    }

    public MemberSignUpResponseDto () {

    }
}
