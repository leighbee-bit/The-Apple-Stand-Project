package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Apple;

public interface AppleRepository extends JpaRepository<Apple, Long> {
}