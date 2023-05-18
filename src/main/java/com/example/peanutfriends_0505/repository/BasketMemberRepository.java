package com.example.peanutfriends_0505.repository;

import com.example.peanutfriends_0505.domain.BasketMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketMemberRepository extends JpaRepository<BasketMember, Long> {
}
