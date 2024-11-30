package com.example.filmblogproject.domain.repository;

import com.example.filmblogproject.domain.entity.ServiceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceLogRepository extends JpaRepository<ServiceLog,Long> {
}
