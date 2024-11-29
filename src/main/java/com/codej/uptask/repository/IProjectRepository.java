package com.codej.uptask.repository;

import com.codej.uptask.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProjectRepository extends JpaRepository<Project, String> {
}
