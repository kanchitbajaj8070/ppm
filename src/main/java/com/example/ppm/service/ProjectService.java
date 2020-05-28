package com.example.ppm.service;

import com.example.ppm.domain.Backlog;
import com.example.ppm.domain.Project;
import com.example.ppm.domain.ProjectTask;
import com.example.ppm.exceptions.ProjectIdException;
import com.example.ppm.repository.BacklogRepository;
import com.example.ppm.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private BacklogRepository backlogRepository;
@Autowired
    private ProjectRepository projectRepository;
public void saveOrUpdateProject( Project project)
{
try
{  project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
if(project.getId()==null)
{//backlog sets only on creation
    Backlog b= new Backlog();
    b.setProject(project);
    project.setBacklog(b);
    b.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
}
else//updating
{
    project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
}
projectRepository.save(project);
}
catch (Exception e)
{
    throw new ProjectIdException("Project Id"+ project.getProjectIdentifier().toUpperCase()+"already Exists.");
}

}
public List<Project> getAllProjects()
{
    List<Project> projects= new ArrayList<>();
    for( Project p:projectRepository.findAll())
        projects.add(p);
    return projects;
}
public Project getProjectByIdentifier( String projectId)
{

    Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    if( project==null)
        throw new ProjectIdException( projectId+" does not exists");
    return project;
}
public void deleteProjectByIdentifer( String projectId)
{
    Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    if( project==null)
        throw new ProjectIdException( " Cant delete project"+projectId+" as it doesnt exisits");
    projectRepository.delete(project);
}

}
