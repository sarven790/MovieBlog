package com.example.filmblogproject.domain.repository;

import com.example.filmblogproject.domain.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory,Long> {

    PasswordHistory findByUserCredentialNameEqualsIgnoreCase(String userName);

}
