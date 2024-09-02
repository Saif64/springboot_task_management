package com.example.task_management.task_management.repository;


import com.example.task_management.task_management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}

