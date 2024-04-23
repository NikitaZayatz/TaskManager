package com.tasks.demo.repositories;

import com.tasks.demo.models.AplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AplicationUser, Integer> {
   AplicationUser findByUsername(String username);
}
