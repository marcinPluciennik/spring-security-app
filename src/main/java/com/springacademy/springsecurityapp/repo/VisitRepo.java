package com.springacademy.springsecurityapp.repo;

import com.springacademy.springsecurityapp.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {
}
