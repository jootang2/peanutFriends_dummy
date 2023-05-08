package com.example.peanutfriends_0505.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;

    private String email;

    private String password;

    private String name;

    @CreationTimestamp
    private LocalDateTime regDate;

    @CreationTimestamp
    private LocalDateTime editDate;

    public Member() {

    }

    public Member(Long memberId, String email, String password, String name, LocalDateTime regDate, LocalDateTime editDate) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.regDate = regDate;
        this.editDate = editDate;
    }


}
