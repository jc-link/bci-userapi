package com.jcdev.userapi.repository;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("authenticationTokenRepository")
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);
}
