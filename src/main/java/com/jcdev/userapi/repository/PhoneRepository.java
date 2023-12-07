package com.jcdev.userapi.repository;

import com.jcdev.userapi.domain.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("phoneRepository")
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
