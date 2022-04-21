package com.UserLoginDemo.UserLoginBuild.appuser;

import com.UserLoginDemo.UserLoginBuild.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository  extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
