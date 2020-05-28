package com.example.ppm.service;

import com.example.ppm.domain.Backlog;
import com.example.ppm.domain.Project;
import com.example.ppm.domain.ProjectTask;
import com.example.ppm.exceptions.ProjectNotFoundException;
import com.example.ppm.repository.BacklogRepository;
import com.example.ppm.repository.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTaskService {

@Autowired
   private  BacklogRepository backlogRepository;
@Autowired
    private ProjectTaskRepository projectTaskRepository;
public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {// Id=abcd
    //we want taks like->
    //abcd-1, abcd-2 ,abcd-3
    // update the backlog sequence on addintion
    //Intial priority if intial priority is null
    //status has to be set indtially when
    projectIdentifier.toUpperCase();
    Backlog backlog = null;
    backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
    if (backlog == null)
        throw new ProjectNotFoundException("Project "+projectIdentifier+" Not found");
    else {
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence() == null ? 0 : backlog.getPTSequence();
        projectTask.setProjectSequence(projectIdentifier + "-" + (backlogSequence + 1));
        backlog.setPTSequence(backlogSequence + 1);
        projectTask.setProjectIdentifier(projectIdentifier);
        if (projectTask.getPriority() == null || (projectTask.getPriority() != null && projectTask.getPriority() == 0))
            projectTask.setPriority(3);
        if (projectTask.getStatus() == null || (projectTask.getStatus() != null && projectTask.getStatus().equals("")))
            projectTask.setStatus("TO_DO");
        return projectTaskRepository.save(projectTask);
    }
}
public List<ProjectTask> getAllTasksById( String projectId)
{List<ProjectTask> projectTasks= new ArrayList<>();
Backlog backlog= backlogRepository.findByProjectIdentifier(projectId);
    if (backlog == null)
        throw new ProjectNotFoundException("Project "+projectId+" Not found");
for( ProjectTask task:projectTaskRepository.getAllByBacklogOrderByPriority(backlog))
    projectTasks.add(task);
return projectTasks;
}
    public ProjectTask findProjectTaskBySequence( String backlogId,String projectSequence)
    {//project exists
        //project taks exist
        // they should be releated to each other
        Backlog backlog=backlogRepository.findByProjectIdentifier(backlogId);
        if( backlog==null)
            throw new ProjectNotFoundException("Project "+backlogId+" Not found");
        ProjectTask projectTask=projectTaskRepository.findFirstByProjectSequence(projectSequence);
        if( projectTask==null)
            throw new ProjectNotFoundException("Project Task With "+projectSequence+" Not found");
       if( !projectTask.getBacklog().equals(backlog))
           throw new ProjectNotFoundException("Project Task  With "+projectSequence+" does not exist in project with id "+
                   backlogId+" . Combination Not found ");
        return projectTask;
    }
    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogId)
    {
        //this checks for any possible errors.
        ProjectTask pt=findProjectTaskBySequence(backlogId,updatedTask.getProjectSequence());
        updatedTask.setBacklog(pt.getBacklog());
        return projectTaskRepository.save(updatedTask);

    }
    public void deleteByProjectSequence( String backlogId,String projectSequence)
    {
        //this checks for any possible errors.
        ProjectTask pt=findProjectTaskBySequence(backlogId,projectSequence);
         projectTaskRepository.delete(pt);
    }


}
