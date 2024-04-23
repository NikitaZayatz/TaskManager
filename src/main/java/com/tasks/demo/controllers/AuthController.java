package com.tasks.demo.controllers;


import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.models.Task;
import com.tasks.demo.repositories.TaskRepository;
import com.tasks.demo.repositories.UserRepository;
import com.tasks.demo.services.RegistrationService;
import com.tasks.demo.services.TaskDetailService;
import com.tasks.demo.util.PersonValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;


@RequestMapping("/auth")
@Controller
public class AuthController {


    private final TaskDetailService taskDetailService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    Set<Task> newTask = new HashSet<>();
    Set<AplicationUser> newPerson = new HashSet<>();
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public AuthController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }





    @GetMapping("/login")
    public String login()
    {
        return "/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("customer") AplicationUser aplicationUser) {
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("customer") AplicationUser aplicationUser,
                                      BindingResult bindingResult) {
        personValidator.validate(aplicationUser, bindingResult);

        if (bindingResult.hasErrors())
            return "/auth/registration";
        registrationService.register(aplicationUser);

        return "redirect:/auth/login";
    }


    @GetMapping("/homePage")
    public String redirection(){
        AplicationUser person = userRepository.findByUsername(getCurrentUsername());

        if(person.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))){
            return "redirect:/auth/adminPage";
        }
        else if(person.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("USER"))){

            return "redirect:/auth/userPage";
        } else if (person.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMINISTRATION"))) {
            return "redirect:/auth/administrationPage";
        }
        return null;

    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null)
        {
            return authentication.getName();
        }
        return null;
    }

}
