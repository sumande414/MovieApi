package com.movieflix.movieApi.auth.services;

import com.movieflix.movieApi.auth.entities.RefreshToken;
import com.movieflix.movieApi.auth.entities.User;
import com.movieflix.movieApi.auth.repositories.RefreshTokenRepository;
import com.movieflix.movieApi.auth.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + username));

        RefreshToken refreshToken = user.getRefreshToken();
        long refreshTokenValidity = 30 * 1000;
        if (refreshToken == null) {

            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
        }
        else if(refreshToken.getExpirationTime().isBefore(Instant.now())){
            refreshToken.setRefreshToken(UUID.randomUUID().toString());
            refreshToken.setExpirationTime(Instant.now().plusMillis(refreshTokenValidity));
        }

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));

        System.out.println(refToken.getExpirationTime()+"\t"+Instant.now());
        if (refToken.getExpirationTime().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh Token expired");
        }

        return refToken;
    }
}
