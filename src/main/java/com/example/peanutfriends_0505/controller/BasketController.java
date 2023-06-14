package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Basket;
import com.example.peanutfriends_0505.domain.BasketMember;
import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.dto.AddBasketDto;
import com.example.peanutfriends_0505.dto.AddBasketResponseDto;
import com.example.peanutfriends_0505.dto.BasketResponseDto;
import com.example.peanutfriends_0505.repository.BasketMemberRepository;
import com.example.peanutfriends_0505.service.BasketMemberService;
import com.example.peanutfriends_0505.service.BasketService;
import com.example.peanutfriends_0505.service.MemberService;
import com.example.peanutfriends_0505.status.BasketStatus;
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

    @GetMapping("/axios/test")
    public String axiosTest(){
        return "axios test success";
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
        basket.setStatus("WAIT");

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

    @GetMapping("/basket/{basketId}")
    public ResponseEntity basketDetail(@PathVariable Long basketId){
        Basket findBasket = basketService.findById(basketId);
        BasketResponseDto basketResponseDto = new BasketResponseDto();
        basketResponseDto.setBasketId(findBasket.getBasketId());
        basketResponseDto.setName(findBasket.getName());
        basketResponseDto.setStartDate(findBasket.getStartDate());
        basketResponseDto.setEndDate(findBasket.getEndDate());
        basketResponseDto.setRegDate(findBasket.getRegDate());
        basketResponseDto.setMasterMember(findBasket.getMasterMember());

        return new ResponseEntity(basketResponseDto, HttpStatus.OK);
    }

    private Member getLoginMember(Authentication authentication) {
        if(authentication == null){
            System.out.println("authentication에 아무것도 없음");
            return memberService.findById(1L);
        }
        Long memberId = (Long) authentication.getPrincipal();
        Member loginMember = memberService.findById(memberId);
        return loginMember;
    }

}
