package com.example.ppm.repository;

import com.example.ppm.domain.Backlog;
import com.example.ppm.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {

public Iterable<ProjectTask> getAllByProjectIdentifierOrderByPriority( String id);
    public Iterable<ProjectTask> getAllByBacklogOrderByPriority( Backlog b);
    public ProjectTask findFirstByProjectSequence(String projectSequence);
    public ProjectTask deleteByProjectSequence(String projectSequence);
}
