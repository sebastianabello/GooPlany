package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.RegistrationOutputPort;
import com.gooplanycol.gooplany.application.service.EmailSenderService;
import com.gooplanycol.gooplany.application.service.EmailValidator;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity.*;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.ConfirmationTokenOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CustomerOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CustomerRepository;
import com.gooplanycol.gooplany.utils.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationOutputAdapter implements RegistrationOutputPort {

    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper companyOutputMapper;

    private final CustomerRepository customerRepository;
    private final CustomerOutputMapper customerOutputMapper;

    private final EmailValidator emailValidator;
    private final EmailSenderService emailSenderService;

    private final PasswordEncoder passwordEncoder;

    private final ConfirmationTokenOutputAdapter confirmationTokenOutputAdapter;
    private final ConfirmationTokenOutputMapper confirmationTokenOutputMapper;

    public String resendConfirmationEmail(String email) {
        CustomerEntity customer = customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        CompanyEntity company = companyRepository.findCompanyByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        ConfirmationTokenEntity tokenCustomer = generateToken(customerOutputMapper.toCustomer(customer));
        ConfirmationTokenEntity tokenCompany = generateToken(companyOutputMapper.toCompany(company));

        String linkCustomer = "http://localhost:8080/api/v1/authentication/confirm?token=" + tokenCustomer.getToken();
        String linkCompany = "http://localhost:8080/api/v1/authentication/confirm?token=" + tokenCompany.getToken();

        if (customer != null) {
            emailSenderService.send(customer.getEmail(), buildEmail(customer.getName(), linkCustomer));
            return tokenCustomer.getToken();
        } else {
            emailSenderService.send(company.getEmail(), buildEmail(company.getName(), linkCompany));
            return tokenCompany.getToken();
        }
    }

    public ConfirmationTokenEntity generateToken(Customer customer) {
        String tokenValue = UUID.randomUUID().toString();
        ConfirmationTokenEntity token = ConfirmationTokenEntity.builder()
                .token(tokenValue)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(customerOutputMapper.toCustomerEntity(customer))
                .build();
        confirmationTokenOutputAdapter.saveConfirmationToken(confirmationTokenOutputMapper.toConfirmationToken(token));
        return token;
    }

    public ConfirmationTokenEntity generateToken(Company company) {
        String tokenValue = UUID.randomUUID().toString();
        ConfirmationTokenEntity token = ConfirmationTokenEntity.builder()
                .token(tokenValue)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .company(companyOutputMapper.toCompanyEntity(company))
                .build();
        confirmationTokenOutputAdapter.saveConfirmationToken(confirmationTokenOutputMapper.toConfirmationToken(token));
        return token;
    }

    @Override
    public Authentication save(Customer customer) throws IllegalAccessException {
        if (customer != null) {
            boolean customerExist = customerRepository.findCustomerByEmail(customer.getEmail()).isPresent();
            boolean isValidEmail = emailValidator.test(customer.getEmail());
            if (customerExist || !isValidEmail) {
                throw new IllegalAccessException("email already taken or  email not valid");
            } else {
                CustomerEntity customerEntity = CustomerEntity.builder()
                        .lastName(customer.getLastName())
                        .address(new ArrayList<>())
                        .cards(new ArrayList<>())
                        .historyCustomer(new HistoryCustomerEntity(null, new ArrayList<>(), LocalDateTime.now()))
                        .roles(List.of(Role.CUSTOMER))
                        .tokens(new ArrayList<>())
                        .confirmationTokens(new ArrayList<>())
                        .enable(false)
                        .build();
                customerEntity.setName(customer.getName());
                customerEntity.setLastName(customer.getLastName());
                customerEntity.setCellphone(customer.getCellphone());
                customerEntity.setEmail(customer.getEmail());
                customerEntity.setUsername(customer.getUsername());
                customerEntity.setPwd(passwordEncoder.encode(customer.getPwd()));
                customerEntity.setCreatedAt(LocalDateTime.now());
                customerRepository.save(customerEntity);
                return new Authentication(resendConfirmationEmail(customer.getEmail())); //confirmation token
            }
        } else {
            return null;
        }
    }

    @Override
    public Authentication save(Company request) throws IllegalAccessException {
        if (request != null) {
            boolean customerExist = companyRepository.findCompanyByEmail(request.getEmail()).isPresent();
            boolean isValidEmail = emailValidator.test(request.getEmail());
            if (customerExist || !isValidEmail) {
                throw new IllegalAccessException("email already taken or  email not valid");
            } else {
                CompanyEntity company = CompanyEntity.builder()
                        .address(new ArrayList<>())
                        .historyCompany(new HistoryCompanyEntity(null, new ArrayList<>(), LocalDateTime.now()))
                        .roles(List.of(Role.COMPANY))
                        .tokens(new ArrayList<>())
                        .confirmationTokens(new ArrayList<>())
                        .enable(false)
                        .build();
                company.setName(request.getName());
                company.setCellphone(request.getCellphone());
                company.setEmail(request.getEmail());
                company.setUsername(request.getUsername());
                company.setPwd(passwordEncoder.encode(request.getPwd()));
                company.setCreatedAt(LocalDateTime.now());
                companyRepository.save(company);
                return new Authentication(resendConfirmationEmail(company.getEmail())); //confirmation token
            }
        } else {
            return null;
        }
    }

    private boolean isTokenConfirmed(ConfirmationTokenEntity confirmationToken) {
        return confirmationToken.getConfirmedAt() != null;
    }

    private boolean isTokenExpired(ConfirmationTokenEntity confirmationToken) {
        return confirmationToken.getExpiresAt().isBefore(LocalDateTime.now());
    }

    private void confirmTokenAndEnableUser(ConfirmationTokenEntity confirmationToken) {
        confirmationTokenOutputAdapter.setConfirmedAt(confirmationToken.getToken());
        CompanyEntity company = confirmationToken.getCompany();
        CustomerEntity customer = confirmationToken.getCustomer();
        if (company != null) {
            company.setEnable(true);
            companyRepository.save(company);
        }
        if (customer != null) {
            customer.setEnable(true);
            customerRepository.save(customer);
        }
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationTokenEntity confirmationToken = confirmationTokenOutputAdapter.getToken(token).orElse(null);
        if (confirmationToken == null) {
            return "Error confirmation token null";
        }
        if (isTokenConfirmed(confirmationToken)) {
            return "Email already confirmed";
        }
        if (isTokenExpired(confirmationToken)) {
            resendConfirmationEmail(confirmationToken.getCompany().getEmail());
            log.info("Token expired, send another one");
            return "the token was expired so we already send you another one";
        }
        confirmTokenAndEnableUser(confirmationToken);
        return "Email confirmed successfully";
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
