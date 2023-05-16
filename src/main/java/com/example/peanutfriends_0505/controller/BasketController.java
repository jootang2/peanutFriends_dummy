package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.dto.AddBasketDto;
import com.example.peanutfriends_0505.dto.AddBasketResponseDto;
import com.example.peanutfriends_0505.service.BasketService;
import com.example.peanutfriends_0505.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BasketController {

    private final MemberService memberService;
    private final BasketService basketService;

    @PostMapping("/filterTest")
    public String test(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        Member member = memberService.findById(memberId);

        return member.toString();
    }

    @PostMapping("/basket/create")
    public ResponseEntity addBasket(@RequestBody AddBasketDto addBasketDto, Authentication authentication) {
        Basket basket = new Basket();
        Long memberId = (Long) authentication.getPrincipal();
        Member loginMember = memberService.findById(memberId);
        basket.setName(addBasketDto.getName());
        basket.setStartDate(addBasketDto.getStartDate());
        basket.setEndDate(addBasketDto.getEndDate());
        basket.setMasterMember(loginMember);
        basket.setRegDate(String.valueOf(LocalDate.now()));

        Basket saveBasket = basketService.addBasket(basket);

        AddBasketResponseDto addBasketResponseDto = new AddBasketResponseDto();
        addBasketResponseDto.setBasketId(saveBasket.getBasketId());
        addBasketResponseDto.setName(saveBasket.getName());
        addBasketResponseDto.setMasterMember(saveBasket.getMasterMember().getName());
        addBasketResponseDto.setStartDate(saveBasket.getStartDate());
        addBasketResponseDto.setEndDate(saveBasket.getEndDate());
        addBasketResponseDto.setRegDate(saveBasket.getRegDate());

        return new ResponseEntity(addBasketResponseDto, HttpStatus.CREATED);
    }

}
