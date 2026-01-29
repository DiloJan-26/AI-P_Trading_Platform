package com.eztrad.servercomp.service;

import com.eztrad.servercomp.model.User;
import com.eztrad.servercomp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Step 21 - here we give our username, password for logins to override/disable spring security password generation
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Step 23 - user,password setup from repo/db
        User user = userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorityList=new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorityList
        );
    }
}
