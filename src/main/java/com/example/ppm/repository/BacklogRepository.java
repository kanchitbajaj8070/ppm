package com.example.ppm.repository;

import com.example.ppm.domain.Backlog;
import com.example.ppm.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog,Long> {

    public Backlog findByProjectIdentifier( String id);
}
