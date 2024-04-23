package com.tasks.demo.repositories;

import com.tasks.demo.models.Document;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface FileRepository extends JpaRepository<Document, Long> {
    @Override
    Optional<Document> findById(Long aLong);

    @Override
    List<Document> findAll();
}