package com.UserLoginDemo.UserLoginBuild.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    //expose ability to save a confirmation token
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token)
    {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token)
    {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token)
    {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
