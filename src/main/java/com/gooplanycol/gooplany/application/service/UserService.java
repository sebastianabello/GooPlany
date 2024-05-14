package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.UserInputPort;
import com.gooplanycol.gooplany.application.ports.output.UserOutputPort;
import com.gooplanycol.gooplany.domain.exception.UserNotFoundException;
import com.gooplanycol.gooplany.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserInputPort {
    private final UserOutputPort userOutputPort;

    @Override
    public User findById(Long id) {
        return userOutputPort.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userOutputPort.findAll();
    }

    @Override
    public User save(User user) {
        return userOutputPort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        return userOutputPort.findById(id)
                .map(userFound -> {
                    userFound.setEmail(user.getEmail());
                    return userOutputPort.save(userFound);
                })
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        if (userOutputPort.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userOutputPort.deleteById(id);
    }
}
