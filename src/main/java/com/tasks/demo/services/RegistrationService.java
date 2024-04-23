package com.tasks.demo.services;

import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.models.Role;
import com.tasks.demo.repositories.RoleRepository;
import com.tasks.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {


    private final RoleRepository roleRepository;
    private final UserRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(RoleRepository roleRepository, UserRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(AplicationUser person) {
        Role role = roleRepository.findByAuthority("USER");
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);
        person.setAuthorities(authorities);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
}