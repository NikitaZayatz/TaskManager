package com.tasks.demo.controllers;



import com.tasks.demo.models.AplicationUser;
import com.tasks.demo.models.Document;
import com.tasks.demo.models.Task;
import com.tasks.demo.repositories.FileRepository;
import com.tasks.demo.repositories.TaskRepository;
import com.tasks.demo.repositories.UserRepository;
import com.tasks.demo.services.RegistrationService;
import com.tasks.demo.services.TaskDetailService;
import com.tasks.demo.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TaskController {

    public static int taskid;

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
    private FileRepository fileRepository;




    public TaskController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }



 /*   @GetMapping("/auth/taskPage")
    public String getImage(Model model) {

        return "/auth/taskPage";
    }*/


    @PostMapping("/auth/taskOpen/{id}")
    public String taskOpen(@PathVariable("id") int id, Model model) {
        Task currentTask = taskDetailService.get(id);
        List<Document> docs= fileRepository.findAll();
        model.addAttribute("files", docs);
        model.addAttribute("currentTask", currentTask);
        return "/auth/taskPage";
    }

}