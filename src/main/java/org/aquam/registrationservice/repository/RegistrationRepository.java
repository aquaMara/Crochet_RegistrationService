package org.aquam.registrationservice.repository;

import org.aquam.registrationservice.model.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<NewUser, Long> {
    Optional<NewUser> findNewUsersByEmail(String email);
    Optional<NewUser> findNewUsersByUsername(String username);
}
