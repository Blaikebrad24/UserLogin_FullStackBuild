package com.UserLoginDemo.UserLoginBuild.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator  implements Predicate<String> {
    private EmailValidator emailValidator;

    @Override
    public boolean test(String s){
        // TODO: Regex to validate email
        return true;
    }
}
