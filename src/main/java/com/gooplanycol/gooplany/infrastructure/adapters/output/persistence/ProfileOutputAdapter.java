package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.ProfileOutputPort;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ProfileOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfileOutputAdapter implements ProfileOutputPort {
    private final ProfileRepository profileRepository;
    private final ProfileOutputMapper mapper;


    @Override
    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id)
                .map(mapper::toProfile);
    }

    @Override
    public List<Profile> findAll() {
        return mapper.toProfileList(profileRepository.findAll());
    }

    @Override
    public Profile save(Profile profile) {
        return mapper.toProfile(profileRepository.save(mapper.toProfileEntity(profile)));
    }

    @Override
    public void deleteById(Long id) {
        profileRepository.deleteById(id);
    }
}
