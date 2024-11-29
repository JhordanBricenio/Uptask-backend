package com.codej.uptask.service.impl;

import com.codej.uptask.entity.Project;
import com.codej.uptask.entity.Task;
import com.codej.uptask.enums.Status;
import com.codej.uptask.repository.IProjectRepository;
import com.codej.uptask.repository.ITaskRepository;
import com.codej.uptask.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository taskRepository;

    private final IProjectRepository projectRepository;
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) {
        UUID uuid= UUID.randomUUID();
        String id = uuid.toString();
        task.setId(id);
        return taskRepository.save(task);
    }

    @Override
    public Task addTask(Task task, String projectId) {
        UUID uuid = UUID.randomUUID(); // Genera el UUID
        task.setId(uuid.toString());

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        task.setProject(project);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> findByProjectId(String id) {
        return taskRepository.findByProjectId(id);
    }

    @Override
    public void updateStatusProject(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        List<Task> tasks = project.getTasks();
        boolean allMatch = tasks.stream().allMatch(t -> t.getStatusTask() == Status.DONE);
        boolean anyMatch = tasks.stream().anyMatch(t -> t.getStatusTask() == Status.DOING);

        if (allMatch) {
            project.setStatus(Status.DONE);
        } else if (anyMatch) {
            project.setStatus(Status.DOING);
        } else {
            project.setStatus(Status.NEW);
        }
        projectRepository.save(project);
    }

    @Override
    public Task findById(String id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public Task update(Status status, String idTask) {

        Task task1 = taskRepository.findById(idTask).orElseThrow();
        task1.setStatusTask(status);
        taskRepository.save(task1);
        updateStatusProject(task1.getProject().getId());
        
        return task1;
    }

    @Override
    public Task updateTask(Task task, String id) {
        Task task1 = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Errroror no encontrado")
        );
        task1.setName(task.getName());
        task1.setDescription(task.getDescription());

         return taskRepository.save(task1);
    }


    @Override
    public void delete(String id) {
        taskRepository.deleteById(id);
    }
}
