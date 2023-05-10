package com.example.peanutfriends_0505.controller;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.dto.MemberLoginDto;
import com.example.peanutfriends_0505.dto.MemberLoginResponseDto;
import com.example.peanutfriends_0505.dto.MemberSignUpDto;
import com.example.peanutfriends_0505.dto.MemberSignUpResponseDto;
import com.example.peanutfriends_0505.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody @Valid MemberSignUpDto memberSignUpDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = memberSignUpDto.getEmail();
        String name = memberSignUpDto.getName();
        String password = memberSignUpDto.getPassword();

        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);

        Member saveMember = memberService.addMember(member);

        MemberSignUpResponseDto memberSignUpResponseDto = new MemberSignUpResponseDto();
        memberSignUpResponseDto.setMemberId(saveMember.getMemberId());
        memberSignUpResponseDto.setName(saveMember.getName());
        memberSignUpResponseDto.setRegDate(saveMember.getRegDate());

        return new ResponseEntity(memberSignUpResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto memberLoginDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Member findMember = memberService.findByEmail(memberLoginDto.getEmail());

        if(!findMember.getPassword().equals(memberLoginDto.getPassword())) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String jwt = memberService.login(findMember);

        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto();
        memberLoginResponseDto.setMemberId(findMember.getMemberId());
        memberLoginResponseDto.setName(findMember.getName());
        memberLoginResponseDto.setJwt(jwt);

        return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
    }

}
