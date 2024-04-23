package com.tasks.demo.controllers;


import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.models.Task;
import com.tasks.demo.models.TaskStatus;
import com.tasks.demo.repositories.TaskRepository;
import com.tasks.demo.repositories.UserRepository;
import com.tasks.demo.services.RegistrationService;
import com.tasks.demo.services.TaskDetailService;
import com.tasks.demo.services.UserService;
import com.tasks.demo.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.tasks.demo.models.TaskStatus.NOT_DONE;

@Controller
public class AdministationController {

    private final TaskDetailService taskDetailService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    Set<Task> newTask = new HashSet<>();
    Set<AplicationUser> newPerson = new HashSet<>();
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public AdministationController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }




    @GetMapping("/auth/administrationPage")
    public String administrationPage(Model model) {
        Task task = new Task();

        List<AplicationUser> users = userRepository.findAll();
        model.addAttribute("task", task);
        model.addAttribute("users", users);
        return "/auth/administrationPage";
    }



    @PostMapping("/auth/administrationAddData")
    public String administrationAddData(@ModelAttribute("task") Task newTask)
    {
        newTask.setStatus(NOT_DONE);
        taskRepository.save(newTask);
        return "redirect:/auth/index";
    }
}
