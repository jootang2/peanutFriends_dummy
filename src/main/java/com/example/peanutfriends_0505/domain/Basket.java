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

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member captain;

    @CreationTimestamp
    private LocalDateTime regDate;

    @CreationTimestamp
    private LocalDateTime editDate;


}
