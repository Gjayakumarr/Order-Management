package com.ordermanagement.users.security.service;

import com.ordermanagement.users.repository.UserRepository;
import com.ordermanagement.users.security.model.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> userDetails = userRepository.findByEmail(username);

        if (!userDetails.isEmpty()) {
            if (!userDetails.get("is_deleted").equals(true)) {
            	GrantedAuthority defaultAuthority = new SimpleGrantedAuthority("ROLE_USER");
            	Set<GrantedAuthority> authorities = Set.of(defaultAuthority);
                return new UserDetailsImpl(
                        (Integer) userDetails.get("user_id"),
                        (String) userDetails.get("first_name"),
                        (String) userDetails.get("middle_name"),
                        (String) userDetails.get("last_name"),
                        (String) userDetails.get("username"),
                        (String) userDetails.get("password"),
                        (Long) userDetails.get("contact_number"),
                        (Integer) userDetails.get("manager_id"),
                        (Integer) userDetails.get("department_id"),
                        0,
                        (Boolean) userDetails.get("is_deleted"),
                        authorities
                );
            } else {
                System.err.println("Account Locked due to Account deleted");

                throw new LockedException("Account Locked due to Account deleted");
            }
        } else {
            System.err.println("Wrong username or password");

            throw new BadCredentialsException("Wrong username or password");
        }

    }
}
