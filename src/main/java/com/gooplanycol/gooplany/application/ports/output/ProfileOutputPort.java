package com.gooplanycol.gooplany.application.ports.output;

import com.gooplanycol.gooplany.domain.model.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileOutputPort {
    Optional<Profile> findById(Long id);

    List<Profile> findAll();

    Profile save(Profile profile);

    void deleteById(Long id);
}
