package com.tasks.demo.util;

import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {


    @Autowired
    private final UserService personDetailsService;


    public PersonValidator(UserService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AplicationUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AplicationUser person = (AplicationUser) o;

        try {
         //   personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }

         return;
    }
}
