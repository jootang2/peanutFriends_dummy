package com.example.peanutfriends_0505.service;

import com.example.peanutfriends_0505.domain.RefreshToken;
import com.example.peanutfriends_0505.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void deleteRefreshToken(String refreshToken) {
        RefreshToken findRefreshToken = refreshTokenRepository.findByValue(refreshToken).orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 RefreshToken입니다."));
        refreshTokenRepository.delete(findRefreshToken);
    }

    public RefreshToken addToken(RefreshToken refreshToken) {
        RefreshToken saveToken = refreshTokenRepository.save(refreshToken);
        return saveToken;
    }

    public RefreshToken findRefreshToken(String refreshToken) {
        RefreshToken findRefreshToken = refreshTokenRepository.findByValue(refreshToken).orElseThrow(()-> new IllegalArgumentException("찾을 수 없는 RefreshToken입니다."));
        return findRefreshToken;
    }
}
