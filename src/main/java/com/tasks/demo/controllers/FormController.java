package com.tasks.demo.controllers;


import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.models.Task;
import com.tasks.demo.repositories.FileRepository;
import com.tasks.demo.repositories.TaskRepository;
import com.tasks.demo.repositories.UserRepository;
import com.tasks.demo.services.RegistrationService;
import com.tasks.demo.services.TaskDetailService;
import com.tasks.demo.util.PersonValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.tasks.demo.models.TaskStatus.*;

@Controller
public class FormController {

    private final TaskDetailService taskDetailService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    List<Task> newTask = new ArrayList<>();
    List<AplicationUser> newPerson = new ArrayList<>();
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;


    @Autowired
    public FormController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @PostMapping("/auth/taskToProcessRight/{id}")
    public String taskToProcessRight(@PathVariable int id) {
        Task task = taskDetailService.get(id);
        task.setStatus(IN_PROGRESS);
        taskRepository.save(task);
        return "redirect:/auth/index";
    }

    @PostMapping("/auth/taskToDoLeft/{id}")
    public String taskToDoLeft(@PathVariable int id) {
        Task task = taskDetailService.get(id);
        task.setStatus(NOT_DONE);
        taskRepository.save(task);
        return "redirect:/auth/index";
    }

    @PostMapping("/auth/taskToDoNERight/{id}")
    public String taskToDoNERight(@PathVariable int id) {
        Task task = taskDetailService.get(id);
        task.setStatus(DONE);
        taskRepository.save(task);
        return "redirect:/auth/index";
    }



    @PostMapping("/auth/taskToProcessLeft/{id}")
    public String taskToProcessLeft(@PathVariable int id) {
        Task task = taskDetailService.get(id);
        task.setStatus(IN_PROGRESS);
        taskRepository.save(task);
        return "redirect:/auth/index";
    }


    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }


}
