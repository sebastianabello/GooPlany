package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.RegistrationOutputPort;
import com.gooplanycol.gooplany.application.service.EmailSenderService;
import com.gooplanycol.gooplany.application.service.EmailValidator;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ConfirmationTokenCustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.utils.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationOutputAdapter implements RegistrationOutputPort {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    private final EmailValidator emailValidator;
    private final EmailSenderService emailSenderService;

    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenCustomerOutputAdapter confirmationTokenCustomerOutputAdapter;
    private final ConfirmationTokenCustomerOutputMapper confirmationTokenCustomerOutputMapper;



    public ConfirmationTokenCustomerEntity generateTokenCustomer(CustomerEntity customer) {
        String tokenValue = UUID.randomUUID().toString();
        ConfirmationTokenCustomerEntity token = ConfirmationTokenCustomerEntity.builder()
                .token(tokenValue)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(customer)
                .build();
        confirmationTokenCustomerOutputAdapter.saveConfirmationToken(confirmationTokenCustomerOutputMapper.toConfirmationTokenCustomer(token));
        return token;
    }

    public String resendConfirmationEmail(String email) {
        CustomerEntity customer = customerRepository.findCustomerByEmail(email).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        ConfirmationTokenCustomerEntity tokenCustomer = generateTokenCustomer(customer);
        String link = "http://localhost:8080/api/v1/authentication/confirm?token=" + tokenCustomer.getToken();
        emailSenderService.send(customer.getEmail(), buildEmail(customer.getName(), link));
        return tokenCustomer.getToken();
    }

    @Override
    public Customer saveCustomer(Customer requestCustomer) throws IllegalAccessException {
        if (requestCustomer != null) {
            boolean customerExist = customerRepository.findCustomerByEmail(requestCustomer.getEmail()).isPresent();
            boolean isValidEmail = emailValidator.test(requestCustomer.getEmail());
            if (customerExist || !isValidEmail) {
                throw new IllegalAccessException("email already taken or  email not valid");
            } else {
                CustomerEntity customer = CustomerEntity.builder()
                        .address(new ArrayList<>())
                        .cards(new ArrayList<>())
                        .history(new HistoryEntity(null, new ArrayList<>(), LocalDate.now()))
                        .roles(List.of(Role.CUSTOMER))
                        .tokens(new ArrayList<>())
                        .confirmationTokens(new ArrayList<>())
                        .enabled(false)
                        .build();
                customer.setName(requestCustomer.getName());
                customer.setLastName(requestCustomer.getLastName());
                customer.setBirthdate(requestCustomer.getBirthdate());
                customer.setCellphone(requestCustomer.getCellphone());
                customer.setEmail(requestCustomer.getEmail());
                customer.setUsername(requestCustomer.getUsername());
                customer.setPwd(passwordEncoder.encode(requestCustomer.getPwd()));
                customerRepository.save(customer); // Guarda la entidad CustomerEntity en la base de datos antes de generar el token de confirmación

                // Ahora puedes generar y guardar el token de confirmación
                ConfirmationTokenCustomerEntity tokenCustomer = generateTokenCustomer(customer);
                String link = "http://localhost:8080/api/v1/authentication/confirm?token=" + tokenCustomer.getToken();
                emailSenderService.send(customer.getEmail(), buildEmail(customer.getName(), link));
                return new Customer(tokenCustomer.getToken());
            }
        } else {
            return null;
        }
    }

    @Override
    public Company saveCompany(Company requestCustomer) throws IllegalAccessException {
        if (requestCustomer != null) {
            boolean customerExist = companyRepository.findCompanyByEmail(requestCustomer.getEmail()).isPresent();
            boolean isValidEmail = emailValidator.test(requestCustomer.getEmail());
            if (customerExist || !isValidEmail) {
                throw new IllegalAccessException("email already taken or  email not valid");
            } else {
                CompanyEntity company = CompanyEntity.builder()
                        .address(new ArrayList<>())
                        .history(new HistoryEntity(null, new ArrayList<>(), LocalDate.now()))
                        .roles(List.of(Role.COMPANY))
                        .tokens(new ArrayList<>())
                        .confirmationTokens(new ArrayList<>())
                        .enabled(false)
                        .build();
                company.setName(requestCustomer.getName());
                company.setCellphone(requestCustomer.getCellphone());
                company.setNit(requestCustomer.getNit());
                company.setEmail(requestCustomer.getEmail());
                company.setUsername(requestCustomer.getUsername());
                company.setPwd(passwordEncoder.encode(requestCustomer.getPwd()));
                companyRepository.save(company);
                return new Company(resendConfirmationEmail(company.getEmail())); //confirmation token
            }
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public String confirmToken(String token) {
        ConfirmationTokenCustomerEntity confirmationTokenCustomer = confirmationTokenCustomerOutputMapper.toConfirmationTokenCustomerEntity(confirmationTokenCustomerOutputAdapter.getToken(token).orElse(null));
        if(confirmationTokenCustomer!=null){
            if(confirmationTokenCustomer.getConfirmedAt()!=null){
                return "Email already confirmed";
            }else{
                LocalDateTime expiredAt = confirmationTokenCustomer.getExpiresAt();
                if(expiredAt.isBefore(LocalDateTime.now())){//is expired
                    resendConfirmationEmail(confirmationTokenCustomer.getCustomer().getEmail());
                    log.info("Token expired, send another one");
                    return "the token was expired so we already send you another one";
                }else{
                    confirmationTokenCustomerOutputAdapter.setConfirmedAt(token);
                    CustomerEntity customer = confirmationTokenCustomer.getCustomer();
                    customer.setEnabled(true);
                    customerRepository.save(customer);
                    return "Email confirmed successfully";
                }
            }
        }else{
            return "Error confirmation token null";
        }
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
