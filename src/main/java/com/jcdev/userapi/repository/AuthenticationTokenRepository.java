package com.jcdev.userapi.repository;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository("authenticationTokenRepository")
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {
}
