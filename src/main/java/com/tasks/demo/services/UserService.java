package com.tasks.demo.services;

import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository peopleRepository;


    public UserService(UserRepository peopleRepository) {
        this.peopleRepository = peopleRepository;

    }

    public AplicationUser findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }



    public AplicationUser get(int id) {
        return peopleRepository.findById(id).get();
    }

    public List<AplicationUser> listAll() {
        return peopleRepository.findAll();
    }



    public void deletePersonById(int id) {
        peopleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AplicationUser user = findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities().stream().map(role->new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet()));
    }
}