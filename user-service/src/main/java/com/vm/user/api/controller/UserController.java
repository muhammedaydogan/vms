package com.vm.user.api.controller;

import com.vm.common.application.command.AddBalanceCommand;
import com.vm.common.application.command.LoginUserCommand;
import com.vm.common.application.command.RegisterUserCommand;
import com.vm.user.domain.model.User;
import com.vm.user.application.service.UserService;
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
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserCommand loginUserCommand) {
        return userService.authenticate(loginUserCommand)
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
            var newBalance = userService.addBalance(request);
            return ResponseEntity.ok(String.valueOf(newBalance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }
}
