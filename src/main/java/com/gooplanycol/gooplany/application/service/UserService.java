package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.UserServicePort;
import com.gooplanycol.gooplany.application.ports.output.UserPersistencePort;
import com.gooplanycol.gooplany.domain.exception.UserNotFoundException;
import com.gooplanycol.gooplany.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userPersistencePort.findAll();
    }

    @Override
    public User save(User user) {
        return userPersistencePort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return userPersistencePort.findById(id)
                .map(userFound -> {
                    userFound.setFirstname(user.getFirstname());
                    userFound.setLastname(user.getLastname());
                    userFound.setEmail(user.getEmail());
                    userFound.setPhone(user.getPhone());
                    return userPersistencePort.save(userFound);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userPersistencePort.deleteById(id);
    }
}
