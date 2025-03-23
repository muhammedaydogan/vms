package com.vm.user.domain.repository;

import com.vm.common.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
}
