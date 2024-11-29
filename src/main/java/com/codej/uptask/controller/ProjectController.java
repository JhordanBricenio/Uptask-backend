package com.codej.uptask.controller;


import com.codej.uptask.controller.dto.MessageDTO;
import com.codej.uptask.entity.Project;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.service.IProjectService;
import com.codej.uptask.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProjectController {

    private  final IProjectService projectService;
    private final IUserService userService;

    @GetMapping("/project/findAll")
    List<Project> findAll(){
       return projectService.findAll();
    }

    @PostMapping("/project/save")
    ResponseEntity<Project> save(@RequestBody Project project,
                                 @RequestHeader("Authorization") String token){

        String extractToken =  extractToken(token);
        
        UserEntity user = userService.findUserProfileByJwt(extractToken);
        Project projectNew= projectService.save(project, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectNew);
    }

    @GetMapping("/project/findById/{id}")
    Project findById( @PathVariable String id){
        return projectService.findById(id);
    }

    @PutMapping("/project/update/{id}")
    Project update(@RequestBody Project project, @PathVariable String id){
        return  projectService.update(project, id);
    }

    @DeleteMapping("/project/delete/{id}")
    ResponseEntity<?> deleteById(@PathVariable String id){
        projectService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Proyecto eliminado"), HttpStatus.OK);
    }

    public String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
