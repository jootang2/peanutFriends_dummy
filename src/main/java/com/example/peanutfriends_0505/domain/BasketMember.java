package com.example.peanutfriends_0505.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BasketMember {
    @Id
    @GeneratedValue
    private Long basketMemberId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "BASKET_ID")
    private Basket basket;

    private Boolean isPermit;

    @ManyToOne
    @JoinColumn(name = "PEANUTFRIEND_ID")
    private Member peanutFriend;
}
