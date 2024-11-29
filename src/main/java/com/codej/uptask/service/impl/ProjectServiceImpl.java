package com.codej.uptask.service.impl;

import com.codej.uptask.entity.Project;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.enums.Status;
import com.codej.uptask.repository.IProjectRepository;
import com.codej.uptask.service.IProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final IProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project save(Project project, UserEntity user) {
        UUID uuid= UUID.randomUUID();
        String id = uuid.toString();
        project.setId(id);
        project.setStatus(Status.NEW);
        project.setUser(user);
        return projectRepository.save(project);
    }

    @Override
    public Project findById(String id) {
        return projectRepository.findById(id).orElseThrow();
    }

    @Override
    public Project update(Project project, String id) {

       Project project1= this.findById(id);
       project1.setName(project.getName());
       project1.setStatus(project.getStatus());

        return projectRepository.save(project1);
    }

    @Override
    public void delete(String id) {
        projectRepository.deleteById(id);
    }
}
