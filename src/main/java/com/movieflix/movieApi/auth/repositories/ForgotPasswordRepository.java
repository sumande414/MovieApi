package com.movieflix.movieApi.auth.repositories;

import com.movieflix.movieApi.auth.entities.ForgotPassword;
import com.movieflix.movieApi.auth.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
    @Query("select fp from ForgotPassword fp where fp.otp = ?1  and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);

    Optional<ForgotPassword> findByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForgotPassword f WHERE f.fpid = :id")
    void deleteByFpid(@Param("id") Integer id);
}
