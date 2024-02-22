package com.fusionsauth.repositories;

import com.fusionsauth.entities.UserDetailsManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<UserDetailsManager,Integer> {

    Optional<UserDetailsManager> findByUserName(String userName);

    Optional<Object> findByPhoneNumber(String phoneNumber);

    Optional<Object> findByEmail(String email);

}
