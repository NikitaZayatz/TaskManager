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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

import static com.tasks.demo.models.TaskStatus.*;

@Controller
@RequestMapping("/auth")
public class AdminController {

    private final TaskDetailService taskDetailService;
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    List<Task> newTask = new ArrayList<>();
    Set<AplicationUser> newPerson = new HashSet<>();
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    public AdminController(TaskDetailService taskDetailService, RegistrationService registrationService, PersonValidator personValidator) {
        this.taskDetailService = taskDetailService;
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    @GetMapping("/adminPage")
    public String admin(Model model)
    {

        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        List<AplicationUser> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("Status", TaskStatus.getAll());
        return "/auth/adminPage";
    }




    public void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cookieName", null); // Создаем куку с тем же именем, но со значением null
        cookie.setMaxAge(0); // Устанавливаем срок действия куки на 0 секунд (немедленное удаление)
        cookie.setPath("/"); // Устанавливаем путь куки на корневой путь

        response.addCookie(cookie); // Добавляем куку в ответ
    }


    @GetMapping( "/index" )
    public String  TransferPage(Model model) {

        String role = "USER";
        AplicationUser person = userRepository.findByUsername(getCurrentUsername());
        List<Task> menuList;
        if(person.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ADMIN"))){
            menuList = taskRepository.findAll();
        }
        else if(person.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("USER"))){

             menuList = userRepository.findByUsername(getCurrentUsername()).getTasks();

        } else  {
             role = "ADMININSTRATION";
             menuList = taskRepository.findAll();
        }



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
        model.addAttribute("role", role);
        model.addAttribute("todo",todo);
        model.addAttribute("in_process",in_process);
        model.addAttribute("done",done);
        return "/auth/index";
    }



    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null)
        {
            return authentication.getName();
        }
        return null;
    }



    @GetMapping("/userPage")
    public String userPage(Model model) {
        AplicationUser user = userRepository.findByUsername(getCurrentUsername());
        List<Task> menuList =  user.getTasks();
        model.addAttribute("taskList", menuList);
        return "/auth/userPage";
    }



    @PostMapping("/addTaskToUser/{id}")
    public String addTaskToUser(@PathVariable int id,Model model) {
        Task task = taskDetailService.get(id);
        AplicationUser person = userRepository.findByUsername(getCurrentUserName());
        newTask.add(task);
        person.setTasks(newTask);
        newPerson.add(person);
        task.setUsers(newPerson);
        taskRepository.save(task);
        userRepository.save(person);



        return "redirect:/auth/index";
    }


    public String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deletePersonById(id);
        return "redirect:/admin/admin";
    }

    @GetMapping("/changePerson")
    public String adminUpdateDataProcedure(@ModelAttribute("person") AplicationUser newPerson)
    {
        userRepository.save(newPerson);
        return "redirect:/auth/adminPage";
    }


}


