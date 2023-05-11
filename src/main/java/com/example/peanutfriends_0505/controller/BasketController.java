package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.security.Security;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final MemberService memberService;

    @PostMapping("/api/filterTest")
    public String test(Authentication authentication){
        Long memberId = (Long) authentication.getPrincipal();
        Member member = memberService.findById(memberId);

        return member.toString();
    }

}
