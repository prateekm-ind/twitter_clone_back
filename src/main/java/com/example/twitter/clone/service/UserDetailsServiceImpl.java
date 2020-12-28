package com.example.twitter.clone.service;

import com.example.twitter.clone.entity.RegisteredUser;
import com.example.twitter.clone.repository.RegisteredUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RegisteredUserRepository registeredUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<RegisteredUser> user = registeredUserRepository.findByUsername(username);
        RegisteredUser registeredUser = user.orElseThrow(() -> new UsernameNotFoundException("Username Not Found!"));

        return new User(registeredUser.getUsername(),registeredUser.getPassword(),true,true,true,true, getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
