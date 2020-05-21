package com.example.ppm.repository;

import com.example.ppm.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {
    public Project findByProjectIdentifier(String projectIdentifier);

}
