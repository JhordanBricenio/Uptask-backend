package com.codej.uptask.repository;

import com.codej.uptask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, String> {

    List<Task> findByProjectId(String id);
}
