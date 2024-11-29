package com.codej.uptask.service;

import com.codej.uptask.entity.Project;
import com.codej.uptask.entity.Task;
import com.codej.uptask.entity.UserEntity;

import java.util.List;

public interface IProjectService {
   List<Project> findAll();

   Project save(Project project, UserEntity user);

   Project findById(String id);
   Project update(Project project, String id);

   void delete(String id);

}
