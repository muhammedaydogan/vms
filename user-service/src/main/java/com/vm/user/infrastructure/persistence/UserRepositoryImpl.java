package com.vm.user.infrastructure.persistence;

import com.vm.common.domain.model.Money;
import com.vm.common.domain.model.User;
import com.vm.user.domain.repository.UserRepository;
import com.vm.user.infrastructure.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public void save(User user) {
        UserEntity entity = new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getPasswordHash(),
                user.getBalance().getAmount()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(entity ->
                new User(
                        entity.getId(),
                        entity.getUsername(),
                        entity.getPasswordHash(),
                        new Money(entity.getBalance())
                )
        );
    }
}
