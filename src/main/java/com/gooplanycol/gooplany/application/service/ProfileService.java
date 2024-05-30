package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.ProfileInputPort;
import com.gooplanycol.gooplany.application.ports.output.ProfileOutputPort;
import com.gooplanycol.gooplany.domain.exception.ProfileException;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.utils.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileInputPort {

    private final ProfileOutputPort profileOutputPort;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO) {
        return profileOutputPort.authenticate(authenticationRequestDTO);
    }

    @Override
    public Profile getProfileByToken(String token) {
        return profileOutputPort.getProfileByToken(token);
    }

    @Override
    public boolean removeProfile(Long id) {
        return profileOutputPort.removeProfile(id);
    }

    @Override
    public Profile editData(Profile response, Long id) {
        return profileOutputPort.findById(id)
                .map(profile -> {
                    profile.setCellphone(response.getCellphone());
                    profile.setEmail(response.getEmail());
                    profile.setUsername(response.getUsername());
                    profile.setFirstName(response.getFirstName());
                    profile.setLastName(response.getLastName());
                    profile.setDescription(response.getDescription());
                    profile.setEmergencyContact(response.getEmergencyContact());
                    profile.setLevel(findLevel(response.getLevel()).name());
                    profile.setUpdatedAt(LocalDate.now());
                    return profileOutputPort.save(profile);
                })
                .orElseThrow(() -> new ProfileException("The profile to update doesn't exist or the request is null"));
    }

    private Level findLevel(String level){
        return switch (level) {
            case "private" -> Level.PRIVATE;
            case "friends" -> Level.FRIENDS;
            case "friends_of_friends" -> Level.FRIENDS_OF_FRIENDS;
            default -> Level.PUBLIC;
        };
    }

    @Override
    public Profile findById(Long id) {
        return profileOutputPort.findById(id)
                .orElseThrow(() -> new ProfileException("The profile fetched by id doesn't exist"));
    }

    @Override
    public List<Profile> findAll(Integer offset, Integer pageSize) {
        return profileOutputPort.findAll(offset, pageSize);
    }

    @Override
    public Profile findByEmail(String email) {
        return profileOutputPort.findByEmail(email);
    }

    @Override
    public Profile changePwd(String pwd, Long id) {
        return profileOutputPort.changePwd(pwd, id);
    }

}
