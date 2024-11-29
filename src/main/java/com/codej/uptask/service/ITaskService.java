package com.codej.uptask.service;
import com.codej.uptask.entity.Task;
import com.codej.uptask.enums.Status;

import java.util.List;

public interface ITaskService {

    List<Task> findAll();
    Task save(Task project);

    Task addTask(Task task, String id);

    List<Task> findByProjectId(String id);

     void updateStatusProject(String id);
    Task findById(String id);
    Task update(Status status, String id);
    Task updateTask(Task task, String id);

    void delete(String id);
}
