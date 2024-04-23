package com.tasks.demo.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tasks.demo.models.Task;
import com.tasks.demo.repositories.TaskRepository;
import com.tasks.demo.repositories.UserRepository;
import com.tasks.demo.services.RegistrationService;
import com.tasks.demo.services.TaskDetailService;
import com.tasks.demo.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.tasks.demo.models.TaskStatus.*;



@Controller
public class SortController {


    private final TaskDetailService taskDetailService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;


    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public SortController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }




    @PostMapping("/indexTodo")
    public String indexTodo() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        List<Task> menuList = taskRepository.findAll();
        List<Task> todo = new ArrayList<>();
        List<Task> in_process = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for(Task task : menuList) {
            if(task.getStatus() == NOT_DONE){
                todo.add(task);
            }
            if(task.getStatus() == IN_PROGRESS){
                in_process.add(task);
            }
            if(task.getStatus() == DONE){
                done.add(task);
            }

        }
        Collections.sort(todo, Comparator.comparing(Task::getDeadline));



        return mapper.writeValueAsString(menuList);
    }

    @PostMapping("/indexInprocess")
    public String indexInprocess(Model model) {
        List<Task> menuList = taskRepository.findAll();
        List<Task> todo = new ArrayList<>();
        List<Task> in_process = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for(Task task : menuList) {
            if(task.getStatus() == NOT_DONE){
                todo.add(task);
            }
            if(task.getStatus() == IN_PROGRESS){
                in_process.add(task);
            }
            if(task.getStatus() == DONE){
                done.add(task);
            }
        }
        Collections.sort(in_process, Comparator.comparing(Task::getDeadline));
        model.addAttribute("todo",todo);
        model.addAttribute("in_process",in_process);
        model.addAttribute("done",done);
        return "/auth/index";
    }

    @PostMapping("/indexDone")
    public String indexDone(Model model) {
        List<Task> menuList = taskRepository.findAll();
        List<Task> todo = new ArrayList<>();
        List<Task> in_process = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for(Task task : menuList) {
            if(task.getStatus() == NOT_DONE){
                todo.add(task);
            }
            if(task.getStatus() == IN_PROGRESS){
                in_process.add(task);
            }
            if(task.getStatus() == DONE){
                done.add(task);
            }
        }
        Collections.sort(done, Comparator.comparing(Task::getDeadline));
        model.addAttribute("todo",todo);
        model.addAttribute("in_process",in_process);
        model.addAttribute("done",done);
        return "/auth/index";
    }


    @PostMapping("/indexInProcessExecutor")
    public String indexInProcessExecutor(Model model) {
        List<Task> menuList = taskRepository.findAll();
        List<Task> todo = new ArrayList<>();
        List<Task> inProcess = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for(Task task : menuList) {
            if(task.getStatus() == NOT_DONE){
                todo.add(task);
            }
            if(task.getStatus() == IN_PROGRESS){
                inProcess.add(task);
            }
            if(task.getStatus() == DONE){
                done.add(task);
            }
        }
        Collections.sort(inProcess, Comparator.comparing(Task::getUsers).reversed());
        model.addAttribute("todo",todo);
        model.addAttribute("in_process",inProcess);
        model.addAttribute("done",done);
        return "/auth/index";
    }



    @PostMapping("/indexToDoExecutor")
    public String indexToDoExecutor(Model model) {
        List<Task> menuList = taskRepository.findAll();
        List<Task> todo = new ArrayList<>();
        List<Task> inProcess = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for(Task task : menuList) {
            if(task.getStatus() == NOT_DONE){
                todo.add(task);
            }
            if(task.getStatus() == IN_PROGRESS){
                inProcess.add(task);
            }
            if(task.getStatus() == DONE){
                done.add(task);
            }
        }
        Collections.sort(todo, Comparator.comparing(Task::getUsers).reversed());
        model.addAttribute("todo",todo);
        model.addAttribute("in_process",inProcess);
        model.addAttribute("done",done);
        return "/auth/index";
    }



    @PostMapping("/indexDoneExecutor")
    public String indexDoneExecutor(Model model) {
        List<Task> menuList = taskRepository.findAll();
        List<Task> todo = new ArrayList<>();
        List<Task> inProcess = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for(Task task : menuList) {
            if(task.getStatus() == NOT_DONE){
                todo.add(task);
            }
            if(task.getStatus() == IN_PROGRESS){
                inProcess.add(task);
            }
            if(task.getStatus() == DONE){
                done.add(task);
            }
        }
        Collections.sort(done, Comparator.comparing(Task::getUsers).reversed());
        model.addAttribute("todo",todo);
        model.addAttribute("in_process",inProcess);
        model.addAttribute("done",done);
        return "/auth/index";
    }





    @PostMapping("/search")
    public String search(@RequestParam("myString")String keyword, Model model) {
        List<Task> menuList = taskRepository.findAll();

        List<Task> todo = new ArrayList<>();
        List<Task> in_process = new ArrayList<>();
        List<Task> done = new ArrayList<>();
        for (Task task : menuList) {
            if(task.getName().toLowerCase().contains(keyword.toLowerCase()))
            {
                if(task.getStatus() == NOT_DONE){
                    todo.add(task);
                }
                if(task.getStatus() == IN_PROGRESS){
                    in_process.add(task);
                }
                if(task.getStatus() == DONE){
                    done.add(task);
                }
            }

        }

        model.addAttribute("todo",todo);
        model.addAttribute("in_process",in_process);
        model.addAttribute("done",done);
        return "/auth/index";

    }

}
