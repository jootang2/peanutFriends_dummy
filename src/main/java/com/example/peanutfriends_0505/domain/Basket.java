package com.example.peanutfriends_0505.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue
    private Long basketId;

    private String name;

    private String startDate;

    private String endDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member masterMember;

    private String regDate;

    private String editDate;

    @Enumerated(EnumType.ORDINAL)
    private String status;

    public Basket() {
    }
}
