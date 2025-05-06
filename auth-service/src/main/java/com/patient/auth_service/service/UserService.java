package com.patient.auth_service.service;

import com.patient.auth_service.model.User;
import com.patient.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
