package com.example.task_management.task_management.services.impl;


import com.example.task_management.task_management.repository.UserRepository;
import com.example.task_management.task_management.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;


}
