package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.BasketMember;
import com.example.peanutfriends_0505.repository.BasketMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketMemberService {
    private final BasketMemberRepository basketMemberRepository;


    public BasketMember addBasketMember(BasketMember basketMember) {
        BasketMember addBasketMember = basketMemberRepository.save(basketMember);
        return addBasketMember;
    }

    public BasketMember findById(Long basketMemberId){
        BasketMember findBasketMember = basketMemberRepository.findById(basketMemberId).orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 BasketMember 입니다."));
        return findBasketMember;
    }

    public List<BasketMember> findByBasketId(Long basketId){
        List<BasketMember> findBasketMember = basketMemberRepository.findByBasketId(basketId);
        return findBasketMember;
    }
}
