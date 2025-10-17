//package com.template.spring.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.Optional;
//
//@Component
//public class UserDetailService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserEntity> userRes = userRepo.findByUsername(username);
//
//        if (userRes.isEmpty())
//            throw new UsernameNotFoundException("No user found with this username " + username);
//        UserEntity userEntity = userRes.get();
//        return new
//                org.springframework.security.core.userdetails.User(
//                username,
//                userEntity.getPassword(),
//                Collections.singletonList(
//                        new SimpleGrantedAuthority("ROLE_USER")
//                )
//        );
//    }
//}
