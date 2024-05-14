package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.domain.model.User;

import java.util.List;

public interface ProfileInputPort {
    Profile findById(Long id);

    List<Profile> findAll();

    Profile save(Profile profile);

    Profile update(Long id,Profile profile);

    void deleteById(Long id);
}
