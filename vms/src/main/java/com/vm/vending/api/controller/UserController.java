package com.vm.vending.api.controller;

import com.vm.vending.application.command.AddBalanceCommand;
import com.vm.vending.application.command.LoginUserCommand;
import com.vm.vending.application.command.RegisterUserCommand;
import com.vm.vending.application.service.UserService;
import com.vm.vending.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserCommand request) {
        // TODO implement JWT
        return ResponseEntity.ok(userService.registerUser(request.getUsername(), request.getPasswordHash()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserCommand loginUserCommand) {
        return userService.authenticate(loginUserCommand.getUserId(), loginUserCommand.getPasswordHash())
                ? ResponseEntity.ok("Login successful")
                : ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout(@RequestBody UUID userId) {
        // todo jwt
        if (userService.logout(userId)) {
            return ResponseEntity.ok("Logout successful");
        }
        return ResponseEntity.status(404).body(null);
    }

    @PostMapping("/{userId}/balance}")
    public ResponseEntity<String> getBalance(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(String.valueOf(userService.getBalance(userId).getAmount()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("User not found");
        }
    }

    // should use cardService or can be moved to cardController later on
    @PostMapping("/add-balance")
    public ResponseEntity<String> addBalance(@RequestBody AddBalanceCommand request) {
        try {
            var newBalance = userService.addBalance(request.getUserId(), request.getBalance());
            return ResponseEntity.ok(String.valueOf(newBalance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
