package com.adith.smartcontactmanager.config;

import com.adith.smartcontactmanager.entities.User;
import com.adith.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {


@Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetching user from database
        User user=userRepository.findUserByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("could not found user");
        }
        UserDetailsImpl userDetailsimpl=new UserDetailsImpl(user);
        return userDetailsimpl;
    }

}
