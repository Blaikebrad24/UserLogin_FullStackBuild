package com.UserLoginDemo.UserLoginBuild.registration;

import com.UserLoginDemo.UserLoginBuild.appuser.AppUser;
import com.UserLoginDemo.UserLoginBuild.appuser.AppUserRole;
import com.UserLoginDemo.UserLoginBuild.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private EmailValidator emailValidator;
    private final AppUserService appUserService;

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
}
