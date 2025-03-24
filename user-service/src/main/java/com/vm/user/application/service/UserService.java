package com.vm.user.application.service;

import com.vm.user.application.command.AddBalanceCommand;
import com.vm.user.application.command.LoginUserCommand;
import com.vm.user.application.command.RegisterUserCommand;
import com.vm.common.domain.valueobject.Money;
import com.vm.user.domain.model.User;
import com.vm.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(RegisterUserCommand registerUserCommand) {
        User user = new User(
                UUID.randomUUID(),
                registerUserCommand.getUsername(),
                registerUserCommand.getPasswordHash(),
                new Money(0)
        );
        userRepository.save(user);
        return user;
    }

    public boolean authenticate(LoginUserCommand loginUserCommand) {
        return userRepository.findById(loginUserCommand.getUserId())
                .map(user -> user.getPasswordHash().equals(loginUserCommand.getPasswordHash()))
                .orElse(false);
    }

    public Integer addBalance(AddBalanceCommand request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.increaseBalance(new Money(request.getBalance()));
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
