package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.domain.BasketMember;
import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.dto.AddBasketDto;
import com.example.peanutfriends_0505.dto.AddBasketResponseDto;
import com.example.peanutfriends_0505.repository.BasketMemberRepository;
import com.example.peanutfriends_0505.service.BasketMemberService;
import com.example.peanutfriends_0505.service.BasketService;
import com.example.peanutfriends_0505.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BasketController {

    private final MemberService memberService;
    private final BasketService basketService;
    private final BasketMemberService basketMemberService;

    @PostMapping("/filterTest")
    public String test(Authentication authentication) {
        Member member = getLoginMember(authentication);

        return member.toString();
    }

    @PostMapping("/basket/create")
    public ResponseEntity addBasket(@RequestBody AddBasketDto addBasketDto, Authentication authentication) {
        Basket basket = new Basket();
        Member loginMember = getLoginMember(authentication);
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

    @GetMapping("/baskets")
    public List<Basket> getBaskets() {
        return basketService.getBasketList();
    }

    @PostMapping("/basket/{basketId}/join")
    public String signUpBasket(@PathVariable Long basketId, Authentication authentication){
        Basket findBasket = basketService.findById(basketId);
        Member loginMember = getLoginMember(authentication);
        BasketMember basketMember = new BasketMember();
        basketMember.setBasket(findBasket);
        basketMember.setMember(loginMember);
        basketMember.setIsPermit(false);

        basketMemberService.addBasketMember(basketMember);
        return "join basket success";
    }

    private Member getLoginMember(Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        Member loginMember = memberService.findById(memberId);
        return loginMember;
    }

}
