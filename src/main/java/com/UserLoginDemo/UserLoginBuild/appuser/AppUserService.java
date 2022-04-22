package com.UserLoginDemo.UserLoginBuild.appuser;

import com.UserLoginDemo.UserLoginBuild.appuser.AppUserRepository;
import com.UserLoginDemo.UserLoginBuild.registration.token.ConfirmationToken;
import com.UserLoginDemo.UserLoginBuild.registration.token.ConfirmationTokenRepository;
import com.UserLoginDemo.UserLoginBuild.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

//how to find the users once login
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    //user name is email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) throws IllegalAccessException {
        //check if user exits
        boolean userExits = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if(userExits){
            throw new IllegalAccessException("email is already taken");
        }
        //proceed
        String encodedPassword =  bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        //TODO: Send confirmation token
        // create a token then save it
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
                );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }
}
