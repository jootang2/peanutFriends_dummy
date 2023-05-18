package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.BasketMember;
import com.example.peanutfriends_0505.repository.BasketMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketMemberService {
    private final BasketMemberRepository basketMemberRepository;


    public BasketMember addBasketMember(BasketMember basketMember) {
        BasketMember addBasketMember = basketMemberRepository.save(basketMember);
        return addBasketMember;
    }
}
