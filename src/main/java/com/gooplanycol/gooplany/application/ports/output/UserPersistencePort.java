package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    void deleteById(Long id);

}
