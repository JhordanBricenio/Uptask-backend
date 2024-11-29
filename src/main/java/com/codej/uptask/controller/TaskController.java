package com.codej.uptask.controller;

import com.codej.uptask.controller.dto.MessageDTO;
import com.codej.uptask.entity.Project;
import com.codej.uptask.entity.Task;
import com.codej.uptask.enums.Status;
import com.codej.uptask.service.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TaskController {

    private final ITaskService taskService;

    @GetMapping("/task/findAll")
    List<Task> findAll(){
        return taskService.findAll();
    }

    @PostMapping("/task/add")
    ResponseEntity<Task>  addTask(@RequestBody Task task,
                                  @RequestParam String idProject){
        Task taskNew= taskService.addTask(task, idProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/task/findByProjectId/{id}")
    List<Task> findByProjectId(@PathVariable String id){
        return taskService.findByProjectId(id);
    }

    @GetMapping("/task/findById/{id}")
    Task findById( @PathVariable String id){
        return taskService.findById(id);
    }


    @PutMapping("/task/{taskId}/status")
    public ResponseEntity<Task> updateStatusProject(
            @PathVariable String taskId,
            @RequestParam Status status
    ) {
        Task taskUpdate = taskService.update(status, taskId);
        return ResponseEntity.ok(taskUpdate);
    }

    @PutMapping("/task/update/{id}")
    public ResponseEntity<Task> updateNameDescription(
           @RequestBody Task task, @PathVariable String id
    ) {
        Task taskUpdate = taskService.updateTask(task, id);
        return ResponseEntity.ok(taskUpdate);
    }

    @DeleteMapping("/task/delete/{id}")
    ResponseEntity<?> deleteById(@PathVariable String id){
        taskService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Tarea eliminada con éxito"), HttpStatus.OK);
    }
}
