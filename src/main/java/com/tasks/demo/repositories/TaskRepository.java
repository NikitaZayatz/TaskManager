package com.tasks.demo.repositories;

import com.tasks.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Override
    List<Task> findAll();
}
