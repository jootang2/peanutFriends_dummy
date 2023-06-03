package com.example.peanutfriends_0505.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AddBasketDto {
    private String name;
    private String startDate;
    private String endDate;

    public AddBasketDto(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AddBasketDto() {
    }
}
