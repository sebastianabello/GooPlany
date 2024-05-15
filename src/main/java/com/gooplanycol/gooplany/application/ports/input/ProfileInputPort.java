package com.gooplanycol.gooplany.application.ports.input;

import com.gooplanycol.gooplany.domain.model.Profile;

import java.util.List;

public interface ProfileInputPort {
    Profile findById(Long id);

    List<Profile> findAll();

    Profile save(Profile profile);

    Profile update(Long id,Profile profile);

    void deleteById(Long id);

    void registerToEvent(Long profileId, Long eventId);
}
