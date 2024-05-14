package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.UserOutputPort;
import com.gooplanycol.gooplany.domain.model.User;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.UserOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserOutputAdapter implements UserOutputPort {
    private final UserRepository repository;
    private final UserOutputMapper mapper;


    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toUser);
    }

    @Override
    public List<User> findAll() {
        return mapper.toUsersList(repository.findAll());
    }

    @Override
    public User save(User user) {
        return mapper.toUser(repository.save(mapper.toUserEntity(user)));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
