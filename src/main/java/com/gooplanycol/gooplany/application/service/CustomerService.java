package com.gooplanycol.gooplany.application.service;

import com.gooplanycol.gooplany.application.ports.input.CustomerInputPort;
import com.gooplanycol.gooplany.application.ports.output.CustomerOutputPort;
import com.gooplanycol.gooplany.domain.exception.ProfileException;
import com.gooplanycol.gooplany.domain.model.Customer;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request.AuthenticationRequest;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.utils.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInputPort {

    private final CustomerOutputPort customerOutputPort;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequestDTO) {
        return customerOutputPort.authenticate(authenticationRequestDTO);
    }

    @Override
    public Customer getProfileByToken(String token) {
        return customerOutputPort.getProfileByToken(token);
    }

    @Override
    public boolean removeProfile(Long id) {
        return customerOutputPort.removeProfile(id);
    }

    @Override
    public Customer editData(Customer response, Long id) {
        return customerOutputPort.findById(id)
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
                    return customerOutputPort.save(profile);
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
    public Customer findById(Long id) {
        return customerOutputPort.findById(id)
                .orElseThrow(() -> new ProfileException("The profile fetched by id doesn't exist"));
    }

    @Override
    public List<Customer> findAll(Integer offset, Integer pageSize) {
        return customerOutputPort.findAll(offset, pageSize);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerOutputPort.findByEmail(email);
    }

    @Override
    public Customer changePwd(String pwd, Long id) {
        return customerOutputPort.changePwd(pwd, id);
    }

}
