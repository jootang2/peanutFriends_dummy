package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.configuration.AuthenticationConfig;
import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.domain.RefreshToken;
import com.example.peanutfriends_0505.dto.*;
import com.example.peanutfriends_0505.repository.MemberRepository;
import com.example.peanutfriends_0505.service.MemberService;
import com.example.peanutfriends_0505.service.RefreshTokenService;
import com.example.peanutfriends_0505.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    @Value("${jwt.secretKey}")
    public String accessSecretKey;
    @Value("${jwt.refreshKey}")
    public String refreshSecretKey;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody @Valid MemberSignUpDto memberSignUpDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = memberSignUpDto.getEmail();
        String name = memberSignUpDto.getName();
        String password = memberSignUpDto.getPassword();

        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);
        member.setRegDate(String.valueOf(LocalDate.now()));

        Member saveMember = memberService.addMember(member);

        MemberSignUpResponseDto memberSignUpResponseDto = new MemberSignUpResponseDto();
        memberSignUpResponseDto.setMemberId(saveMember.getMemberId());
        memberSignUpResponseDto.setName(saveMember.getName());
        memberSignUpResponseDto.setRegDate(saveMember.getRegDate());

        return new ResponseEntity(memberSignUpResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto memberLoginDto, BindingResult bindingResult, HttpServletResponse res) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Member findMember = memberService.findByEmail(memberLoginDto.getEmail());

        if (!findMember.getPassword().equals(memberLoginDto.getPassword())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String accessToken = JwtUtil.createAccessToken(findMember, accessSecretKey);
        String refreshToken = JwtUtil.createRefreshToken(findMember, refreshSecretKey);

        Cookie accessCookie = new Cookie("atk", accessToken);
//        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");

        Cookie refreshCookie = new Cookie("rtk", refreshToken);
//        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");

        res.addCookie(accessCookie);
        res.addCookie(refreshCookie);

        RefreshToken saveRefreshToken = new RefreshToken();
        saveRefreshToken.setMemberId(findMember.getMemberId());
        saveRefreshToken.setValue(refreshToken);
        refreshTokenService.addToken(saveRefreshToken);

        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto();
        memberLoginResponseDto.setMemberId(findMember.getMemberId());
        memberLoginResponseDto.setName(findMember.getName());
        memberLoginResponseDto.setAccessToken(accessToken);
        memberLoginResponseDto.setRefreshToken(refreshToken);

        return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken());
        Long memberId = JwtUtil.getMemberId(refreshToken.getValue(), refreshSecretKey);
        Member member = memberService.findById(memberId);

        String accessToken = JwtUtil.createAccessToken(member, accessSecretKey);

        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto();
        memberLoginResponseDto.setMemberId(member.getMemberId());
        memberLoginResponseDto.setName(member.getName());
        memberLoginResponseDto.setAccessToken(accessToken);
        memberLoginResponseDto.setRefreshToken(refreshToken.getValue());

        return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenDto refreshTokenDto, HttpServletResponse res) {
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        Cookie accessCookie = new Cookie("atk", null);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);

        Cookie refreshCookie = new Cookie("rtk", null);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);

        res.addCookie(accessCookie);
        res.addCookie(refreshCookie);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity check(@CookieValue(value = "atk", required = false) String accessToken) {
        if (accessToken != null) {
            Long memberId = JwtUtil.getMemberId(accessToken, accessSecretKey);
            return new ResponseEntity(memberId, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
