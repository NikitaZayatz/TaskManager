package com.tasks.demo.services;

import com.tasks.demo.models.Task;
import com.tasks.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDetailService  implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    private final TaskRepository taskRepository;

    @Autowired
    public TaskDetailService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    public Task get(int id) {
        return taskRepository.findById(id).get();
    }

}
