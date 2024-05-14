package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.ProfileInputPort;
import com.gooplanycol.gooplany.application.ports.output.ProfileOutputPort;
import com.gooplanycol.gooplany.application.ports.output.UserOutputPort;
import com.gooplanycol.gooplany.domain.exception.ProfileNotFoundException;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileInputPort {

    private final ProfileOutputPort profileOutputPort;
    private final UserOutputPort userOutputPort;

    @Override
    public Profile findById(Long id) {
        return profileOutputPort.findById(id)
                .orElseThrow(ProfileNotFoundException::new);
    }

    @Override
    public List<Profile> findAll() {
        return profileOutputPort.findAll();
    }

    @Override
    public Profile save(Profile profile) {
        User user = profile.getUser();
        user = userOutputPort.save(user);
        profile.setUser(user);
        return profileOutputPort.save(profile);
    }

    @Override
    public Profile update(Long id, Profile profile) {
        return profileOutputPort.findById(id)
                .map(userFound -> {
                    userFound.setFirstName(profile.getFirstName());
                    userFound.setLastName(profile.getLastName());
                    userFound.setUsername(profile.getUsername());
                    userFound.setBirthdate(profile.getBirthdate());
                    userFound.setCountry(profile.getCountry());
                    userFound.setDescription(profile.getDescription());
                    userFound.setEmergencyContact(profile.getEmergencyContact());
                    userFound.setGender(profile.getGender());
                    userFound.setLevel(profile.getLevel());
                    return profileOutputPort.save(userFound);
                })
                .orElseThrow(ProfileNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        profileOutputPort.deleteById(id);
    }
}
