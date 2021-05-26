package com.springacademy.springsecurityapp.repo;

import com.springacademy.springsecurityapp.model.VerificationTokenAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VerificationTokenAdminRepo extends JpaRepository<VerificationTokenAdmin, Long> {

    VerificationTokenAdmin findByValue(String value);
    void deleteByValue(String value);
}
