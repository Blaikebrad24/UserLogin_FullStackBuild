package com.UserLoginDemo.UserLoginBuild.registration;

import com.UserLoginDemo.UserLoginBuild.appuser.AppUser;
import com.UserLoginDemo.UserLoginBuild.appuser.AppUserRole;
import com.UserLoginDemo.UserLoginBuild.appuser.AppUserService;
import com.UserLoginDemo.UserLoginBuild.registration.token.ConfirmationToken;
import com.UserLoginDemo.UserLoginBuild.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) throws IllegalAccessException {
        //check if the email is valid
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("Email is not valid");
        }
        //need a method to sign up users in AppService
        return appUserService.signUpUser(new AppUser(request.getFirstName(),
                                        request.getLastName(),request.getEmail(),
                                        request.getPassword(), AppUserRole.USER));
    }

    @Transactional
    public String confirmToken(String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenService
                                                .getToken(token)
                                                .orElseThrow(() -> new IllegalStateException("token not found"));
        if(confirmationToken.getConfirmedAT() != null)
        {
            throw new IllegalStateException(("email already confirmed"));
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAT();
        if(expiredAt.isBefore(LocalDateTime.now()))
        {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
