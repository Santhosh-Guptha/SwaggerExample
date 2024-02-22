package com.fusionsauth.serviceimpl;

import com.fusionsauth.configuration.SecurityConfiguration;
import com.fusionsauth.entities.UserDetailsManager;
import com.fusionsauth.exceptions.UserDetailsIncorrectException;
import com.fusionsauth.exceptions.UserDuplicateException;
import com.fusionsauth.repositories.UserRepositories;
import com.fusionsauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityConfiguration configuration;

    @Override
    public UserDetailsManager saveUser(UserDetailsManager userDetails) throws Exception {
        userDetails.setRole("ROLE_USER");
        userDetails.setPassword("Encrypted : "+passwordEncoder.encode(userDetails.getPassword()));
        if(validateUser(userDetails)){
            throw new UserDuplicateException("UserName or PhoneNumber or Email already Exist");
        }
        userRepositories.save(userDetails);
        return userDetails;
    }


    @Override
    public List<UserDetailsManager> getAllUsers() {
        return userRepositories.findAll();
    }

    @Override
    public UserDetailsManager getUserById(int id) throws Exception {
        UserDetailsManager userDetails = userRepositories.findById(id).orElseThrow(() -> new Exception(""));
        userDetails.setPassword(userDetails.getPassword());
        return userDetails;
    }

    @Override
    public Optional<UserDetailsManager> getUserByUserName(String userName) {
        return userRepositories.findByUserName(userName);

    }

    @Override
    public UserDetailsManager updateUserDetails(int id, UserDetailsManager userDetails) {
        UserDetailsManager userDetails1 = userRepositories.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found by id: "+ id));
        userDetails1.setUserName(userDetails.getUserName());
        userDetails1.setFirstName(userDetails.getFirstName());
        userDetails1.setLastName(userDetails.getLastName());
        userDetails1.setDescription(userDetails1.getDescription());
        userDetails1.setPhoneNumber(userDetails.getPhoneNumber());
        if(validateUser(userDetails)){
            throw new UserDuplicateException("UserName or PhoneNumber or Email already Exist");
        }
        userRepositories.save(userDetails);
        return null;
    }

    @Override
    public boolean validateUser(UserDetailsManager userDetails) {
        return userRepositories.findByEmail(userDetails.getEmail()).isPresent() ||
                userRepositories.findByPhoneNumber(userDetails.getPhoneNumber()).isPresent() ||
                userRepositories.findByUserName(userDetails.getUserName()).isPresent();
    }

    @Override
    public UserDetailsManager deleteUserById(int id) {
        UserDetailsManager userDetailsManager = userRepositories.findById(id).orElseThrow(()->new UsernameNotFoundException("Username Not Found By the given id..."));
        userRepositories.delete(userDetailsManager);
        return userDetailsManager;
    }

    @Override
    public String changePassword(String oldPassword, String newPassword) throws Exception {
        UserDetailsManager userDetailsManager = userRepositories.findByUserName(getCurrentUsername())
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found."));

        String storedPassword = userDetailsManager.getPassword().substring(12); // Fetching stored password

        // Checking if the encoded old password matches the stored password
        if (!passwordEncoder.matches(oldPassword, storedPassword)) {
            throw new UserDetailsIncorrectException("Old password is incorrect.");
        } else {
            // Setting the new encoded password
            userDetailsManager.setPassword("Encrypted : "+passwordEncoder.encode(newPassword));
            userRepositories.save(userDetailsManager); // Saving the updated user details
        }

        return "Password Successfully changed.";
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Returns the username of the authenticated user
        }
        return null; // No user authenticated or anonymous user
    }


}
