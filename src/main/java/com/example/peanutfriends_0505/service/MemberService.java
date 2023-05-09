package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member addMember(Member member){
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public Member findByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        return findMember;
    }
}
