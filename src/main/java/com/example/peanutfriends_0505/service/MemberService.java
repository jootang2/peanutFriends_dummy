package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.Member;
import com.example.peanutfriends_0505.repository.MemberRepository;
import com.example.peanutfriends_0505.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private final MemberRepository memberRepository;

    private Long expiredMs = 1000 * 60 * 60l;

    public Member addMember(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public Member findByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        return findMember;
    }

    public Member findById(Long memberId) {
        Member findMember =  memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        return findMember;
    }

    public void removeMemberAccount(Member member) {
        memberRepository.delete(member);
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
