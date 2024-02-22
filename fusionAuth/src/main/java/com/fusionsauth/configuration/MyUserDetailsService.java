package com.fusionsauth.configuration;

import com.fusionsauth.entities.UserDetailsManager;
import com.fusionsauth.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsManager> optional = userRepositories.findByUserName(username);
        optional.orElseThrow(()->new UsernameNotFoundException("Username not found..."));
        return optional.map(MyUserDetailsManager::new).get();
    }
}
