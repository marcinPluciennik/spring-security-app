package com.springacademy.springsecurityapp.service;

import com.springacademy.springsecurityapp.model.AppUser;
import com.springacademy.springsecurityapp.model.VerificationToken;
import com.springacademy.springsecurityapp.model.VerificationTokenAdmin;
import com.springacademy.springsecurityapp.repo.AppUserRepo;
import com.springacademy.springsecurityapp.repo.VerificationTokenAdminRepo;
import com.springacademy.springsecurityapp.repo.VerificationTokenRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${email.admin}")
    private String email_admin;

    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;
    private VerificationTokenRepo verificationTokenRepo;
    private MailSenderService mailSenderService;
    private VerificationTokenAdminRepo verificationTokenAdminRepo;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo,
                       VerificationTokenRepo verificationTokenRepo, MailSenderService mailSenderService,
                       VerificationTokenAdminRepo verificationTokenAdminRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailSenderService = mailSenderService;
        this.verificationTokenAdminRepo = verificationTokenAdminRepo;
    }

    public void addNewUser(AppUser user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (appUserRepo.findByUsername(user.getUsername()).isPresent()){
            LOGGER.error("ACCOUNT EXISTS ALREADY!");
        }
        appUserRepo.save(user);
        LOGGER.info("SUCCESS, ACCOUNT HAS BEEN CREATED");

        String token = UUID.randomUUID().toString();


        try{
            LOGGER.info("CHOSEN ROLES: " + user.getRoles().toString());

            if (user.getRoles().contains("ROLE_ADMIN")){
                VerificationTokenAdmin verificationTokenAdmin = new VerificationTokenAdmin(user, token);
                verificationTokenAdminRepo.save(verificationTokenAdmin);

                String confirmationUrlUAdmin = "http://" + request.getServerName() + ":" + request.getServerPort() +
                        request.getContextPath() + "/verify-token-admin?token=" + token;

                mailSenderService.sendMail(email_admin,
                        "Verification Token Admin",
                        confirmationUrlUAdmin,
                        false);
                appUserRepo.findByUsername(user.getUsername()).get().getRoles().remove("ROLE_ADMIN");
                appUserRepo.save(user);
            }

            VerificationToken verificationToken = new VerificationToken(user, token);
            verificationTokenRepo.save(verificationToken);

            String confirmationUrlUser = "http://" + request.getServerName() + ":" + request.getServerPort() +
                    request.getContextPath() + "/verify-token?token=" + token;

            mailSenderService.sendMail(user.getUsername(),
                    "Verification Token",
                    confirmationUrlUser,
                    false);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public void verifyTokenUser(String token) {
        AppUser appUser = verificationTokenRepo.findByValue(token).getAppUser();
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
        LOGGER.info("ROLE USER HAS BEEN ACTIVATED");
        verificationTokenRepo.deleteByValue(token);
        LOGGER.info("ACTIVATED TOKEN HAS BEEN REMOVED");
    }

    public void verifyTokenAdmin(String token){
        AppUser appUser = verificationTokenAdminRepo.findByValue(token).getAppUser();
        appUser.getRoles().add("ROLE_ADMIN");
        appUser.setEnabled(true);
        appUserRepo.save(appUser);
        LOGGER.info("ROLE ADMIN HAS BEEN ACTIVATED");
        verificationTokenAdminRepo.deleteByValue(token);
        LOGGER.info("ACTIVATED TOKEN HAS BEEN REMOVED");
    }
}
