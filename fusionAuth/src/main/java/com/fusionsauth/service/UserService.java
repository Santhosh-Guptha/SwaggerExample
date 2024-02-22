package com.fusionsauth.service;

import com.fusionsauth.entities.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    UserDetailsManager saveUser(UserDetailsManager userDetails) throws Exception;

    List<UserDetailsManager> getAllUsers();

    UserDetailsManager getUserById(int id) throws Exception;

    Optional<UserDetailsManager> getUserByUserName(String userName);

    UserDetailsManager updateUserDetails(int id,UserDetailsManager userDetails);

    boolean validateUser(UserDetailsManager userDetails);

    UserDetailsManager deleteUserById(int id);

    String changePassword(String oldPassword, String newPassword) throws Exception;

}
