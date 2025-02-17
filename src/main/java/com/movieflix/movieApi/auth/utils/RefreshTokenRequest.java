package com.movieflix.movieApi.auth.utils;

import com.movieflix.movieApi.auth.entities.RefreshToken;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
