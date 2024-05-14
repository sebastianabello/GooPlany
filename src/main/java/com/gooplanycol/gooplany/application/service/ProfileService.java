package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.ProfileInputPort;
import com.gooplanycol.gooplany.application.ports.output.EventPostOutputPort;
import com.gooplanycol.gooplany.application.ports.output.EventRegistrationOutputPort;
import com.gooplanycol.gooplany.application.ports.output.ProfileOutputPort;
import com.gooplanycol.gooplany.application.ports.output.UserOutputPort;
import com.gooplanycol.gooplany.domain.exception.AlreadyRegisteredException;
import com.gooplanycol.gooplany.domain.exception.EventPostNotFoundException;
import com.gooplanycol.gooplany.domain.exception.ProfileNotFoundException;
import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.EventRegistration;
import com.gooplanycol.gooplany.domain.model.Profile;
import com.gooplanycol.gooplany.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileInputPort {

    private final ProfileOutputPort profileOutputPort;
    private final UserOutputPort userOutputPort;
    private final EventPostOutputPort eventPostOutputPort;
    private final EventRegistrationOutputPort eventRegistrationOutputPort;

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
                    userFound.setUserName(profile.getUserName());
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

    @Override
    public void registerToEvent(Long profileId, Long eventId) {
        Profile profile = profileOutputPort.findById(profileId).orElseThrow(ProfileNotFoundException::new);
        EventPost eventPost = eventPostOutputPort.findById(eventId).orElseThrow(EventPostNotFoundException::new);

        // Comprueba si ya existe una inscripción para este perfil y evento
        Optional<EventRegistration> existingRegistration = eventRegistrationOutputPort.findByProfileAndEventPostId(profileId, eventId);
        if (existingRegistration.isPresent()) {
            throw new AlreadyRegisteredException("El usuario ya está inscrito en este evento");
        }

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setProfile(profile);
        eventRegistration.setEventPost(eventPost);
        eventRegistration.setRegisteredAt(LocalDateTime.now());
        eventRegistrationOutputPort.save(eventRegistration);
    }

    @Override
    public List<String> getEmailsByEventId(Long eventId) {
        return eventRegistrationOutputPort.findAllByEventPostId(eventId).stream()
                .map(eventRegistration -> eventRegistration.getProfile().getUser().getEmail())
                .toList();
    }
}
