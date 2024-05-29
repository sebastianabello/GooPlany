package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence;

import com.gooplanycol.gooplany.application.ports.output.RegistrationCompanyOutputPort;
import com.gooplanycol.gooplany.application.service.EmailSenderService;
import com.gooplanycol.gooplany.application.service.EmailValidator;
import com.gooplanycol.gooplany.domain.model.*;
import com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response.AuthenticationResponse;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.mapper.CompanyOutputMapper;
import com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.repository.CompanyRepository;
import com.gooplanycol.gooplany.utils.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompanyOutputAdapter implements RegistrationCompanyOutputPort {

    private final CompanyRepository companyRepository;
    private final CompanyOutputMapper companyOutputMapper;

    private final EmailValidator emailValidator;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenOutputAdapter confirmationTokenOutputAdapter;

    @Override
    public AuthenticationResponse save(Company request) throws IllegalAccessException {
        if (request != null) {
            boolean customerExist = companyRepository.findCompanyByEmail(request.getEmail()).isPresent();
            boolean isValidEmail = emailValidator.test(request.getEmail());
            if (customerExist || !isValidEmail) {
                throw new IllegalAccessException("email already taken or  email not valid");
            } else {
                Company company = Company.builder()
                        .enable(false)
                        .tokens(new ArrayList<>())
                        .confirmationTokens(new ArrayList<>())
                        .address(new ArrayList<>())
                        .roles(Arrays.asList(Role.COMPANY))
                        .build();
                company.setName(request.getName());
                company.setCellphone(request.getCellphone());
                company.setEmail(request.getEmail());
                company.setNit(request.getNit());
                company.setPwd(passwordEncoder.encode(request.getPwd()));
                company.setCreateAt(LocalDate.now());

                companyOutputMapper.toCompany(companyRepository.save(companyOutputMapper.toCompanyEntity(company)));
                return new AuthenticationResponse(resendConfirmationEmail(company.getEmail())); //confirmation token
            }
        } else {
            return null;
        }
    }

    public String resendConfirmationEmail(String email) {
        Company company = companyOutputMapper.toCompany(companyRepository.findCompanyByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found")));

        ConfirmationToken token = generateToken(company);

        String link = "http://localhost:8080/api/v1/authentication/confirm?token=" + token.getToken();
        emailSenderService.send(company.getEmail(), buildEmail(company.getName(), link));
        return token.getToken();

    }

    public ConfirmationToken generateToken(Company profile) {
        String tokenValue = UUID.randomUUID().toString();
        ConfirmationToken token = ConfirmationToken.builder()
                .token(tokenValue)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(profile)
                .build();
        confirmationTokenOutputAdapter.saveConfirmationToken(token);
        return token;
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenOutputAdapter.getToken(token).orElse(null);
        if (confirmationToken != null) {
            if (confirmationToken.getConfirmedAt() != null) {
                return "Email already confirmed";
            } else {
                LocalDateTime expiredAt = confirmationToken.getExpiresAt();
                if (expiredAt.isBefore(LocalDateTime.now())) {//is expired
                    resendConfirmationEmail(confirmationToken.getUser().getEmail());
                    System.out.println("Token expired, send another one");
                    return "the token was expired so we already send you another one";
                } else {
                    confirmationTokenOutputAdapter.setConfirmedAt(token);
                    Company company = (Company) confirmationToken.getUser();
                    company.setEnable(true);
                    companyOutputMapper.toCompany(companyRepository.save(companyOutputMapper.toCompanyEntity(company)));
                    return "Email confirmed successfully";
                }
            }
        } else {
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
