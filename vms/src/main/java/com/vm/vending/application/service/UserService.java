package com.vm.vending.application.service;

import com.vm.vending.domain.model.Money;
import com.vm.vending.domain.model.User;
import com.vm.vending.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(String username, String passwordHash) {
        User user = new User(UUID.randomUUID(), username, passwordHash, new Money(0));
        userRepository.save(user);
        return user;
    }

    public boolean authenticate(UUID userId, String passwordHash) {
        return userRepository.findById(userId)
                .map(user -> user.getPasswordHash().equals(passwordHash))
                .orElse(false);
    }

    public Integer addBalance(UUID userId, int amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.increaseBalance(new Money(amount));
        userRepository.save(user);
        return user.getBalance().getAmount();
    }

    public Money getBalance(UUID userId) {
        return userRepository.findById(userId)
                .map(User::getBalance)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public boolean logout(UUID userId) {
        if (userRepository.findById(userId).isPresent()) {
            return true; //todo jwt
        }
        return false;
    }


}
