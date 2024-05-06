package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.User;

import java.util.List;

public interface UserServicePort {

    User findById(Long id);
    
    List<User> findAll();

    User save(User user);

    User update(Long id,User user);

    void deleteById(Long id);
}
