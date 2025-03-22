package com.vm.vending.domain.repository;

import com.vm.vending.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
}
